package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import main.*;
import root.*;
public class Spawner {
	private ArrayList<Creature> creatures;
	public Spawner(SurvivalGame SG, double bladePerc, double blueGuardPerc, double chaserPerc){
		creatures = new ArrayList<Creature>();
		for(int i = 1; i < SurvivalGame.WORLD_ROW - 1; i++) {
			for(int j = 1; j < SurvivalGame.WORLD_COL - 1; j++) {
				double k = Math.random();
				if(k < bladePerc) {
					if(k < 0.25*bladePerc) {
						creatures.add(new Blade(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, Math.max(0.5, Math.random())*4, Math.max(Math.random()*9*Main.TILES_SIZE, 3*Main.TILES_SIZE), 1d, 1d));
					}
					else if(k < 0.5*bladePerc) {
						creatures.add(new Blade(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, Math.max(0.5, Math.random())*4,Math.max(Math.random()*9*Main.TILES_SIZE, 3*Main.TILES_SIZE), -1d, 1d));
					}
					else if(k < 0.75*bladePerc) {
						creatures.add(new Blade(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, Math.max(0.5, Math.random())*4,Math.max(Math.random()*9*Main.TILES_SIZE, 3*Main.TILES_SIZE), 0, 1d));
					}
					else {
						creatures.add(new Blade(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, Math.max(0.5, Math.random())*4, Math.max(Math.random()*9*Main.TILES_SIZE, 3*Main.TILES_SIZE), 1d, 0));
					}
				}
				if(Math.random() < blueGuardPerc) {
					creatures.add(new BlueGuard(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, Math.max(Math.random()*9*Main.TILES_SIZE, 3*Main.TILES_SIZE), 0.00, Math.max(Math.random()/100*6, 0.04)));
				}
				if(Math.random() < chaserPerc) {
					creatures.add(new Chaser(SG, j*Main.TILES_SIZE, i*Main.TILES_SIZE, 0.03));
				}
			}
		}
	}
	public String traceOnSpawner(double[][] objectCorner) {
		double[][] corner = new double[2][2];
		for(Creature crt: creatures){
			corner[0][0] = crt.worldX + crt.hitbox.x;
			corner[0][1] = crt.worldY + crt.hitbox.y;
			corner[1][0] = crt.worldX + crt.hitbox.width + crt.hitbox.x;
			corner[1][1] = crt.worldY + crt.hitbox.height + crt.hitbox.y;
			if(Intersection.check(objectCorner, corner)) {
				return crt.getSpecies();
			}
		}
		return "NN"; 
	}
	public void update() {
		for(Creature crt: creatures) {
			crt.update();
		}
	}
	public void render(Graphics2D g2) {
		for(Creature crt: creatures) {
			crt.render(g2);
		}
	}
}
