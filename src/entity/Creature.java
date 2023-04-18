package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.SurvivalGame;

public abstract class Creature {
	protected SurvivalGame SG;
	protected String species;
	protected Rectangle hitbox;
	protected int worldX, worldY;
	Creature(SurvivalGame SG, int worldX, int worldY, String SP, Rectangle H) {
		this.SG = SG;
		this.worldX = worldX;
		this.worldY = worldY;
		this.species = SP;
		this.hitbox = H;
	}
	protected void draw(Graphics2D g2) {};
	protected void update() {};
}

