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
		/*String parentKey = ks.getParentNode();	
		this.setUp(parentKey); 
		this.simulatedAnnealing(parentKey);*/
		
		String parentKey, bestText = null, bestKey = null;
		double  bestScore = 0;
		
		String[] diag = pf.createDiagrams(this.cipherText);
		
		parentKey = ks.getParentNode();
		String txt = pf.decrypt(diag, parentKey);	
		// Calculate the score.
		double parentScore = scoreText(txt);
		bestScore = parentScore;
		
		for (int i = this.temperature; i > 10; i--) {
			for (int j = this.transitions; j > 0; j--) {
				String key = ks.shuffleKey(parentKey);
				String text = pf.decrypt(diag, key);
				System.out.println(text.substring(0, 10));
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
				if(bestScore < parentScore){
					bestKey = parentKey;
					bestScore = parentScore;
					bestText = text;
					parentKey = key;
				}
				
			}
		}
		System.out.println(bestText);
		System.out.println("LAST K:" + parentKey);
		this.setPlainText(bestText);
		System.out.println(getPlainText());
	}
	/*// Set up starting node.
	private void setUp(String parentKey){
		// Generate random parent key. Then decrypt cipher text with the key.
		parentKey = ks.getParentNode();
		this.setPlainText(pf.decrypt(this.getDiagraphs(), parentKey));	
		// Calculate the score.
		double score = scoreText();
		this.setKeyScore(score);
	}
	// Simulated annealing - heuristic evaluation function.
	private void simulatedAnnealing(){
		String key;
//		for (int i = temperature; i > 10; i--) {
//			for (int j = transitions; j > 0; j--) {
//				// Key shuffling delegated to KeyNode class.
//				ks.shuffleKey(parentKey);
//				key = ks.getNode();
//				this.setPlainText(pf.decrypt(this.getDiagraphs(), key));	
//				// Calculate distance to see which key is better.
//				double score = scoreText();
//				double delta = score - this.getKeyScore();
//				// If current key better...
//				if(delta > 0) {
//					parentKey = key;
//					this.setKeyScore(score);
//				}// If parent key better...
//				else {
//					double probability = Math.pow(Math.E,(delta/i));
//					// Number getting larger than one as transition iterator decrements. 
//					if (probability > r.nextDouble()) { 
//						parentKey = key;
//						this.setKeyScore(score);
//					}
//				}
//				//System.out.println("Temp " + i +  ". Current key: " + parentKey + " with score " + this.getKeyScore() + ". Decrypted: " + this.getPlainText().substring(0, 20) + "..." + "\n");
//			}
		
		String parent;
		String text = pf.decrypt(parent, key)
		double parentScore;
		double bestScore;
		
		
		for(int i = temperature; i < 0; i--){
			for (int j = transitions; j > 0; j--) {
				String child = ks.shuffleKey(parent);
				text = pf.decrypt(child, key);
				double childScore = scoreText(text);
				
				double df = childScore - parentScore;
				
				if(df > 0){
					parent = child;
					parentScore = childScore;
				}else{
					if((Math.exp(df/i)) > 0.5){
						parent = child;
						parentScore = childScore;
					}
				}
				
				if(bestScore < parentScore){
					String bestKey = parent;
					bestScore = parentScore;
					String bestText = text;
					System.out.println("statistics");
				}
				
			}
		}	
	}*/
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