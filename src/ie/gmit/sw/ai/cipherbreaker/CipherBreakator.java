package ie.gmit.sw.ai.cipherbreaker;

public interface CipherBreakator {
	public void breakCipher();
	public String getPlainText();
	public String getCurrentKey();
}
