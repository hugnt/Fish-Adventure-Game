//package entity;
//
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//import main.SurvivalGame;
//import root.IOHandler;
//
//public class Blade extends Entity {
//	public final boolean isEnemy = true;
//	public int length;
//	public int startX, startY;
//	public int speed = 3;
//	int[] dir;
//	private BufferedImage image;
//	public Blade(SurvivalGame SG, int startX, int startY, int length, int [] dir) {
//		species = "Blade";
//		this.SG = SG;
//		this.startX = startX;
//		this.startY = startY;
//		this.worldX = startX;
//		this.worldY = startY;
//		this.length = length;
//		this.dir = dir;
//		hitBox = new Rectangle(3, 3, 30, 30);
//		image = IOHandler.getImage("blade");
//	}
//	public void update() {
//		if(dis() > length){
//			this.startX = (int)x;
//			this.startY = (int)y;
//			dir1 = -dir1;
//			dir2 = -dir2;
//		}
//		
//		x += speed*dir1;
//		y += speed*dir2;
//	}
//	private double dis() {
//		return Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
//	}
//	public void draw(Graphics2D g2) {
//			int screenX = (int)x - SG.playerWorldX() + SG.playerScreenX();
//			int screenY = (int)y - SG.playerWorldY() + SG.playerScreenY();
//			//iff screenX
//			if(screenX >= -5*SG.tileSize() && screenY >= -5*SG.tileSize() 
//					&& screenX <= SG.screenWidth() + 5*SG.tileSize() && screenX <= SG.screenHeight() + 5*SG.tileSize()) {
//				g2.drawImage(image, screenX, screenY, (int)(SG.tileSize() *1.5) , (int)(SG.tileSize() *1.5), null);
//			}
//				 //orriginal size
//			
//	}
//}