package logia.utility.image;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Class DrawImage.
 * 
 * @author Paul Mai
 */
public class DrawImage {

	/**
	 * Do draw.
	 *
	 * @param file the file
	 * @param image the image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void doDraw(File file, BufferedImage image) throws IOException {
		if (image != null) {
			Graphics2D g = image.createGraphics();
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			g.dispose();
			ImageIO.write(image, "png", file);
		}
	}

	/**
	 * Do draw.
	 *
	 * @param frame the frame
	 * @param image the image
	 */
	public static void doDraw(Frame frame, BufferedImage image) {
		if (image != null) {
			frame.setSize(image.getWidth(), image.getWidth());
			frame.getGraphics().drawImage(image, 0, 0, null);
		}
	}
}
