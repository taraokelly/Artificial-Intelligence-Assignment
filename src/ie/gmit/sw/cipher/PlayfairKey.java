package ie.gmit.sw.cipher;

public class PlayfairKey {
	
	private String key;
	private String[][] keyTable;
	
	public String parseString(String parse) {
		parse = parse.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
		return parse;
	}
	public String[][] buildCipherTable(String key) {
		String[][] keyTable = new String[5][5];
		String keyString = parseString(key) + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				keyTable[i][j] = "";
			}			
		}

		for (int k = 0; k < keyString.length(); k++) {
			boolean repeat = false;
			boolean used = false;
			
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (keyTable[i][j].equals("" + keyString.charAt(k))) {
						repeat = true;
					} else if (keyTable[i][j].equals("") && !repeat && !used) {
						keyTable[i][j] = "" + keyString.charAt(k);
						used = true;
					}
				}
			}
		}
		return keyTable;
	}
	
	public void setKey(String key) {
		this.key = this.parseString(key);
		this.keyTable = this.buildCipherTable(this.key);
	}
	public String getKey() {
		return key;
	}
	public String[][] getKeyTable() {
		return keyTable;
	}
	
}
