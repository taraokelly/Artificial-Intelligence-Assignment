package ie.gmit.sw.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadTextFileLine extends ReadTextFile{
	
	public ReadTextFileLine(String name) {
		super(name);
	}
	
	// Read in file line by line, and append to string.
	public void readFile() {
		String text = null;
		// Check validity of file name given.
		if(isFile()){
			try {
				BufferedReader bf = new BufferedReader(new FileReader(this.getFile()));
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
		this.setFileContents(text);
	}
}