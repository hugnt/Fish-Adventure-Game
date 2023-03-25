package root;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import javax.imageio.ImageIO;



public abstract class IOHandler {
	
	public static BufferedImage getImage(String fileName) {
		BufferedImage img = null;
		InputStream is = IOHandler.class.getResourceAsStream("/" + fileName);
		System.out.println("/" + fileName);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return img;
	}
	
}
