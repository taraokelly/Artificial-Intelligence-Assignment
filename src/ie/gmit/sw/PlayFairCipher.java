package ie.gmit.sw;

import java.awt.Point;

public class PlayFairCipher {
	
	private String[][] cipherTable;

	public PlayFairCipher(String key, String message) {
		this.cipherTable = this.buildCipherTable(parseString(key));
	}

	private String parseString(String parse) {
		parse = parse.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
		return parse;
	}
	private String[][] buildCipherTable(String key) {
		String[][] cipherTable = new String[5][5];
		String keyString = parseString(key) + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				cipherTable[i][j] = "";
			}			
		}

		for (int k = 0; k < keyString.length(); k++) {
			boolean repeat = false;
			boolean used = false;
			
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (cipherTable[i][j].equals("" + keyString.charAt(k))) {
						repeat = true;
					} else if (cipherTable[i][j].equals("") && !repeat && !used) {
						cipherTable[i][j] = "" + keyString.charAt(k);
						used = true;
					}
				}
			}
		}
		return cipherTable;
	}
	private String[] buildDiagraph(String di[], Integer length) {
		String[] d = new String[length];
		for (int i = 0; i < length; i++) {
			char a = di[i].charAt(0);
			char b = di[i].charAt(1);
			int r1 = (int) getPoint(a).getX();
			int r2 = (int) getPoint(b).getX();
			int c1 = (int) getPoint(a).getY();
			int c2 = (int) getPoint(b).getY();

			if (r1 == r2) {
				c1 = (c1 + 1) % 5;
				c2 = (c2 + 1) % 5;

			} else if (c1 == c2) {
				r1 = (r1 + 1) % 5;
				r2 = (r2 + 1) % 5;

			} else {
				int temp = c1;
				c1 = c2;
				c2 = temp;
			}
			d[i] = cipherTable[r1][c1] + "" + cipherTable[r2][c2];
		}
		return d;
	}
	public String encrypt(String plainText) {
		String p = parseString(plainText);
		Integer l = (int) p.length() / 2 + p.length() % 2;

		for (int i = 0; i < (l - 1); i++) {
			if (p.charAt(2 * i) == p.charAt(2 * i + 1)) {
				p = new StringBuffer(p).insert(2 * i + 1, 'X').toString();
				l = (int) p.length() / 2 + p.length() % 2;
			}
		}

		String[] digraph = new String[l];
		for (int j = 0; j < l; j++) {
			if (j == (l - 1) && p.length() / 2 == (l - 1))
				p = p + "X";
			digraph[j] = p.charAt(2 * j) + "" + p.charAt(2 * j + 1);
		}

		String out = "";
		String[] encDigraphs = new String[l];
		encDigraphs = buildDiagraph(digraph, l);
		for (int k = 0; k < l; k++)
			out = out + encDigraphs[k];
		return out;
	}
	public String decrypt(String out) {
		String cihperText = "";
		for (int i = 0; i < out.length() / 2; i++) {
			char a = out.charAt(2 * i);
			char b = out.charAt(2 * i + 1);
			int r1 = (int) getPoint(a).getX();
			int r2 = (int) getPoint(b).getX();
			int c1 = (int) getPoint(a).getY();
			int c2 = (int) getPoint(b).getY();
			if (r1 == r2) {
				c1 = (c1 + 4) % 5;
				c2 = (c2 + 4) % 5;
			} else if (c1 == c2) {
				r1 = (r1 + 4) % 5;
				r2 = (r2 + 4) % 5;
			} else {
				int temp = c1;
				c1 = c2;
				c2 = temp;
			}
			cihperText = cihperText + cipherTable[r1][c1] + cipherTable[r2][c2];
		}
		return cihperText;
	}
	
	public String[][] getTable() {
		return cipherTable;
	}
	public void setKey(String key) {
		this.cipherTable = this.buildCipherTable(parseString(key));
	}
	private Point getPoint(char c) {
		Point pt = new Point(0, 0);
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				if (c == cipherTable[i][j].charAt(0))
					pt = new Point(i, j);
		return pt;
	}
}
