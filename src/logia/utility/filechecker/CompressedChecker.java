package logia.utility.filechecker;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Class CompressedChecker.
 * 
 * @author Paul Mai
 */
public final class CompressedChecker {
	public static boolean isCompressed(File file) throws Exception {
		if (is7Z(file)) {
			return true;
		}
		else if (isGZ(file)) {
			return true;
		}
		else if (isZIP(file)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getCompressedType(File file) throws Exception {
		if (is7Z(file)) {
			return CompressedCode._7Z_7ZIP.getExtension();
		}
		else if (isGZ(file)) {
			return CompressedCode.GZ.getExtension();
		}	
		else if (isZIP(file)) {
			return CompressedCode.ZIP.getExtension();
		}
		else {
			return "N/A";
		}
	}
	
	public static boolean is7Z(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < CompressedCode._7Z_7ZIP.getHeader().length; ++i) {
					if (fis.read() != CompressedCode._7Z_7ZIP.getHeader()[i]) {
						checked = false;
						break;
					}
					else {
						checked = true;
					}
				}
			}
			finally {
				fis.close();
			}
		}
		return checked;
	}
	
	public static boolean isGZ(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < CompressedCode.GZ.getHeader().length; ++i) {
					if (fis.read() != CompressedCode.GZ.getHeader()[i]) {
						checked = false;
						break;
					}
					else {
						checked = true;
					}
				}
			}
			finally {
				fis.close();
			}
		}
		return checked;
	}
	
	public static boolean isZIP(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < CompressedCode.ZIP.getHeader().length; ++i) {
					if (fis.read() != CompressedCode.ZIP.getHeader()[i]) {
						checked = false;
						break;
					}
					else {
						checked = true;
					}
				}
			}
			finally {
				fis.close();
			}
		}
		return checked;
	}
	
	private enum CompressedCode {
		_7Z_7ZIP("7z", new int[] { 0x37, 0x7a, 0xbc, 0xaf, 0x27, 0x1c }),
		GZ("gz", new int[] { 0x1f, 0x8b, 0x08 }),
		ZIP("zip", new int[] { 0x1f, 0x8b, 0x08 });
		
		private String extension;
		private int[] header;
		private CompressedCode(String extension, int[] header) {
			this.extension = extension;
			this.header = header;
		}
		public String getExtension() {
			return extension;
		}
		public int[] getHeader() {
			return header;
		}
	}
}
