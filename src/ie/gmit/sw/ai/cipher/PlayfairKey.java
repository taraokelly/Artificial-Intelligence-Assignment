package ie.gmit.sw.cipher;
/*
 * Inherits String "key" and "key" getter and setters, from "Key" class.
 * Besides that, its just a plain ol' POJO.
 */
public class PlayfairKey extends Key {
	private char [][] keyTable;	
	// Getters and setters.
	public char [][] getKeyTable() {
		return keyTable;
	}
	public void setKeyTable(char [][] keyTable) {
		this.keyTable = keyTable;
	}
}
