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
						System.out.println(frc.getFileContents());
					}else{
						System.out.println("Invalid File");
					}
			}
		}
		// Housekeeping.
		in.close();
	}

}
