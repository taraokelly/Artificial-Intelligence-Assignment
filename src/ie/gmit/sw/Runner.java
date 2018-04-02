package ie.gmit.sw;

import java.util.Scanner;

import ie.gmit.sw.cipherbreaker.*;
import ie.gmit.sw.file.ReadTextFileLine;
import ie.gmit.sw.file.WriteTextFileLine;

public class Runner {
	
	WriteTextFileLine wf = new WriteTextFileLine("./DecryptedFile.txt");
	CipherBreakerFactory factory = new CipherBreakerFactory();
	String option = null;
	Integer temp, trans;
	
	public void menu(Scanner in,Boolean running){
		System.out.println("=====================================================\n"+
						   "                        Menu                         \n"+
						   "=====================================================\n\n"+
							"Enter the root path for the file you wish to decrypt:");
		System.out.println("[ESC to terminate]");
		option = in.nextLine();
		
		switch (option) {	
			case "ESC": 
				running = false;
				break;
			default: 
				ReadTextFileLine frc = new ReadTextFileLine(option);
				
				if(frc.getFileContents() != null){
					System.out.println("Enter temperature:");
					temp = validateIntInput(in);
					in.nextLine();
					
					System.out.println("Enter transition:");
					trans = validateIntInput(in);
					in.nextLine();
					
					CipherBreakator sa = factory.getCipherBreaker("SA", temp, trans, frc.getFileContents());
					System.out.println("Attempting to break cipher...\n");
					sa.breakCipher();
					System.out.println("Saving results...\n");
					System.out.println(wf.writeFile(sa.getPlainText()));
				}else{
					System.out.println("Invalid File");
				}
		}
	}
	public Integer validateIntInput(Scanner in){
		while (!in.hasNextInt()) {
		   System.out.println("Invalid datatype. Enter whole number.");
		   in.nextLine();
		}
		return in.nextInt();
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Boolean running = true;
		
		while(running){	
			Runner r = new Runner();
			r.menu(in, running);
			System.out.println();
		}
		// Housekeeping.
		in.close();
	}
}
