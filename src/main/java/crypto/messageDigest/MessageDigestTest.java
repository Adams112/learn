package crypto.messageDigest;

import crypto.messageDigest.md.MD5;

public class MessageDigestTest {

	public static void main(String[] args) {
		MD5 md5 = new MD5();
		printBytes(md5.digest("jklmn".getBytes()));
	}
	
	private static void printInt(int n) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++)
			b[i] = getByte((n >> (28 - 4 * i)) & 0xf);
		System.out.println(new String(b));
	}
	
	private static byte getByte(int b) {
		if (b < 10)
			return (byte) (b + '0');
		else
			return (byte) (b - 10 + 'a');
	}
	
	private static void printBytes(byte[] b) {
		byte[] dst = new byte[b.length * 2];
		for (int i = 0; i < b.length; i++) {
			dst[i * 2 + 0] = getByte((b[i] >> 4) & 0xf);
			dst[i * 2 + 1] = getByte(b[i] & 0xf);
		}
		System.out.println(new String(dst));
	}
}
