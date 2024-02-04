package com.iocl.fb.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;

public class AESCrypt {

	private static final String ALGORITHM = "AES";
	private static final String KEY = "aaUIpGnapISrKsHL";

	public static String encrypt(int value) {
		return encrypt(String.valueOf(value));
	}

	public static String encrypt(String value) {
		String encryptedValue64 = "";
		try {
			// String randomKey;
			// if (ApplicationOneTimeConstants.userNameEncryptedTextMap.get(value) != null)
			// {
			// return ApplicationOneTimeConstants.userNameEncryptedTextMap.get(value);
			// }

			// randomKey = generateRandomKey();
			// Key key = generateKey();

			Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(), AESCrypt.ALGORITHM);
			// Key key = new SecretKeySpec(randomKey.getBytes(), AESCrypt.ALGORITHM);
			Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
			encryptedValue64 = Base64.getUrlEncoder().encodeToString(encryptedByteValue);
			// if (AESRandomKeyList.userNameEncryptedTextMap.get(value) != null) {
			// AESRandomKeyList.encryptedTextEncryptionKeyMap
			// .remove(AESRandomKeyList.userNameEncryptedTextMap.get(value));
			// }
			// ApplicationOneTimeConstants.userNameEncryptedTextMap.put(value,
			// encryptedValue64);
			// ApplicationOneTimeConstants.encryptedTextEncryptionKeyMap.put(encryptedValue64,
			// randomKey);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return encryptedValue64;
	}

	public static String decrypt(String value) throws InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		String decryptedValue = "";
		// try {
		// Key key = generateKey();
		// String randomKey =
		// ApplicationOneTimeConstants.encryptedTextEncryptionKeyMap.get(value);
		Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(), AESCrypt.ALGORITHM);
		// Key key = new SecretKeySpec(randomKey.getBytes(), AESCrypt.ALGORITHM);
		Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedValue64 = Base64.getUrlDecoder().decode(value);
		byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);

		decryptedValue = new String(decryptedByteValue, "utf-8");
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		return decryptedValue;
	}

	// private static Key generateKey() {
	//
	// Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(), AESCrypt.ALGORITHM);
	// return key;
	// }

	public static boolean isValidUserid(String userId) {

//		if (userId.equals("98765432")) {// For VAPT Auditor
//			return true;
//		}
//		if (userId.equals("")) {
//			return false;
//		}
//		if (userId.length() > 6) {
//			return false;
//		}
//		if (!userId.matches("-?(0|[1-9]\\d*)")) {
//			return false;
//		}
		return true;
	}

	public static String generateRandomKey() {
		return RandomStringUtils.randomAlphanumeric(8);
	}

}
