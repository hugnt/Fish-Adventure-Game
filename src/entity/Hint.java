package entity;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Game;
import root.IOHandler;

public class Hint {
	private int x, y;
	private int width, height;
	private BufferedImage questionDialog;
	private String question;
	
	private Font font;
	
	private boolean showHint;

	
	public Hint(int x, int y, int width, int height, String question) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.question = question;
		questionDialog = IOHandler.getImage("popup.png");
		font = IOHandler.getFont("RobotoMono-Regular.ttf").deriveFont(Font.PLAIN, 11*Game.SCALE);
	
	}
	
	private void drawQuestion(Graphics g, String text, int x, int y) {
	    int maxWordsPerLine =(int)(3*Game.SCALE);
	    String[] words = text.split("\\s+");
	    StringBuilder line = new StringBuilder();
	    for (int i = 0; i < words.length; i++) {
	        if (i > 0 && i % maxWordsPerLine == 0) {
	            // add the current line to the graphics context
	            g.drawString(line.toString(), x, y += g.getFontMetrics().getHeight());
	            // start a new line
	            line = new StringBuilder();
	        }
	        // add the current word to the current line
	        line.append(words[i]).append(" ");
	    }
	    // add the last line to the graphics context
	    g.drawString(line.toString(), x, y += g.getFontMetrics().getHeight());
	}
	
	public void render(Graphics g, int xMapOffset) {
		if(showHint==true) {
			g.drawImage(questionDialog, x - xMapOffset, y-Game.TILES_SIZE*3, width*5, height*3, null);
			g.setFont(font);
			drawQuestion(g, question,  x - xMapOffset+20, y-Game.TILES_SIZE*2+5);
		}
	
	}

	public boolean isShowHint() {
		return showHint;
	}

	public void setShowHint(boolean showHint) {
		this.showHint = showHint;
	}
	
	
	
}
