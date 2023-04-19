package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.*;
import root.*;

public class Blade extends Creature {
	BufferedImage img;
	private double startX, startY, speedCoef, length, dirx, diry;
	public Blade(SurvivalGame SG, double startX, double startY, double speedCoef, double length, double dirx, double diry) {
		super(SG, startX, startY, "Blade", 
				new Rectangle((int)(3*Main.SCALE), (int)(3*Main.SCALE), (int)(30*Main.SCALE), (int)(30*Main.SCALE)));
		this.startX = startX;
		this.startY = startY;
		this.speedCoef = speedCoef;
		this.length = length;
		this.dirx = dirx;
		this.diry = diry;
		img = IOHandler.getImage("blade.png");
	}
	public void update() {
		if(Math.sqrt(Math.pow(worldX - startX, 2) + Math.pow(worldY - startY, 2))> length)
		{
			this.startX = worldX;
			this.startY = worldY;
			dirx = -dirx;
			diry = -diry;
		}
		worldX += speedCoef*dirx;
		worldY += speedCoef*diry;
	}
	public void render(Graphics2D g2) {
		int screenX = (int)worldX - SG.getPlayer().getWorldX() + SG.getPlayer().getScreenX();
		int screenY = (int)worldY - SG.getPlayer().getWorldY() + SG.getPlayer().getScreenY();
		if(screenX >= -5*Main.TILES_SIZE && screenY >= -5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_WIDTH + 5*Main.TILES_SIZE 
			&& screenY <= Main.GAME_HEIGHT + 5*Main.TILES_SIZE) {
			g2.drawImage(img, screenX, screenY, (int)(Main.TILES_SIZE *1.5) , (int)(Main.TILES_SIZE *1.5), null);
		}			
	}
}