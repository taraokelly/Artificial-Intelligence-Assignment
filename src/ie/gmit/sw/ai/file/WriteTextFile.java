package ie.gmit.sw.file;

import java.io.File;
import java.io.PrintWriter;
/*
 * Specialized WriteFile implementation to handle the validation and generalization of 
 * plain text files.
 */
public abstract class WriteTextFile implements WriteFile {
	private File file;
	private PrintWriter pw;
	
	public WriteTextFile(String fileName) {
		super();
		this.setFile(fileName);
	}
	
	public abstract String writeFile(String text);
	
	protected Boolean isNotDirectory(){
		return (!this.getFile().isDirectory()) ? true : false;
	}
	// Getters and setters.
	public File getFile() {
		return file;
	}
	private void setFile(String file) {
		this.file = new File(file);
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}	
}
