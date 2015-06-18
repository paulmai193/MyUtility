package logia.utility.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class EncryptionUtils.
 */
public class EncryptionUtils {

	/** The Random Constant SALT. */
	private static final byte[] SALT            = { (byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A,
		(byte) 0x75                        };

	/** The Constant ITERATION_COUNT. */
	private final static int    ITERATION_COUNT = 31;

	/**
	 * Instantiates a new encryption utils.
	 */
	private EncryptionUtils() {
	}

	/**
	 * Decode.
	 *
	 * @param token the token
	 * @return the string
	 */
	public static String decode(String token, String password) {
		if (token == null) {
			return null;
		}
		try {

			String input = token.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+');

			byte[] dec = Base64.decodeBase64(input.getBytes());

			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), EncryptionUtils.SALT, EncryptionUtils.ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(EncryptionUtils.SALT, EncryptionUtils.ITERATION_COUNT);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			byte[] decoded = dcipher.doFinal(dec);

			String result = new String(decoded);
			return result;

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Encode.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String encode(String input, String password) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		try {

			KeySpec keySpec = new PBEKeySpec(password.toCharArray(), EncryptionUtils.SALT, EncryptionUtils.ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(EncryptionUtils.SALT, EncryptionUtils.ITERATION_COUNT);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

			byte[] enc = ecipher.doFinal(input.getBytes());

			String res = new String(Base64.encodeBase64(enc));
			// escapes for url
			res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");

			return res;

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Encode MD5.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String encodeMD5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (byte element : messageDigest) {
				String h = Integer.toHexString(0xFF & element);
				while (h.length() < 2) {
					h = "0" + h;
				}
				hexString.append(h);
			}
			return hexString.toString();

		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

}
