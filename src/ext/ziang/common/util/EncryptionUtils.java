package ext.ziang.common.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密实用程序
 *
 * @author anzhen
 * @date 2024/04/03
 */
public class EncryptionUtils {

	/**
	 * 算法
	 */
	private static final String ALGORITHM = "AES";
	/**
	 * 密钥
	 */
	private static final String SECRET_KEY = "aa;dsj@123sc%daq";

	public static String encrypt(String strToEncrypt) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
			return new String(decryptedBytes);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static void main(String[] args) {
		String originalString = "anzhen3531";
		String encryptedString = encrypt(originalString);
		String decryptedString = decrypt(encryptedString);

		System.out.println("Original: " + originalString);
		System.out.println("Encrypted: " + encryptedString);
		System.out.println("Decrypted: " + decryptedString);
	}
}
