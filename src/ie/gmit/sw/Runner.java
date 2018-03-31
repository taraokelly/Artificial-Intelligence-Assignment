package ie.gmit.sw;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		String option = null;
		boolean running = true;
		
		while(running){
			
			System.out.println("The root path for the file you wish to decrypt:");
			System.out.println("[ESC to terminate]");
			option = in.nextLine();///"C:/Users/Tara/Documents/Artifical Intelligence/Assignment/hobbit.txt"
			
			switch (option) {	
				case "ESC": 
					running = false;
					break;
				default: 
					ReadFile frc = new ReadFile(option);
					if(frc.getFileContents() != null){
						//System.out.println(frc.getFileContents());
						// Do decryption.
						String keyword = "THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOGS";
						//String message = "this is the message";
						String message = "HERARAHETDUYAZYQ";
						//String message = frc.getFileContents();
						PlayFairCipher pf = new PlayFairCipher(keyword, message);
						String arr[] = pf.decrypt(message).split(" ", 2);
						System.out.println(arr[0]);
						
					}else{
						System.out.println("Invalid File");
					}
			}
		}
		// Housekeeping.
		in.close();
	}
}
