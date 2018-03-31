package ie.gmit.sw;

import java.util.Scanner;

import ie.gmit.sw.cipher.PlayFairCipher;
import ie.gmit.sw.file.ReadFile;
import ie.gmit.sw.file.WriteFile;

public class Runner {

	public static void main(String[] args) {
		
		WriteFile wf = new WriteFile("./DecryptedFile.txt");
		Scanner in = new Scanner(System.in);
		String option = null;
		boolean running = true;
		
		while(running){	
			System.out.println("=====================================================\n"+
							   "=====================================================\n"+
								"Enter the root path for the file you wish to decrypt:");
			System.out.println("[ESC to terminate]");
			option = in.nextLine();//"C:/Users/Tara/Documents/Artifical Intelligence/Assignment/hobbit.txt"
			
			switch (option) {	
				case "ESC": 
					running = false;
					break;
				default: 
					//ReadFile frc = new ReadFile(option);
					ReadFile frc = new ReadFile("C:/Users/Tara/Documents/Artifical Intelligence/Assignment/ex.txt");
					
					if(frc.getFileContents() != null){
						
						String keyword = "THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOGS";
						String message = frc.getFileContents();
						
						PlayFairCipher pf = new PlayFairCipher();
						pf.setKey(keyword);
						
						wf.writeFile(pf.decrypt(message));
						
					}else{
						System.out.println("Invalid File");
					}
			}
		}
		// Housekeeping.
		in.close();
	}
}
