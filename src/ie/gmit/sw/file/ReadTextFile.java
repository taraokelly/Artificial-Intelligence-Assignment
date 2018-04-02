package ie.gmit.sw.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class ReadTextFile implements ReadFile {
	private File file;
	private String fileContents;
	private String fileType = "text/plain";
	
	public ReadTextFile(String name) {
		this.setFile(new File(name));
		this.readFile();
	}
	
	public abstract void readFile();
	
	protected Boolean isFile(){
		// Ternary statement that calls isTextFile() if it is a valid file, or returns false.
		return (this.file.exists() && !this.file.isDirectory()) ? isTextFile() : false;
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
	
	public String getFileContents() {
		return fileContents;
	}
	protected void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}
	public String getFileType() {
		return fileType;
	}
	public File getFile() {
		return file;
	}
	private void setFile(File file) {
		this.file = file;
	}
}
