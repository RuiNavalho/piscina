package pt.piscina.ds.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Cripter {
	
	private static MessageDigest digest;
	
	public static String cryptMyPass(String str) {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}
		digest.update(str.getBytes());
		byte[] hash = digest.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

}
