package logia.utility.filechecker;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Class ImageChecker.
 * 
 * @author Paul Mai
 */
public final class ImageChecker {
	public static boolean isImage(File file) throws Exception {
		if (isBMP(file)) {
			return true;
		}
		else if (isGIF(file)) {
			return true;
		}
		else if (isICO(file)) {
			return true;
		}
		else if (isJPEG(file)) {
			return true;
		}
		else if (isPNG(file)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getImageType(File file) throws Exception {
		if (isBMP(file)) {
			return ImageCode.BMP.getExtension();
		}
		else if (isGIF(file)) {
			return ImageCode.GIF.getExtension();
		}
		else if (isICO(file)) {
			return ImageCode.ICO.getExtension();
		}
		else if (isJPEG(file)) {
			return ImageCode.JPEG.getExtension();
		}
		else if (isPNG(file)) {
			return ImageCode.PNG.getExtension();
		}		
		else {
			return "N/A";
		}
	}
	
	public static boolean isBMP(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < ImageCode.BMP.getHeader().length; ++i) {
					if (fis.read() != ImageCode.BMP.getHeader()[i]) {
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
	
	public static boolean isGIF(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < ImageCode.GIF.getHeader().length; ++i) {
					if (fis.read() != ImageCode.GIF.getHeader()[i]) {
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
	
	public static boolean isICO(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < ImageCode.ICO.getHeader().length; ++i) {
					if (fis.read() != ImageCode.ICO.getHeader()[i]) {
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
	
	public static boolean isJPEG(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < ImageCode.JPEG.getHeader().length; ++i) {
					if (fis.read() != ImageCode.JPEG.getHeader()[i]) {
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
	
	public static boolean isPNG(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < ImageCode.PNG.getHeader().length; ++i) {
					if (fis.read() != ImageCode.PNG.getHeader()[i]) {
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
	
	private enum ImageCode {
		BMP("bmp", new int[] { 0x42, 0x4d }),
		GIF("gif", new int[] { 0x47, 0x49, 0x46, 0x38 }),
		ICO("ico", new int[] { 0x00, 0x00, 0x01, 0x00 }),
		JPEG("jpeg", new int[] { 0xff, 0xd8, 0xff }),
		PNG("png", new int[] { 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a });
		
		private String extension;
		private int[] header;
		private ImageCode(String extension, int[] header) {
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
