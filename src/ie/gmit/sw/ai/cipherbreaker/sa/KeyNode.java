package ie.gmit.sw.ai.cipherbreaker.sa;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ie.gmit.sw.ai.cipher.PlayfairKey;
/* 
 * The KeyNode class holds the key and parent key objects. KeyNode also performs the key 
 * shuffling required for the simulated annealing. 
 */
public class KeyNode {
	
	private PlayfairKey parentNode = new PlayfairKey();
	private PlayfairKey node = new PlayfairKey();
	
	// Generate the corresponding key from a table.
	public String generateKey(char[][] keyTable) {
		String s = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				s+= keyTable[i][j];
			}
		}
		return s;
	}		
	// Simulated annealing shuffling.
	public void shuffleKey(String newKey) {		
		int random = (int)Math.floor(Math.random() * 100);
		this.setParentTable(newKey);		
		switch (random) {
			case 1:
			case 2:
				// Swap random rows (2%).
				this.swapRandomRows();
				break;
			case 3:
			case 4:
				// Swap columns (2%).
				this.swapRandomColumns();
				break;
			case 5:
			case 6:
				// Flip all rows (2%).
				this.flipRows();
				break;
			case 7:
			case 8:
				// Flip all columns (2%). 
				this.flipColumns();
				break;
			case 9:
			case 10:
				// Reverse the whole key (2%).
				this.flipColumns();
				this.flipRows();	
				break;
			default:
				// Swap single letters (90%).
				this.swapRandomChars();			
				break;
		}
		
		this.setNode(this.generateKey(this.getParentTable()));
	}
	// Swap around random rows in the current key node to set as the next child node.
	private void swapRandomRows() {
		int r1 = 0, r2 = 0;
		// Select two unique columns.
		while(r1 == r2){
			r1 = (int)Math.floor(Math.random()*5);
			r2 = (int)Math.floor(Math.random()*5);
		}
		// Swap.
		char[][] table = this.getParentTable();
		char[] temp = table[r1];
		table[r1] = table[r2];
		table[r2] = temp;
		this.setParentTable(table);
	}
	// Swap around random columns in the current key node to set as the next child node.
	private void swapRandomColumns() {		
		int c1 = 0, c2 = 0;
		// Select two unique columns.
		while(c1 == c2){
			c1 = (int)Math.floor(Math.random()*5);
			c2 = (int)Math.floor(Math.random()*5);
		}
		// Swap.
		for (int i = 0; i < 5; i++) {
			char[][] table = this.getParentTable();
			char temp = table[i][c1];
			table[i][c1] = table[i][c2];
			table[i][c2] = temp;
			this.setParentTable(table);
		}		
	}
	// Flip all rows in the current key node to set as the next child node.
	private void flipRows() {
		char[][] table = this.getParentTable();
		for (int i = 0; i < 5; i++) {
			char[] temp = table[i];
			for(int j = 0; j < temp.length / 2; j++) {
			    char temp2 = temp[j];
			    temp[j] = temp[temp.length - j - 1];
			    temp[temp.length - j - 1] = temp2;
			}
			table[i] = temp;			
		}
		this.setParentTable(table);
	}
	// Flip all columns in the current key node to set as the next child node.
	private void flipColumns() {
		char[][] table = this.getParentTable();
		for (int i = 0; i < 5/2; i++) {
			for (int j = 0; j < 5; j++) {
				char temp = table[i][j];
				table[i][j] = table[5 - i - 1][j];
				table[5 - i - 1][j] = temp;
			}
		}
		this.setParentTable(table);
	}
	// Swap randomly selected chars.
	private void swapRandomChars() {
		int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		// Select unique coordinates to swap.
		while ((r1 == r2) && (c1 == c2)){
			r1 = (int) Math.floor((Math.random()*5));
			c1 = (int) Math.floor((Math.random()*5));
			r2 = (int) Math.floor((Math.random()*5));
			c2 = (int) Math.floor((Math.random()*5));	
		}
		// Swap.
		char[][] table = this.getParentTable();
		char temp = table[r1][c1];
		table[r1][c1] = table[r2][c2];
		table[r2][c2] = temp;
		this.setParentTable(table);
	}
	// Getters and setters.
	public char[][] getParentTable() {
		return this.parentNode.getKeyTable();
	}
	public void setParentTable(String key) {
		char[][] tempTable = new char[5][5];
		int run = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tempTable[i][j] = key.charAt(run);
				run++;
			}	
		}
		this.parentNode.setKeyTable(tempTable);
	}
	public void setParentTable(char[][] keyTable) {
		this.parentNode.setKeyTable(keyTable);
	}
	public String getParentNode() {
		return this.parentNode.getKey();
	}
	public void setParentNode(String key) {
		char[] k = key.toCharArray();
		int i;
		Random random = ThreadLocalRandom.current();
		// Original shuffle.
		for (int j = k.length - 1; j > 0; j--) {
			i = random.nextInt(j + 1);
			if (i != j) {
				k[i] ^= k[j];
				k[j] ^= k[i];
				k[i] ^= k[j];
			 }
		}
		this.parentNode.setKey(String.valueOf(k));
		this.setParentTable(this.parentNode.getKey());
	}
	public String getNode() {
		return this.node.getKey();
	}
	public void setNode(String key) {
		this.node.setKey(key);
	}
}
