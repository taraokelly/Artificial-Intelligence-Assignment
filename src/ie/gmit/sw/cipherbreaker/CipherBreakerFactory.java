package ie.gmit.sw.cipherbreaker;

import ie.gmit.sw.cipherbreaker.sa.SimulatedAnnealingCipherBreaker;

public class CipherBreakerFactory {
	
	public CipherBreakator getCipherBreaker(String type){  
		return null;
	}
	public CipherBreakator getCipherBreaker(String type, int data1){
		return null;
	}
	public CipherBreakator getCipherBreaker(String type, int data1, int data2, String data3){
		if(type == null){
	    	return null;
	    }		
	    if(type.equalsIgnoreCase("SA")){
	    	return new SimulatedAnnealingCipherBreaker(data1,data2,data3);
		} 
		return null;
   }
}
