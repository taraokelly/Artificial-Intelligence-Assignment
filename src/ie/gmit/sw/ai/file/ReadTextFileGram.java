package ie.gmit.sw.ai.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/*
 * Read 4-grams from a text file.
 */
public class ReadTextFileGram extends ReadTextFile {
	
	private Map<String, Double> fourGramMap;
	
	public ReadTextFileGram(String name) {
		super(name);
	}
	
	// Read in file line by line, and append to string.
	public void readFile() {
		fourGramMap = new HashMap<String, Double>();
		// Check validity of file name given.
		if(isFile()){
			try {
				BufferedReader bf = new BufferedReader(new FileReader(this.getFile()));
				String line = null;	
				while ((line = bf.readLine()) != null) {
					String[] parts = line.split(" "); // Split the line at the space
					fourGramMap.put(parts[0], Double.parseDouble(parts[1])); // first part is the gram, second is the count
				}		
				// Housekeeping.
				bf.close();
				// Display error in menu.
			} catch (FileNotFoundException e) {
				System.out.println("Error reading 4grams.");
			} catch (IOException e) {
				System.out.println("Error reading 4grams.");
			} 
		}
	}
	
	public Map<String, Double> getFourGramMap() {
		return fourGramMap;
	}
}
