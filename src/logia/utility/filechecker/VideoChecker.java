package logia.utility.filechecker;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Class VideoChecker.
 * 
 * @author Paul Mai
 */
public final class VideoChecker {

	private enum VideoCode {
		_3GP5_MPEG4("3gp", new int[] { 0x00, 0x00, 0x00, 0x18, 0x66, 0x74, 0x79, 0x70 }), ASF("asf", new int[] { 0x30, 0x26, 0xb2, 0x75, 0x8e, 0x66,
				0xcf, 0x11 }), AVI("avi", new int[] { 0x52, 0x49, 0x46, 0x46 }), MKV("mkv", new int[] { 0x1a, 0x45, 0xdf, 0xa3, 0x93, 0x42, 0x82,
						0x88 }), MOV_1("mov", new int[] { 0x6d, 0x6d, 0x6f, 0x76 }), MOV_2("mov", new int[] { 0x66, 0x72, 0x65, 0x65 }), MOV_3("mov",
								new int[] { 0x6d, 0x64, 0x61, 0x74 }), MOV_4("mov", new int[] { 0x77, 0x69, 0x64, 0x65 }), MOV_5("mov", new int[] { 0x70, 0x6e, 0x6f,
										0x74 }), MOV_6("mov", new int[] { 0x73, 0x6b, 0x69, 0x70 });

		private String extension;
		private int[]  header;

		private VideoCode(String extension, int[] header) {
			this.extension = extension;
			this.header = header;
		}

		public String getExtension() {
			return this.extension;
		}

		public int[] getHeader() {
			return this.header;
		}
	}

	public static String getVideoType(File file) throws Exception {
		if (VideoChecker.is3GP5_MPEG4(file)) {
			return VideoCode._3GP5_MPEG4.getExtension();
		}
		else if (VideoChecker.isASF(file)) {
			return VideoCode.ASF.getExtension();
		}
		else if (VideoChecker.isAVI(file)) {
			return VideoCode.AVI.getExtension();
		}
		else if (VideoChecker.isMKV(file)) {
			return VideoCode.MKV.getExtension();
		}
		else if (VideoChecker.isMOV_1(file)) {
			return VideoCode.MOV_1.getExtension();
		}
		else if (VideoChecker.isMOV_2(file)) {
			return VideoCode.MOV_2.getExtension();
		}
		else if (VideoChecker.isMOV_3(file)) {
			return VideoCode.MOV_3.getExtension();
		}
		else if (VideoChecker.isMOV_4(file)) {
			return VideoCode.MOV_4.getExtension();
		}
		else if (VideoChecker.isMOV_5(file)) {
			return VideoCode.MOV_5.getExtension();
		}
		else if (VideoChecker.isMOV_6(file)) {
			return VideoCode.MOV_6.getExtension();
		}
		else {
			return "N/A";
		}
	}

	public static boolean is3GP5_MPEG4(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode._3GP5_MPEG4.getHeader().length; ++i) {
					if (fis.read() != VideoCode._3GP5_MPEG4.getHeader()[i]) {
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

	public static boolean isASF(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.ASF.getHeader().length; ++i) {
					if (fis.read() != VideoCode.ASF.getHeader()[i]) {
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

	public static boolean isAVI(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.AVI.getHeader().length; ++i) {
					if (fis.read() != VideoCode.AVI.getHeader()[i]) {
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

	public static boolean isMKV(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MKV.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MKV.getHeader()[i]) {
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

	public static boolean isMOV_1(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_1.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_1.getHeader()[i]) {
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

	public static boolean isMOV_2(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_2.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_2.getHeader()[i]) {
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

	public static boolean isMOV_3(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_3.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_3.getHeader()[i]) {
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

	public static boolean isMOV_4(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_4.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_4.getHeader()[i]) {
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

	public static boolean isMOV_5(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_5.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_5.getHeader()[i]) {
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

	public static boolean isMOV_6(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < VideoCode.MOV_6.getHeader().length; ++i) {
					if (fis.read() != VideoCode.MOV_6.getHeader()[i]) {
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

	public static boolean isVideo(File file) throws Exception {
		if (VideoChecker.is3GP5_MPEG4(file)) {
			return true;
		}
		else if (VideoChecker.isASF(file)) {
			return true;
		}
		else if (VideoChecker.isAVI(file)) {
			return true;
		}
		else if (VideoChecker.isMKV(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_1(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_2(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_3(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_4(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_5(file)) {
			return true;
		}
		else if (VideoChecker.isMOV_6(file)) {
			return true;
		}
		else {
			return false;
		}
	}
}
