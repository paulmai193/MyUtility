package logia.utility.filechecker;

import java.io.File;
import java.io.FileInputStream;

/**
 * The Class AudioChecker.
 * 
 * @author Paul Mai
 */
public final class AudioChecker {
	public static boolean isAudio(File file) throws Exception {
		if (isAAC_MPEG2(file)) {
			return true;
		}
		else if (isAAC_MPEG4(file)) {
			return true;
		}
		else if (isAMR(file)) {
			return true;
		}
		else if (isFLAC(file)) {
			return true;
		}
		else if (isFLV(file)) {
			return true;
		}
		else if (isM4A(file)) {
			return true;
		}
		else if (isMIDI(file)) {
			return true;
		}
		else if (isMP3(file)) {
			return true;
		}
		else if (isWAV(file)) {
			return true;
		}
		else if (isWMA(file)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static String getAudioType(File file) throws Exception {
		if (isAAC_MPEG2(file)) {
			return AudioCode.AAC_MPEG2.getExtension();
		}
		else if (isAAC_MPEG4(file)) {
			return AudioCode.AAC_MPEG4.getExtension();
		}
		else if (isAMR(file)) {
			return AudioCode.AMR.getExtension();
		}
		else if (isFLAC(file)) {
			return AudioCode.FLAC.getExtension();
		}
		else if (isFLV(file)) {
			return AudioCode.FLV.getExtension();
		}
		else if (isM4A(file)) {
			return AudioCode.M4A.getExtension();
		}
		else if (isMIDI(file)) {
			return AudioCode.MIDI.getExtension();
		}
		else if (isMP3(file)) {
			return AudioCode.MP3.getExtension();
		}
		else if (isWAV(file)) {
			return AudioCode.WAV.getExtension();
		}
		else if (isWMA(file)) {
			return AudioCode.WMA.getExtension();
		}
		else {
			return "N/A";
		}
	}
	
	public static boolean isAAC_MPEG2(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.AAC_MPEG2.getHeader().length; ++i) {
					if (fis.read() != AudioCode.AAC_MPEG2.getHeader()[i]) {
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
	
	public static boolean isAAC_MPEG4(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.AAC_MPEG4.getHeader().length; ++i) {
					if (fis.read() != AudioCode.AAC_MPEG4.getHeader()[i]) {
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
	
	public static boolean isAMR(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.AMR.getHeader().length; ++i) {
					if (fis.read() != AudioCode.AMR.getHeader()[i]) {
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
	
	public static boolean isFLAC(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.FLAC.getHeader().length; ++i) {
					if (fis.read() != AudioCode.FLAC.getHeader()[i]) {
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
	
	public static boolean isFLV(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.FLV.getHeader().length; ++i) {
					if (fis.read() != AudioCode.FLV.getHeader()[i]) {
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
	
	public static boolean isM4A(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.M4A.getHeader().length; ++i) {
					if (fis.read() != AudioCode.M4A.getHeader()[i]) {
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
	
	public static boolean isMIDI(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.MIDI.getHeader().length; ++i) {
					if (fis.read() != AudioCode.MIDI.getHeader()[i]) {
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
	
	public static boolean isMP3(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.MP3.getHeader().length; ++i) {
					if (fis.read() != AudioCode.MP3.getHeader()[i]) {
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
	
	public static boolean isWAV(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.WAV.getHeader().length; ++i) {
					if (fis.read() != AudioCode.WAV.getHeader()[i]) {
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
	
	public static boolean isWMA(File file) throws Exception {
		boolean checked = false;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			try {
				for (int i = 0; i < AudioCode.WMA.getHeader().length; ++i) {
					if (fis.read() != AudioCode.WMA.getHeader()[i]) {
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
	
	private enum AudioCode {
		AAC_MPEG2("aac", new int[] { 0xff, 0xf9 }),
		AAC_MPEG4("aac", new int[] { 0xff, 0xf1 }),
		AMR("arm", new int[] { 0x23, 0x21, 0x41, 0x4d, 0x52 }),
		FLAC("flac", new int[] { 0x66, 0x4c, 0x61, 0x43, 0x00, 0x00, 0x00, 0x22 }),
		FLV("flv", new int[] { 0x46, 0x4c, 0x56 }),
		M4A("m4a", new int[] { 0x00, 0x00, 0x00, 0x20, 0x66, 0x74, 0x79, 0x70, 0x4d, 0x34, 0x41 }),
		MIDI("midi", new int[] { 0x4d, 0x54, 0x68, 0x64 }),
		MP3("mp3", new int[] { 0x49, 0x44, 0x33 }),
		WAV("wav", new int[] { 0x52, 0x49, 0x46, 0x46 }),
		WMA("wma", new int[] { 0x30, 0x26, 0xb2, 0x75, 0x8e, 0x66, 0xcf, 0x11 });
		
		private String extension;
		private int[] header;
		private AudioCode(String extension, int[] header) {
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
