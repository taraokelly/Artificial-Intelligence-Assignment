package ie.gmit.sw.cipher;

public abstract class Key implements Keyator {
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
