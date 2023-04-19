package entity;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import main.Game;
import main.Main;
import root.IOHandler;

public class Hint {
	private int x, y;
	private int width, height;
	private BufferedImage hintDialog;
	private String hint;
	
	private Font font;
	
	private boolean showHint;

	
	public Hint(int x, int y, int width, int height, String hint) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hint = hint;
		hintDialog = IOHandler.getImage("popup.png");
		font = IOHandler.getFont("RobotoMono-Regular.ttf").deriveFont(Font.PLAIN, 11*Main.SCALE);
	
	}
	
	private void drawQuestion(Graphics g, String text, int x, int y, int maxWidth) {
	    FontMetrics fm = g.getFontMetrics();
	    int lineHeight = fm.getHeight();
	    int curX = x;
	    int curY = y;
	    for (String word : text.split("\\s+")) {
	        // Tính độ dài của từ hiện tại
	        int wordWidth = fm.stringWidth(word + " ");

	        // Nếu độ dài từ hiện tại vượt quá giới hạn chiều rộng
	        if (curX + wordWidth > x + maxWidth) {
	            // Xuống dòng và bắt đầu một chuỗi văn bản mới
	            curX = x;
	            curY += lineHeight;
	        }

	        // Vẽ từ hiện tại
	        g.drawString(word + " ", curX, curY);
	        curX += wordWidth;
	    }
	}

	public void render(Graphics g, int xMapOffset) {
	    if (showHint) {
	        g.drawImage(hintDialog, x - xMapOffset, y - Main.TILES_SIZE * 3, (int)(width*5*Main.SCALE), (int)(height*3*Main.SCALE), null);
	        g.setFont(font);
	        drawQuestion(g, hint, (int)(x - xMapOffset+Main.TILES_SIZE * 0.7),(int)(y - Main.TILES_SIZE * 2+Main.TILES_SIZE * 0.4), (int)(width*5*Main.SCALE-Main.TILES_SIZE * 0.7));
	    }
	}

	public boolean isShowHint() {
		return showHint;
	}

	public void setShowHint(boolean showHint) {
		this.showHint = showHint;
	}
	
	
	
}
