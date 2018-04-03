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
	
	private Map<String, Integer> fourGramMap;
	private long fourGramSum;
	
	public ReadTextFileGram(String name) {
		super(name);
	}
	
	// Read in file line by line, and append to string.
	public void readFile() {
		fourGramMap = new HashMap<String, Integer>();
		long sum = 0;
		String text = null;
		// Check validity of file name given.
		if(isFile()){
			try {
				BufferedReader bf = new BufferedReader(new FileReader(this.getFile()));
				StringBuffer sb = new StringBuffer();
				String line = null;	
				while ((line = bf.readLine()) != null) {
					String[] columns = line.split(" ");
					
					if (columns.length >= 2) {
						String key = columns[0];
						int value = Integer.parseInt(columns[1]);
						fourGramMap.put(key,value);
						sum += value;
					}	
					setFourGramCount(sum);
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
	
	public Map<String, Integer> getFourGramMap() {
		return fourGramMap;
	}
	public long getFourGramCount() {
		return fourGramSum;
	}
	public void setFourGramCount(long fourGramSum) {
		this.fourGramSum = fourGramSum;
	}
}
