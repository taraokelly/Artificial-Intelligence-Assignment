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
	private double keyScore;
	private String cipherText;
	private String plainText;
	private String[] diagraphs;
	
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
		/* UPDATE: built diagraphs before and created another decrypt method in playfair accepting the pre-built diagraph instead.
		 * This prevents the diagraph being built in O(n) time, and will do it in O(1) time. 
		 */
		String parentKey = ks.getParentNode();	
		this.setUp(parentKey); 
		this.simulatedAnnealing(parentKey);
	}
	// Set up starting node.
	private void setUp(String parentKey){
		// Generate random parent key. Then decrypt cipher text with the key.
		parentKey = ks.getParentNode();
		this.setPlainText(pf.decrypt(this.getDiagraphs(), parentKey));	
		// Calculate the score.
		double score = scoreText(this.getPlainText());
		this.setKeyScore(score);
	}
	// Simulated annealing - heuristic evaluation function.
	private void simulatedAnnealing(String parentKey){
		String key;
		for (int i = temperature; i > 0; i--) {
			for (int j = transitions; j > 0; j--) {
				// Key shuffling delegated to KeyNode class.
				ks.shuffleKey(parentKey);
				key = ks.getNode();
				//double score = 0, delta;
				this.setPlainText(pf.decrypt(this.getDiagraphs(), key));	
				// Calculate distance to see which key is better.
				double score = scoreText(this.getPlainText());
				double delta = score - this.getKeyScore();
				// If current key better...
				if(delta > 0) {
					parentKey = key;
					this.setKeyScore(score);
				}// If parent key better...
				else {
					double probability = Math.pow(Math.E,(delta/i));
					// Number getting larger than one as transition iterator decrements. 
					if (probability > r.nextDouble()) { 
						parentKey = key;
						this.setKeyScore(score);
					}
				}
			}
		}	
	}
	// Generate 4-mer shingles from text.
	private double scoreText(String plainText) {
		double score = 0;
		long count = 457373638373L;
		for(int i = 0; i < this.getPlainText().length() - 4; i++){
			Double gram = gramMap.get(this.getPlainText().substring(i,i+4));
			if(gram == null) {
				gram = 1.0;
			}
			score += Math.log10((double) gram/ count);
		}
		return score;
	}
	// Getters and setters.
	private double getKeyScore() {
		return keyScore;
	}
	private void setKeyScore(double keyScore) {		
		this.keyScore = keyScore;
	}
	public String getCurrentKey() {
		return ks.getNode();
	}
	public String getCipherText() {
		return cipherText;
	}
	private void setCipherText(String cipherText) {
		this.cipherText = cipherText;
		this.setDiagraphs(pf.createDiagrams(this.cipherText));
	}
	public String getPlainText() {
		return plainText;
	}
	private void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	public String[] getDiagraphs() {
		return diagraphs;
	}
	public void setDiagraphs(String[] diagraphs) {
		this.diagraphs = diagraphs;
	}
}