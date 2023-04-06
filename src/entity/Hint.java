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
	private JPanel questionPanel;
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
		questionPanel = new JPanel();
		
		font = new Font("Arial", Font.PLAIN, 10);
		
	
	}
	
    private void drawQuestion(Graphics g, String text, int x, int y) {
    	int maxCharsPerLine = 23;
        StringBuilder inputText = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (i > 0 && i % maxCharsPerLine == 0) {
                inputText.append("\n");
            }
            inputText.append(text.charAt(i));
            i++;
        }
        for (String line : inputText.toString().split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	
	public void render(Graphics g, int xMapOffset) {
		if(showHint==true) {
			g.drawImage(questionDialog, x - xMapOffset, y-Game.TILES_SIZE*3, width*4, height*3, null);
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
