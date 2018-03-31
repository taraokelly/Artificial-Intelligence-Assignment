package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class ReadFile {
	
	private File file;
	private String fileContents;
	private String fileType = "text/plain";
	
	public ReadFile(String name) {
		this.setFile(new File(name));
		this.setFileContents(readFile());
	}
	
	private Boolean isFile(){
		//Ternary statement that calls isTextFile() if it is a valid file, or returns false.
		return (this.file.exists() && !this.file.isDirectory()) ? isTextFile() : false;
	}
	private Boolean isTextFile(){
		try{  
			  return (Files.probeContentType(this.file.toPath()).equalsIgnoreCase(this.fileType));
		   }  
		   catch (IOException ioException){  
		      return null;
		   }
	}
	// Read in file line by line, and append to string.
	private String readFile() {
		String text = null;
		// Check validity of file name given.
		if(isFile()){
			try {
				BufferedReader bf = new BufferedReader(new FileReader(this.file));
				StringBuffer sb = new StringBuffer();
				String line = null;	
				while ((line = bf.readLine()) != null) {
					sb.append(line);				
				}		
				text = sb.toString();
				// Housekeeping.
				bf.close();
				// Display error in menu.
			} catch (FileNotFoundException e) {
				text = null;
			} catch (IOException e) {
				text = null;
			} 
		}
		return text;
	}
	
	public String getFileContents() {
		return fileContents;
	}
	private void setFileContents(String fileContents) {
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