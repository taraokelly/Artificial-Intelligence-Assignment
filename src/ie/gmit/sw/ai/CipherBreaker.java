package ie.gmit.sw.ai;

import java.util.Scanner;

import ie.gmit.sw.ai.cipherbreaker.*;
import ie.gmit.sw.ai.file.ReadTextFileLine;
import ie.gmit.sw.ai.file.WriteTextFileLine;
/* 
 * The Runner class is responsible for displaying the menu, getting user input, and to 
 * actualize the user controls. 
 */
public class CipherBreaker {
	
	WriteTextFileLine wf = new WriteTextFileLine("./DecryptedFile.txt");
	CipherBreakerFactory factory = new CipherBreakerFactory();
	Scanner in = new Scanner(System.in);
	Boolean running = true;
	String option = null;
	Integer temp, trans;
	/* 
	 * The user can input a file name to read in, or ESC to exit. 
	 * If the file is valid, the user will be prompted to enter the temperature and transition values. 
	 * Upon entering acceptable values, the Cipher Breaker factory will be called to create a SA class.
	 * After, the results will be saved to the file "DecryptedFile.txt" in the current directory.
	 */
	public void menu(){
		while(running){	
			System.out.println("=====================================================\n"+
					   "                        Menu                         \n"+
					   "=====================================================\n\n"+
						"Enter the path for the file you wish to decrypt:");
			System.out.println("[ESC to terminate]");
			option = in.nextLine();
			
			switch (option.toUpperCase()) {	
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
						System.out.println("Final key:" + sa.getCurrentKey()+"\n\nEnter destination:");
						option = in.nextLine();
						wf.setFile(option);
						System.out.println("\nSaving results...\n");
						System.out.println(wf.writeFile(sa.getPlainText()));
					}else{
						System.out.println("Invalid File");
					}
			}
			System.out.println();
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
		CipherBreaker r = new CipherBreaker();
		r.menu();
	}
}
