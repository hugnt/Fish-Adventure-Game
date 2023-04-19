package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.*;
import root.*;

public class BlueGuard extends Creature {
	private BufferedImage image;
	private double centerX, centerY, radius, alpha, omega;
	public BlueGuard(SurvivalGame SG, double centerX, double centerY, double radius, double alpha, double omega) {
		super(SG, radius*Math.cos(alpha) + centerX, radius*Math.sin(alpha) + centerY, "Blue Guard", 
				new Rectangle((int)(4*Main.SCALE), (int)(4*Main.SCALE), (int)(36*Main.SCALE), (int)(36*Main.SCALE)));
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.omega = omega;
		this.alpha = alpha;
		image = IOHandler.getImage("blue_guard.png");
	}
	public void update() {
		if(alpha > 2*Math.PI) alpha -= 2*Math.PI;
		alpha += omega;
		worldX = radius*Math.cos(alpha) + centerX;
		worldY = radius*Math.sin(alpha) + centerY;
	}
	public void render(Graphics2D g2) {
		int screenX = (int)worldX - SG.getPlayer().getWorldX() + SG.getPlayer().getScreenX();
		int screenY = (int)worldY - SG.getPlayer().getWorldY() + SG.getPlayer().getScreenY();
		if(screenX >= -5*Main.TILES_SIZE && screenY >= -5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_WIDTH + 5*Main.TILES_SIZE 
			&& screenX <= Main.GAME_HEIGHT + 5*Main.TILES_SIZE)
			g2.drawImage(image, screenX, screenY, (int)(Main.TILES_SIZE *1.5) , (int)(Main.TILES_SIZE *1.5), null); //orriginal size
			
	}
}