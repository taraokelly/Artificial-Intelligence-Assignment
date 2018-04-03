package ie.gmit.sw.cipherbreaker.sa;

import java.util.Map;

import ie.gmit.sw.cipher.PlayfairDecrypter;
import ie.gmit.sw.cipherbreaker.CipherBreakator;
import ie.gmit.sw.file.ReadTextFileGram;
/*
 * Here the set up, comparisons and operations required for the simulated annealing happens.
 */
public class SimulatedAnnealingCipherBreaker implements CipherBreakator {
	
	private KeyNode ks;
	private ReadTextFileGram rg;
	private PlayfairDecrypter pf;
	private String[] grams;
	private Map<String, Integer> gramMap;
	private final String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	private int temperature;
	private int transitions;
	private double keyScore;
	private String cipherText;
	private String plainText;
	
	public SimulatedAnnealingCipherBreaker(int temperature, int transitions, String cipherText) {
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
		String parentKey = ks.getParentNode();	
		this.setUp(parentKey); 
		this.simulatedAnnealing(parentKey);
	}
	// Set up starting node.
	private void setUp(String parentKey){
		double tempS = 0;	
		// Generate random parent key. Then decrypt cipher text with the key.
		parentKey = ks.getParentNode();
		this.setPlainText(pf.decrypt(this.getCipherText(), parentKey));	
		this.generate4Grams(this.getPlainText());
		// Calculate the score.
		for (String k : grams) {
			if (gramMap.keySet().contains(k)) {
				double probability = (Math.log10(gramMap.get(k).doubleValue()) / Math.log10(rg.getFourGramCount()));
				tempS += probability;
			}			
		}		
		this.setKeyScore(tempS);
	}
	// Simulated annealing - heuristic evaluation function.
	private void simulatedAnnealing(String parentKey){
		String key;
		for (int i = temperature; i > 0; i--) {
			for (int j = transitions; j > 0; j--) {
				ks.shuffleKey(parentKey);
				key = ks.getNode();
				double score = 0, delta;
				this.setPlainText(pf.decrypt(this.getCipherText(), key));	
				this.generate4Grams(this.getPlainText());	
				// Calculate the score/fitness.
				for (String k : grams) {
					if (gramMap.keySet().contains(k)) {
						double probability = (Math.log10(gramMap.get(k).doubleValue()) / Math.log10(rg.getFourGramCount()));
						score += probability;
					}			
				}		
				// Calculate distance to see which key is better.
				delta = score - this.getKeyScore();
				// If current key better...
				if(delta > 0) {
					parentKey = key;
					this.setKeyScore(score);
				}// If parent key better...
				else if(delta < 0){
					double probability = Math.pow(Math.E,(delta/temperature));
					if (probability > 0.5) {
						parentKey = key;
						this.setKeyScore(score);
					}
				}
			}
		}	
	}
	// Generate 4-mer shingles from text.
	// Shifting 2 characters at a time.
	private void generate4Grams(String cipherText) {
		grams = new String[cipherText.length()-4];
		int i = 0;			
		for (int j = 0; j < cipherText.length()-4; j++) {
			 grams[i] = cipherText.substring(j, j + 4);         
	         i++;
		}
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
	}
	public String getPlainText() {
		return plainText;
	}
	private void setPlainText(String plainText) {
		this.plainText = plainText;
	}
}