package logia.utility.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Class ScaleImage.
 * 
 * @author Paul Mai
 */
public final class ScaleImage {

	/**
	 * Scale image.
	 *
	 * @param sourceLocation the source location
	 * @param targetLocation the target location
	 * @param fileImage the file image
	 * @param newSize the new size (pixel)
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void doScale(String sourceLocation, String targetLocation, String fileImage, double newSize) throws IOException {
		File file = new File(sourceLocation + System.getProperty("file.separator") + fileImage);
		if (file.exists()) {
			Image image = ImageIO.read(file);
			BufferedImage sbi = (BufferedImage) image;
			/* Get size of image */
			Double width = (double) sbi.getWidth();
			Double height = (double) sbi.getHeight();
			Double d;
			/* Get proportion of scaled image */
			if (width > height) {
				d = width / height;
				height = newSize;
				width = height * d;
			}
			else {
				d = height / width;
				width = newSize;
				height = width * d;
			}
			/* Scale this image */
			BufferedImage dbi = null;
			if (sbi != null) {
				dbi = new BufferedImage(width.intValue(), height.intValue(), sbi.getType());
				Graphics2D g = dbi.createGraphics();
				g.drawImage(sbi, 0, 0, width.intValue(), height.intValue(), null);
				g.dispose();
			}
			/* retrieve image */
			File outputfile = new File(targetLocation + System.getProperty("file.separator") + fileImage);
			ImageIO.write(dbi, "png", outputfile);
		}
	}
}
