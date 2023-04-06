package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import root.IOHandler;

public class Lock {
	private int x, y;
	private int width, height;
	private boolean unlock;
	
	public Lock(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		unlock = false;
	}
	
	public void render(Graphics g, int xMapOffset) {
//		g.drawImage(barrierImg,x - xMapOffset, y, width, height, null);
	}

	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}
	
	
	
	
	
}
