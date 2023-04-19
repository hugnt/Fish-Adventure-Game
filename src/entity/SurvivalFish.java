package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;

import main.Main;
import main.SurvivalGame;
import root.*;
import root.Pair;
public class SurvivalFish extends Creature{
	private SimpleTimer invin; //Not stack
	private BufferedImage rightImg, leftImg;
	private String deathMessage = "";
	private int screenX, screenY;
	private int speed;
	private int point;
	private boolean left, right;
	public SurvivalFish(SurvivalGame SG, int worldX, int worldY) {
		super(SG, worldX, worldY, "Struggler", new Rectangle(0, 0, (int)(28*Main.SCALE), (int)(20*Main.SCALE)));
		invin = new SimpleTimer();
		screenX = Main.GAME_WIDTH/2 - Main.TILES_SIZE/2;
		screenY = Main.GAME_HEIGHT/2 - Main.TILES_SIZE/2;
		speed = (int)(4*Main.SCALE);
		point = 0;
		left = true; right = false;
		getPlayerImage();
	}
	//Supporting methods for constructor
	private void getPlayerImage() {
		leftImg = IOHandler.getImage("yellow_fish_left.png");
		rightImg = IOHandler.getImage("yellow_fish_right.png");;
		leftImg = leftImg.getSubimage(12, 20, 40, 26);
		rightImg = rightImg.getSubimage(12, 20, 40, 26);
	}
	//Get set methods
	public int getWorldX() {
		return worldX;
	}
	public int getWorldY() {
		return worldY;
	}
	public int getScreenX() {
		return screenX;
	}
	public int getScreenY() {
		return screenY;
	}
	public void setDeathMessage(String str) {
		deathMessage = str;
	}
	public String getDeathMessage() {
		return deathMessage;
	}
	public int getPoint() {
		return point;
	}
//	public void giveInvincibility(long nanosecond) { //stacked
//		if(invin.timeLeft() < nanosecond) {
//			invin.set(nanosecond);
//			invin.start();
//		}
//	}
	public void update() {
//		if(invin.timeLeft() == 0) {
//			SG.entities.collisionWPlayer();
//		}
		if(SKeyHandler.upPressed || SKeyHandler.downPressed || SKeyHandler.leftPressed || SKeyHandler.rightPressed) {
			if(SKeyHandler.shiftPressed) speed = (int)(speed*1.5);
			int[][] corner = {{worldX + hitbox.x, worldY + hitbox.y},
							 {worldX + hitbox.width + hitbox.x, worldY + hitbox.height + hitbox.y}};
			int[] nextTile = null;
			int[] delta = new int[2];
			Pair<Boolean, Integer> bip = new Pair<Boolean, Integer>(false, 0);
			int newPoint = 0;
			if(SKeyHandler.leftPressed ) {
				left = true;
				right = false;
				nextTile = SG.traceMap(corner, -speed, 0, true);
				for(int i = 0; i <= 2; i++) {
					bip = checkTile(nextTile[(i + 6)%8]);
					if(!bip.first) break;
				}
				if(bip.first) delta[0] = -speed;
				newPoint += bip.second;
			}
			else if(SKeyHandler.rightPressed) {
				right = true;
				left = false;
				nextTile = SG.traceMap(corner, speed, 0, true);
				for(int i = 2; i <= 4; i++) {
					bip = checkTile(nextTile[i]);
					if(!bip.first) break;
				}
				if(bip.first) delta[0] = speed;
				newPoint += bip.second;	
			}	
			if(SKeyHandler.upPressed) {
				nextTile = SG.traceMap(corner, 0, -speed, true);
				for(int i = 0; i <= 2; i++) {
					bip = checkTile(nextTile[i]);
					if(!bip.first) break;
				}
				if(bip.first) delta[1] = -speed;
				newPoint += bip.second;
			}
			else if(SKeyHandler.downPressed) {
				nextTile = SG.traceMap(corner, 0, speed, true);
				for(int i = 4; i <= 6; i++) {
					bip = checkTile(nextTile[i]);
					if(!bip.first) break;
				}
				if(bip.first) delta[1] = speed;
				newPoint += bip.second;
			}
			worldX += delta[0];
			worldY += delta[1];
			this.point += newPoint;
			if(SKeyHandler.shiftPressed) speed = (int)(speed/1.5);
		}
	}
	/*private method for update: return collision & point*/ 
	private Pair<Boolean, Integer> checkTile(int code) {
		Pair<Boolean, Integer> bip = new Pair<Boolean, Integer>(false, 0);
		if(SG.tileIsConsumable(code)) {
			bip.first = true;
			bip.second = 10;
		}
		else if(SG.tileIsMoveable(code)) {
			bip.first = true;
			bip.second = 0;
		}
		else if(SG.tileIsTrap(code)) {
			bip.first = false;
			bip.second = 0;
			SG.setDeathMessage("You got trapped.");
			SG.setLose(true);
		}
		return bip;
	}
	public void render(Graphics2D g2) {
		BufferedImage image = null;
		if (left) image = leftImg;
		else if (right) image = rightImg;
		if(invin.timeLeft() > 0){
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));	
			g2.setColor(Color.WHITE);
			g2.drawString(Long.toString(invin.timeLeftS()), screenX + (int)(15*Main.SCALE), screenY + (int)(40*Main.SCALE));
		}
		g2.drawImage(image, screenX, screenY, (int)(40*Main.SCALE), (int)(26*Main.SCALE), null); //orriginal size
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
