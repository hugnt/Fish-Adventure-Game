package root;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public abstract class IOHandler {
	private static final String FILE_CONFIG = "\\config.properties";
	private static final String FILE_UPDATE = "\\update.properties";
	
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
		InputStream is = null;
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
	 public static Clip getAudio(String fileName) {
	        Clip clip = null;
	        InputStream is = null;
	        try {
	        	is =IOHandler.class.getResourceAsStream("/" + fileName);
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
	            clip = AudioSystem.getClip();
	            // open audioInputStream to the clip
	            clip.open(audioInputStream);
	           	            
	        } catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					is.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
	        return clip;
	    }
	 
	public static String getProperty(String key, String fileName) {
		String res = null;
		fileName = "\\"+ fileName;
		Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + fileName);

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
	public static void setProperty(String key, String value, String fileName) {
	    Properties properties = new Properties();
	    fileName = "\\"+ fileName;
	    OutputStream outputStream = null;
	    try {
	        String currentDir = System.getProperty("user.dir");
	        File configFile = new File(currentDir + fileName);

	        // load properties from file
	        FileInputStream inputStream = new FileInputStream(configFile);
	        properties.load(inputStream);

	        // update property value
	        properties.setProperty(key, value);

	        // save properties to file
	        outputStream = new FileOutputStream(configFile);
	        properties.store(outputStream, "Update time: "+LocalDateTime.now());

	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        // close objects
	        try {
	            if (outputStream != null) {
	                outputStream.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
}
