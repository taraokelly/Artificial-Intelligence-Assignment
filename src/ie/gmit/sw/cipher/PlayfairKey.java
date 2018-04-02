package ie.gmit.sw.cipher;

public class PlayfairKey extends Key{
	
	private char [][] keyTable;	
	
	public char [][] getKeyTable() {
		return keyTable;
	}
	public void setKeyTable(char [][] keyTable) {
		this.keyTable = keyTable;
	}
}
