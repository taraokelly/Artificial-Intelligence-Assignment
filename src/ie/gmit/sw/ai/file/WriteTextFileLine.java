package ie.gmit.sw.ai.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
/*
 * Write single string to text file.
 */
public class WriteTextFileLine extends WriteTextFile{
	
	public WriteTextFileLine(String fileName) {
		super(fileName);
	}
	
	public String writeFile(String text) {
		String status = "Error saving file.";
		// Check validity of file name given.
		if(this.isNotDirectory()){
			try {
				this.setPw(new PrintWriter(this.getFile().getName()));
				getPw().print(text);
				status = "Save successful. File location:\n\t" + this.getFile().getParentFile().getAbsolutePath();
				// Got to clean up that house.
				getPw().close();
			} catch (FileNotFoundException e) {}
			catch (NullPointerException e) {}
			catch (Exception e) {}
		}
		return status;
	}
}
