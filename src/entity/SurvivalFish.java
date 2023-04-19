package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Color;
import main.Main;
import main.SurvivalGame;
import root.*;

public class SurvivalFish extends Creature{
	private long endInvin; //Stack
	private BufferedImage rightImg, leftImg;
	private String deathMessage = "";
	private int screenX, screenY;
	private int speed;
	private int point;
	private boolean left;
	public SurvivalFish(SurvivalGame SG, int worldX, int worldY) {
		super(SG, worldX, worldY, "Struggler", new Rectangle(0, 0, (int)(30*Main.SCALE), (int)(22*Main.SCALE)));
		screenX = Main.GAME_WIDTH/2 - Main.TILES_SIZE/2;
		screenY = Main.GAME_HEIGHT/2 - Main.TILES_SIZE/2;
		speed = (int)(4*Main.SCALE);
		point = 0;
		left = true; 
		getPlayerImage();
	}
	private void getPlayerImage() {
		leftImg = IOHandler.getImage("yellow_fish_left.png");
		rightImg = IOHandler.getImage("yellow_fish_right.png");;
		leftImg = leftImg.getSubimage(12, 20, 40, 26);
		rightImg = rightImg.getSubimage(12, 20, 40, 26);
	}
	public void update() {
		double[][] corner = {{worldX + hitbox.x, worldY + hitbox.y},
				 {worldX + hitbox.width + hitbox.x, worldY + hitbox.height + hitbox.y}};
		int[] nextTile = null;
		double[] delta = new double[2];
		Pair<Boolean, Integer> bip = new Pair<Boolean, Integer>(false, 0); 
		int newPoint = 0;
		if(SKeyHandler.shiftPressed) speed = (int)(speed*1.5);
		/*Check collision with other creatures*/
		if(endInvin < System.nanoTime()) { 
			String killer = SG.getSpawner().traceOnSpawner(corner);
			if(killer != "NN") {
				SG.setLose(true);
				SG.getPlayer().setDeathMessage("You got killed by " + killer);
			}
		}
		//Check collision with tiles
		if(SKeyHandler.leftPressed)
			delta[0] -= speed;
		else if(SKeyHandler.rightPressed)
			delta[0] += speed;
		if(SKeyHandler.upPressed)
			delta[1] -= speed;
		else if(SKeyHandler.downPressed) 
			delta[1] += speed;
		nextTile = SG.getMap().traceMap(corner, delta[0], 0, true);
		if(delta[0] < 0) {
			left = true;
			for(int i = 0; i <= 2; i++) {
				bip = checkTile(nextTile[(i + 6)%8]);
				if(!bip.first) 
					delta[0] = 0;
				newPoint += bip.second;
			}
		}
		else if(delta[0] > 0) {
			left = false;
			for(int i = 2; i <= 4; i++) {
				bip = checkTile(nextTile[i]);
				if(!bip.first) 
					delta[0] = 0;
				newPoint += bip.second;
			}
		}
		nextTile = SG.getMap().traceMap(corner, 0, delta[1], true);
		if(delta[1] < 0) for(int i = 0; i <= 2; i++) {
			bip = checkTile(nextTile[i]);
			if(!bip.first) 
				delta[1] = 0;
			newPoint += bip.second;
		}
		else for(int i = 4; i <= 6; i++) {
			bip = checkTile(nextTile[i]);
			if(!bip.first) 
				delta[1] = 0;
			newPoint += bip.second;
		}
		worldX += delta[0];
		worldY += delta[1];
		if(SG.getGoldfish().distanceToPlayer() < 5*Main.TILES_SIZE) {
			newPoint *= 2;
		}
		if((this.point + newPoint)/100 > this.point/100) {
			endInvin = (long) Math.max(endInvin + 10e9, System.nanoTime() + 10e9);
		}
		this.point += newPoint;
		if(SKeyHandler.shiftPressed) speed = (int)(speed/1.5);
	}
	/*private method for update: return collision & point*/ 
	private Pair<Boolean, Integer> checkTile(int code) {
		Pair<Boolean, Integer> bip = new Pair<Boolean, Integer>(false, 0);
		if(SG.getMap().tileIsConsumable(code)) {
			bip.first = true;
			bip.second += 1;
		}
		else if(SG.getMap().tileIsMoveable(code)) {
			bip.first = true;
		}
		else if(SG.getMap().tileIsTrap(code) && endInvin < System.nanoTime()) {
			bip.first = false;
			bip.second = 0;
			SG.getPlayer().setDeathMessage("You got trapped");
			SG.setLose(true);
		}
		return bip;
	}
	public void render(Graphics2D g2) {
		BufferedImage image = null;
		if (left) image = leftImg;
		else image = rightImg;
		if(endInvin > System.nanoTime()){
			long remainingTime = (endInvin - System.nanoTime())/(long)(1e9);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));	
			g2.setColor(Color.WHITE);
			g2.drawString(Long.toString(remainingTime), screenX + (int)(15*Main.SCALE), screenY + (int)(40*Main.SCALE));
		}
		g2.drawImage(image, screenX, screenY, (int)(40*Main.SCALE), (int)(26*Main.SCALE), null); //orriginal size
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	/*Get set methods*/
	public int getWorldX() {
		return (int)worldX;
	}
	public int getWorldY() {
		return (int)worldY;
	}
	public int getScreenX() {
		return screenX;
	}
	public int getScreenY() {
		return screenY;
	}
	public String getDeathMessage() {
		return deathMessage;
	}
	public void setDeathMessage(String str) {
		deathMessage = str;
	}
	public int getPoint() {
		return point;
	}
	public void giveInvincibility(long nanosecond) { //stacked
		 endInvin = (long) Math.max(endInvin + 10e9, System.nanoTime() + 10e9);
	}
}
