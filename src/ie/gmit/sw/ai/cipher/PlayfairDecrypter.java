package ie.gmit.sw.ai.cipher;
/*
 * Decrypts the Playfair cipher. No local variables for easy decryption with multiple keys.
 */
public class PlayfairDecrypter implements Decryptable{
	// Decrypts a given cipher text with a given key.
	public String decrypt(String cipherText, String key) {
		String[] d = this.createDiagrams(cipherText);
		StringBuilder plainText = new StringBuilder();
		for (String pair : d) {
			char c1, c2;
			int row1 = (key.indexOf(pair.charAt(0)) / 5);
			int row2 = (key.indexOf(pair.charAt(1)) / 5);
            int col1 = (key.indexOf(pair.charAt(0)) % 5);
            int col2 = (key.indexOf(pair.charAt(1)) % 5);
            
            if (col1 == col2) {
            	c1 = key.charAt((row1 + 4) % 5 * 5 + col1);
            	c2 = key.charAt((row2 + 4) % 5 * 5 + col2);
            } else if (row1 == row2) {
            	c1 = key.charAt(row1 * 5 +(col1 +4) % 5);
            	c2 = key.charAt(row2 * 5 +(col2 +4) % 5);
            } else {
            	c1 = key.charAt(row1 * 5 + col2);
            	c2 = key.charAt(row2 * 5 + col1);
            }
            plainText.append(Character.toString(c1) + Character.toString(c2));
		}
		return plainText.toString();
	}
	// Creates substrings of two letters each.
	public String[] createDiagrams(String encMessage) {
		String digrams[] = new String[encMessage.length() /2];
		int i = 0;
		for (int j = 0; j < encMessage.length()-1; j = j + 2) {
			 digrams[i] = encMessage.substring(j, j + 2);
	         i++;
		}
		return digrams;
	}
	// Function specifically decrypting cipher text all really in the form of diagraphs.
	public String decrypt(String[] cipherText, String key) {
		StringBuilder plainText = new StringBuilder();
		for (String pair : cipherText) {
			char c1, c2;
			int row1 = (key.indexOf(pair.charAt(0)) / 5);
			int row2 = (key.indexOf(pair.charAt(1)) / 5);
            int col1 = (key.indexOf(pair.charAt(0)) % 5);
            int col2 = (key.indexOf(pair.charAt(1)) % 5);
            
            if (col1 == col2) {
            	c1 = key.charAt((row1 + 4) % 5 * 5 + col1);
            	c2 = key.charAt((row2 + 4) % 5 * 5 + col2);
            } else if (row1 == row2) {
            	c1 = key.charAt(row1 * 5 +(col1 +4) % 5);
            	c2 = key.charAt(row2 * 5 +(col2 +4) % 5);
            } else {
            	c1 = key.charAt(row1 * 5 + col2);
            	c2 = key.charAt(row2 * 5 + col1);
            }
            plainText.append(Character.toString(c1) + Character.toString(c2));
		}
		return plainText.toString();
	}
}
