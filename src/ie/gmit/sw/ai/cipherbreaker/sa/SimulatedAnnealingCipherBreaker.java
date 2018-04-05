package ie.gmit.sw.ai.cipherbreaker.sa;

import java.security.SecureRandom;
import java.util.Map;

import ie.gmit.sw.ai.cipher.PlayfairDecrypter;
import ie.gmit.sw.ai.cipherbreaker.CipherBreakator;
import ie.gmit.sw.ai.file.ReadTextFileGram;
/*
 * Here the set up, comparisons and operations required for the simulated annealing happens.
 */
public class SimulatedAnnealingCipherBreaker implements CipherBreakator {
	private SecureRandom r;
	private KeyNode ks;
	private ReadTextFileGram rg;
	private PlayfairDecrypter pf;
	private Map<String, Double> gramMap;
	private final String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	private int temperature;
	private int transitions;
	private String key;
	private String cipherText;
	private String plainText;
	
	public SimulatedAnnealingCipherBreaker(int temperature, int transitions, String cipherText) {
		this.r = new SecureRandom();
		ks = new KeyNode();
		ks.setParentNode(alphabet);	
		pf = new PlayfairDecrypter();
		this.setCipherText(cipherText);	
		rg = new ReadTextFileGram("./4grams.txt");
		gramMap = rg.getFourGramMap();	
		
		this.temperature = temperature;
		this.transitions = transitions;
	}
	// Method required to be implemented by the Cipher Breakator interface.
	public void breakCipher() {
		/* 
		 * UPDATE: built diagraphs before and created another decrypt method in playfair accepting the pre-built diagraph instead.
		 * This prevents the diagraph being built in O(n) time, and will do it in O(1) time. 
		 */
		
		String parentKey, bestText = null, bestKey = null;
		double  bestScore = 0;
		
		String[] diag = pf.createDiagrams(this.cipherText);
		
		parentKey = ks.getParentNode();
		String txt = pf.decrypt(diag, parentKey);	
		// Calculate the score.
		double parentScore = scoreText(txt);
		bestScore = parentScore;
		
		for (int i = this.temperature; i > 0; i--) {
				for (int j = this.transitions; j > 0; j--) {
					String key = ks.shuffleKey(parentKey);
					String text = pf.decrypt(diag, key);
					double childScore = scoreText(text);
					
					double delta = childScore - parentScore;
					if(delta > 0){
						parentKey = key;
						parentScore = childScore;
					}else{
						if((Math.exp(delta/i)) > r.nextDouble()){
							parentKey = key;
							parentScore = childScore;
						}
					}
					if(bestScore < childScore){
						bestKey = key;
						bestScore = childScore;
						bestText = text;
					}
					
				}
			}
			this.setPlainText(bestText);
			this.setCurrentKey(bestKey);
	}
	
	// Generate 4-mer shingles from text.
	private double scoreText(String t) {
		double s = 0;
		long count = 457373638373L;
		for(int i = 0; i < t.length() - 4; i++){
			Double gram = gramMap.get(t.substring(i ,i + 4));
			if(gram == null) {
				gram = 1.0;
			} 
			s += Math.log10((double) (gram/ count));
		}
		return s;
	}
	// Getters and setters.
	private void setCurrentKey(String key) {
		this.key = key;
	}
	public String getCurrentKey() {
		return key;
	}
	public String getCipherText() {
		return cipherText;
	}
	private void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
	public String getPlainText() {
		return plainText;
	}
	private void setPlainText(String plainText) {
		this.plainText = plainText;
	}
}