package ie.gmit.sw.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WriteFile {
	
	private File file;
	private String fileName;
	private PrintWriter pw;
	
	public WriteFile(String fileName) {
		super();
		this.setFile(fileName);
		this.setFileName(fileName);
	}
	
	public String writeFile(String text) {
		String status = "Error saving file.";
		// Check validity of file name given.
		if(this.isNotDirectory()){
			try {
				this.pw = new PrintWriter(this.getFileName());
				pw.print(text);
				// Got to clean up that house.
				pw.close();
				status = "Save successful.";
			} catch (FileNotFoundException e) {}
		}
		return status;
	}
	private Boolean isNotDirectory(){
		return (!this.getFile().isDirectory()) ? true : false;
	}
	
	private File getFile() {
		return file;
	}
	private void setFile(String file) {
		this.file = new File(file);
	}
	public String getFileName() {
		return fileName;
	}
	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
