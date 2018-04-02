package ie.gmit.sw.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
				// Got to clean up that house.
				getPw().close();
				status = "Save successful. File location:\n\t" + this.getFile().getParentFile().getAbsolutePath();
			} catch (FileNotFoundException e) {}
		}
		return status;
	}
}
