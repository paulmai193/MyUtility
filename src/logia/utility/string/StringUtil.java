package logia.utility.string;

import java.util.Arrays;

/**
 * The Class StringProcess.
 */
public class StringUtil {

	/** The Constant SPECIAL_CHARACTERS. */
	private static final char[] SPECIAL_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
		'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ',
		'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ',
		'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ',
		'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', };

	/** The Constant REPLACEMENTS. */
	private static final char[] REPLACEMENTS       = { 'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a',
		'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
		'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E',
		'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
		'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', };

	/** The Constant ALPHABET. */
	private static final char[] ALPHABET           = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
		't', 'u', 'v', 'w', 'x', 'y', 'z'     };

	/**
	 * Convert number to character.
	 *
	 * @param number the number
	 * @return the character
	 */
	public static char convertNumToChar(long number) {
		if (number >= StringUtil.ALPHABET.length) {
			number = number / StringUtil.ALPHABET.length - 1;
			return StringUtil.convertNumToChar(number);
		}
		else {
			return StringUtil.ALPHABET[(int) number];
		}
	}

	/**
	 * Removes the accent.
	 *
	 * @param s the string
	 * @return the string
	 */
	public static String removeAccent(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			sb.setCharAt(i, StringUtil.removeAccent(sb.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * Removes the accent and lower case.
	 *
	 * @param s the string
	 * @return the string
	 */
	public static String removeAccentLowerCase(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			sb.setCharAt(i, StringUtil.removeAccent(sb.charAt(i)));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * Removes the accent.
	 *
	 * @param ch the character
	 * @return the char
	 */
	private static char removeAccent(char ch) {
		int index = Arrays.binarySearch(StringUtil.SPECIAL_CHARACTERS, ch);
		if (index >= 0) {
			ch = StringUtil.REPLACEMENTS[index];
		}
		return ch;
	}
}
