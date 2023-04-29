package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Main;
import main.SurvivalGame;
import root.IOHandler;
import root.SKeyHandler;

public class Goldfish extends Creature {
	private BufferedImage leftImg, rightImg;
	public double speedCoef;
	private boolean left, up;
	public Goldfish(SurvivalGame SG, int worldX, int worldY, double speedCoef) {
		super(SG, worldX, worldY, "Gold fish", 
				new Rectangle((int)(6*Main.SCALE), (int)(10*Main.SCALE), (int)(20*Main.SCALE), (int)(13*Main.SCALE)));
		this.speedCoef = speedCoef;
		leftImg = IOHandler.getImage("yellow_fish_left.png");
		rightImg = IOHandler.getImage("yellow_fish_right.png");
	}
	public void update() {
		double[][] corner = {{worldX + hitbox.x, worldY + hitbox.y},
						{worldX + hitbox.x + hitbox.width, worldY + hitbox.y + hitbox.height}};	
		int[] nextTile = null;
		boolean dirFlag = true;
		int u1 = SG.getPlayer().getWorldX() - (int)worldX;
		int u2 = SG.getPlayer().getWorldY() - (int)worldY;
		if(u1 < 0) left = true;
		else if(u1 > 0) left = false;
		if(u2 < 0) up = true;
		else if(u2 > 0) up = false;
		nextTile = SG.getMap().traceMap(corner, speedCoef*u1, 0, false);
		if(left) {
			for(int i = 0; i <= 2; i++) 
				if(!SG.getMap().tileIsMoveable(nextTile[(i + 6)%8]))
					dirFlag = false;	
		}
		else {
			for(int i = 2; i <= 4; i++) 
				if(!SG.getMap().tileIsMoveable(nextTile[i]))
					dirFlag = false;
		}
		if(dirFlag) worldX += speedCoef*u1;
		dirFlag = true;
		nextTile = SG.getMap().traceMap(corner, 0, speedCoef*u2, false);
		if(up) {
			for(int i = 0; i <= 2; i++) 
				if(!SG.getMap().tileIsMoveable(nextTile[i]))
					dirFlag = false;
		}
		else {
			for(int i = 4; i <= 6; i++) 
				if(!SG.getMap().tileIsMoveable(nextTile[i]))
					dirFlag = false;
		}
		if(dirFlag) worldY += speedCoef*u2;
	}
	public void render(Graphics2D g2) {
			int screenX = (int)worldX - SG.getPlayer().getWorldX() + SG.getPlayer().getScreenX();
			int screenY = (int)worldY - SG.getPlayer().getWorldY() + SG.getPlayer().getScreenY();
			if(screenX >= -5*Main.TILES_SIZE && screenY >= -5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_WIDTH + 5*Main.TILES_SIZE && screenY <= Main.GAME_HEIGHT + 5*Main.TILES_SIZE) {
				if(left) g2.drawImage(leftImg, screenX, screenY, Main.TILES_SIZE, Main.TILES_SIZE, null);
				else g2.drawImage(rightImg, screenX, screenY, Main.TILES_SIZE, Main.TILES_SIZE, null);
			}
	}
	public double distanceToPlayer() {
		return Math.sqrt(Math.pow(SG.getPlayer().getWorldX() - worldX, 2) 
				+ Math.pow(SG.getPlayer().getWorldY() - worldY, 2));
	}
}




