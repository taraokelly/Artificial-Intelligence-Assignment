package ie.gmit.sw.ai.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
/*
 * Specialized WriteFile implementation to handle the validation and generalization of 
 * plain text files.
 */
public abstract class WriteTextFile implements WriteFile {
	private File file;
	private PrintWriter pw;
	private String fileType = "text/plain";
	
	public WriteTextFile(String fileName) {
		super();
		this.setFile(fileName);
	}
	
	public abstract String writeFile(String text);
	
	protected Boolean isNotDirectory(){
		return (!this.getFile().isDirectory()) ? true : isTextFile();
	}
	private Boolean isTextFile(){
		try{  
			  // Adapted from: http://marxsoftware.blogspot.ie/2015/02/determining-file-types-in-java.html
			  return (Files.probeContentType(this.file.toPath()).equalsIgnoreCase(this.fileType));
		   }  
		   catch (IOException ioException){  
		      return null;
		   }
	}
	// Getters and setters.
	public File getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = new File(file);
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}	
}
