package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.*;
import root.*;

public class Chaser extends Creature{
	private BufferedImage leftImg, rightImg;
	private long endRestTime;
	private double speed, desX, desY;
	private int [][] dirArr = {{7, 0}, {0, 7}, {-7, 0}, {0, -7}, {5, 5}, {5, -5}, {-5, 5}, {-5, -5}}; 
	private int dirSize = 8;
	private boolean left;
	public Chaser(SurvivalGame SG, int worldX, int worldY, double speed) {
		super(SG, worldX, worldY, "Chaser", 
				new Rectangle((int)(6*Main.SCALE), (int)(4*Main.SCALE), (int)(32*Main.SCALE), (int)(36*Main.SCALE)));
		desX = worldX;
		desY = worldY;
		this.speed = speed;
		leftImg = IOHandler.getImage("octopus.png");
		rightImg = IOHandler.getImage("octopus_right.png");
	}
	public void update() {
		if(endRestTime < System.nanoTime()) {
			if(distanceToPlayer() <= 8*Main.TILES_SIZE) {
				desX = 2*SG.getPlayer().getWorldX() - worldX;
				desY = 2*SG.getPlayer().getWorldY() - worldY;
			}
			else if(worldX < 0 || worldY < 0 || worldX >= SurvivalGame.WORLD_WIDTH || worldY >= SurvivalGame.WORLD_HEIGHT) {
					int[] dir = null;
					if(worldX < 0) dir = dirArr[0];
					else if(worldY < 0) dir = dirArr[1];
					else if(worldX >= SurvivalGame.WORLD_WIDTH) dir = dirArr[2];
					else  dir = dirArr[3];
					desX = worldX + dir[0]*Main.TILES_SIZE;
					desY = worldY + dir[1]*Main.TILES_SIZE;
				}
			else {
				int [] dir;
				int index = (int)(Math.random()*dirSize);
				index = (index + 1)%dirSize;
				dir = dirArr[index];
				desX = worldX + dir[0]*Main.TILES_SIZE;
				desY = worldY + dir[1]*Main.TILES_SIZE;
			}
			endRestTime = System.nanoTime() + (long)2e9; 
		}
		double u1 = desX - worldX;
		double u2 = desY - worldY;
		if(0 > speed*u1) left = true;
		if(0 < speed*u1) left = false;
		worldX += speed*u1;
		worldY += speed*u2;				
	}
	private double distanceToPlayer() {
		return Math.sqrt(Math.pow(SG.getPlayer().getWorldX() - worldX, 2) 
				+ Math.pow(SG.getPlayer().getWorldY() - worldY, 2));
	}
	public void render(Graphics2D g2) {
		int screenX = (int)worldX - SG.getPlayer().getWorldX() + SG.getPlayer().getScreenX();
		int screenY = (int)worldY - SG.getPlayer().getWorldY() + SG.getPlayer().getScreenY();
		if(screenX >= -5*Main.TILES_SIZE && screenY >= -5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_WIDTH + 5*Main.TILES_SIZE 
			&& screenY <= Main.GAME_HEIGHT + 5*Main.TILES_SIZE)
			if(left) g2.drawImage(leftImg, screenX, screenY, (int)(Main.TILES_SIZE *1.5) , (int)(Main.TILES_SIZE *1.5), null);
			else g2.drawImage(rightImg, screenX, screenY, (int)(Main.TILES_SIZE *1.5) , (int)(Main.TILES_SIZE *1.5), null);
	}
}


