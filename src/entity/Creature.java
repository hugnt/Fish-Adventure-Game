package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.SurvivalGame;

public abstract class Creature {
	protected SurvivalGame SG;
	protected String species;
	protected Rectangle hitbox;
	protected double worldX, worldY;
	Creature(SurvivalGame SG, double worldX, double worldY, String SP, Rectangle H) {
		this.SG = SG;
		this.worldX = worldX;
		this.worldY = worldY;
		this.species = SP;
		this.hitbox = H;
	}
	public void render(Graphics2D g2) {};
	public void update() {};
	public String getSpecies() {
		return species;
	}
}

