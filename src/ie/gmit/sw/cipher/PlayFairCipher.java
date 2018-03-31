package ie.gmit.sw.cipher;

import java.awt.Point;

public class PlayFairCipher {
	
	private PlayfairKey key = new PlayfairKey();

	public PlayFairCipher() {}

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
			d[i] = getKeyTable()[r1][c1] + "" + getKeyTable()[r2][c2];
		}
		return d;
	}
	public String encrypt(String plainText) {
		String p = this.key.parseString(plainText);
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

		String cipherText = "";
		String[] encDigraphs = new String[l];
		encDigraphs = buildDiagraph(digraph, l);
		for (int k = 0; k < l; k++)
			cipherText = cipherText + encDigraphs[k];
		return cipherText;
	}
	public String decrypt(String cipherText) {
		String plainText = "";
		for (int i = 0; i < cipherText.length() / 2; i++) {
			char a = cipherText.charAt(2 * i);
			char b = cipherText.charAt(2 * i + 1);
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
			plainText = plainText + getKeyTable()[r1][c1] + getKeyTable()[r2][c2];
		}
		return plainText;
	}
 
	public void setKey(String key) {
		this.key.setKey(key);
	}
	public String getKey() {
		return this.key.getKey();
	}
	public String[][] getKeyTable() {
		return this.key.getKeyTable();
	}
	private Point getPoint(char c) {
		Point pt = new Point(0, 0);
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				if (c == getKeyTable()[i][j].charAt(0))
					pt = new Point(i, j);
		return pt;
	}
}
