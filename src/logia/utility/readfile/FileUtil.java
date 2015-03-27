package logia.utility.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * The Class FileUtil.
 * 
 * @author Paul Mai
 */
public class FileUtil {

	/**
	 * Select filein directory.
	 *
	 * @param filename the filename
	 * @param path the path
	 * @return the file
	 * @throws NullPointerException the null pointer exception
	 * @throws SecurityException the security exception
	 */
	public static File selectFileinDirectory(String filename, String path) throws NullPointerException, SecurityException {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().contains(filename)) {
					return listOfFiles[i];
				}
			}
		}		
		return null;    	
	}

	/**
	 * Gets the files in directory.
	 *
	 * @param directoryPath the directory path
	 * @return the files in directory
	 */
	public static List<File> getFilesInDirectory(String directoryPath) {
		List<File> list = new ArrayList<File>();
		File folder = new File(directoryPath);
		if (folder.isDirectory()) {
			File[] listOfFiles = folder.listFiles();		
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					list.add(listOfFiles[i]);
				}
			}
			sortFilesByDate(list);
		}		
		return list;
	}

	/**
	 * Sort files by date.
	 *
	 * @param list the list
	 */
	public static void sortFilesByDate(List<File> list) {
		Collections.sort(list, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				return (int) (o2.lastModified() - o1.lastModified());
			}

		});
	}

	/**
	 * Read file.
	 *
	 * @param fileUrl the file url
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFile(String fileUrl) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream is = new FileInputStream(fileUrl);
		BufferedReader input = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String line;
		while ((line = input.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		is.close();
		input.close();
		return sb.toString();
	}
	
	public static void copyDirectory(File sourceLocation, File targetLocation) {
		try {
			if (sourceLocation.exists()) {
				if (sourceLocation.isDirectory()) {
					if (!targetLocation.exists()) {
						targetLocation.mkdir();
					}
					String[] children = sourceLocation.list();
					for (int i = 0; i < children.length; i++) {
						copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
					}
				}
				else if (sourceLocation.isFile()) {
					if (!targetLocation.exists()) {
						targetLocation.mkdir();
					}
					FileUtils.copyFileToDirectory(sourceLocation, targetLocation);
				}
				else {
					InputStream in = new FileInputStream(sourceLocation);
					OutputStream out = new FileOutputStream(targetLocation);

					/* Copy the bits from instream to outstream */
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
