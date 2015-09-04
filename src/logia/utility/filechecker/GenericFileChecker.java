package logia.utility.filechecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * The Class GenericFileChecker.
 * 
 * @author Paul Mai
 */
public final class GenericFileChecker {

	protected static final Logger LOGGER = Logger.getLogger("FileChecker");

	public static String getType(byte[] bytes) {
		String type = "N/A";

		FileOutputStream fOut = null;
		File tempFile = null;
		try {
			String PREFIX = "stream2file";
			String SUFFIX = ".tmp";
			tempFile = File.createTempFile(PREFIX, SUFFIX);
			fOut = new FileOutputStream(tempFile);
			fOut.write(bytes);
			fOut.flush();

			type = GenericFileChecker.getType(tempFile);
		}
		catch (Exception e) {
			GenericFileChecker.LOGGER.error(e.getMessage(), e);
		}
		finally {
			try {
				fOut.close();
				fOut = null;
			}
			catch (IOException e) {
			}
			try {
				tempFile.delete();
				tempFile = null;
			}
			catch (Exception e) {
			}

		}
		return type;
	}

	public static String getType(File file) {
		try {
			if (AudioChecker.isAudio(file)) {
				return AudioChecker.getAudioType(file);
			}
			else if (ImageChecker.isImage(file)) {
				return ImageChecker.getImageType(file);
			}
			else if (VideoChecker.isVideo(file)) {
				return VideoChecker.getVideoType(file);
			}
			else if (MultimediaChecker.isMultimedia(file)) {
				return MultimediaChecker.getMultimediaType(file);
			}
			else if (CompressedChecker.isCompressed(file)) {
				return CompressedChecker.getCompressedType(file);
			}
			else {
				return "N/A";
			}
		}
		catch (Exception e) {
			GenericFileChecker.LOGGER.error(e.getMessage(), e);
			return "N/A";
		}
	}

	public static boolean isRecognize(byte[] bytes) {
		boolean isRecognize = false;

		FileOutputStream fOut = null;
		File tempFile = null;
		try {
			String PREFIX = "stream2file";
			String SUFFIX = ".tmp";
			tempFile = File.createTempFile(PREFIX, SUFFIX);
			tempFile.deleteOnExit();
			fOut = new FileOutputStream(tempFile);
			fOut.write(bytes);
			fOut.flush();

			isRecognize = GenericFileChecker.isRecognize(tempFile);
		}
		catch (Exception e) {
			GenericFileChecker.LOGGER.error(e.getMessage(), e);
		}
		finally {
			try {
				fOut.close();
				fOut = null;
			}
			catch (IOException e) {
			}
			try {
				tempFile.delete();
				tempFile = null;
			}
			catch (Exception e) {
			}

		}
		return isRecognize;
	}

	public static boolean isRecognize(File file) {
		try {
			if (AudioChecker.isAudio(file)) {
				return true;
			}
			else if (ImageChecker.isImage(file)) {
				return true;
			}
			else if (VideoChecker.isVideo(file)) {
				return true;
			}
			else if (MultimediaChecker.isMultimedia(file)) {
				return true;
			}
			else if (CompressedChecker.isCompressed(file)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			GenericFileChecker.LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

}
