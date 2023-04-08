package root;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;



public abstract class IOHandler {
	private static final String FILE_CONFIG = "\\config.properties";
	
	public static BufferedImage getImage(String fileName) {
		BufferedImage img = null;
		InputStream is = null;
		//System.out.println("/" + fileName);
		try {
			is = IOHandler.class.getResourceAsStream("/" + fileName);
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
	
	public static Font getFont(String fileName) {
		Font font = null;
		InputStream is = null;IOHandler.class.getResourceAsStream("/" + fileName);;
		try {
			is =IOHandler.class.getResourceAsStream("/" + fileName);
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return font;
	}
	public static String getProperty(String key) {
		String res = null;
		Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);

            // load properties from file
            properties.load(inputStream);
 
            // get property by name
            res = properties.getProperty(key);
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close objects
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return res;
	}
}
