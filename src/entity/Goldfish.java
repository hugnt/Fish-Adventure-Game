package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Main;
import main.SurvivalGame;
import root.IOHandler;
import root.SKeyHandler;

//if close --> x1.5 point
public class Goldfish extends Creature {
	public double speed;
	private BufferedImage leftImg, rightImg;
	private boolean left, up;
	public Goldfish(SurvivalGame SG, int worldX, int worldY, double speed) {
		super(SG, worldX, worldY, "Gold fish", new Rectangle((int)(6*Main.SCALE), (int)(10*Main.SCALE), (int)(20*Main.SCALE), (int)(13*Main.SCALE)));
		this.speed = speed;
		leftImg = IOHandler.getImage("yellow_fish_left.png");
		rightImg = IOHandler.getImage("yellow_fish_right.png");
	}
	public void update() {
		int[][] corner = {{worldX + hitbox.x, worldY + hitbox.y},
						{worldX + hitbox.x + hitbox.width, worldY + hitbox.y + hitbox.height}};	
		int[] nextTile = null;
		int u1 = SG.playerWorldX() - worldX;
		int u2 = SG.playerWorldY() - worldY;
		if(u1 < 0) left = true;
		else if(u1 > 0) left = false;
		if(u2 < 0) up = true;
		else if(u2 > 0) up = false;
		nextTile = SG.traceMap(corner, (int)(speed*u1), (int)(speed*u2), false);
		worldX += (int)(speed*u1);
		worldY += (int)(speed*u2);
	}
	public void render(Graphics2D g2) {
			int screenX = worldX - SG.playerWorldX() + SG.playerScreenX();
			int screenY = worldY - SG.playerWorldY() + SG.playerScreenY();
			if(screenX >= -5*Main.TILES_SIZE && screenY >= -5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_WIDTH + 5*Main.TILES_SIZE && screenX <= Main.GAME_HEIGHT + 5*Main.TILES_SIZE) {
				if(left) g2.drawImage(leftImg, screenX, screenY, Main.TILES_SIZE, Main.TILES_SIZE, null);
				else g2.drawImage(rightImg, screenX, screenY, Main.TILES_SIZE, Main.TILES_SIZE, null);
			}
	}
}
