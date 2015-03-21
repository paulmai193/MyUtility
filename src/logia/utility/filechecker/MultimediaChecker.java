package logia.utility.filechecker;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Class MultimediaChecker.
 * 
 * @author Paul Mai
 */
public final class MultimediaChecker {
	public static boolean isMultimedia(File file) throws Exception {
		if (is3GPP(file)) {
			return true;
		}
		else if (is3GPP2(file)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getMultimediaType(File file) throws Exception {
		if (is3GPP(file)) {
			return MultimediaCode._3GP_3GPP.getExtension();
		}
		else if (is3GPP2(file)) {
			return MultimediaCode._3GP_3GPP2.getExtension();
		}	
		else {
			return "N/A";
		}
	}
	
	public static boolean is3GPP(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < MultimediaCode._3GP_3GPP.getHeader().length; ++i) {
					if (fis.read() != MultimediaCode._3GP_3GPP.getHeader()[i]) {
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
	
	public static boolean is3GPP2(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < MultimediaCode._3GP_3GPP2.getHeader().length; ++i) {
					if (fis.read() != MultimediaCode._3GP_3GPP2.getHeader()[i]) {
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
	
	private enum MultimediaCode {
		_3GP_3GPP("3gp", new int[] { 0x00, 0x00, 0x00, 0x14, 0x66, 0x74, 0x79, 0x70 }),
		_3GP_3GPP2("3gp", new int[] { 0x00, 0x00, 0x00, 0x20, 0x66, 0x74, 0x79, 0x70 });
		
		private String extension;
		private int[] header;
		private MultimediaCode(String extension, int[] header) {
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
