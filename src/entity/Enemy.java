package entity;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Main;
import root.IOHandler;
import root.MoveHandler;
import root.Pair;

public class Enemy {
	private float x, y;
	private BufferedImage enemyImg;
	private BufferedImage enemyImgOri;
	private BufferedImage enemyImgFlip;
	
	
	//constant
	private float denta = 1.0f*Main.SCALE;
	private float dentaX = 0;
	private float dentaY = 0;
	
	
	//Collision
	private MoveHandler moveHandler;
	public static int[][] mapData;
	private boolean touchFish;
	
	private ArrayList<Pair<Float, Float>> fishPos;
	
	public Enemy(float x, float y, int width, int height, String img, boolean horizon) {
		super();
		this.x = x;
		this.y = y;
		this.enemyImg = IOHandler.getImage(img);

		if(horizon) dentaX = denta;
		else dentaY = denta;
		enemyImg = enemyImg.getSubimage(0, 0, width, height);
		
		//flip
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-enemyImg.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		enemyImgFlip = op.filter(enemyImg, null);
		
		enemyImgOri = enemyImg;
		
		moveHandler = new MoveHandler();
		moveHandler.setEnemyHandle(true);
		fishPos = new ArrayList<Pair<Float, Float>>();
	}
	
	public void render(Graphics g, int xMapOffset) {
		g.drawImage(enemyImg,(int)x - xMapOffset, (int)y, Main.TILES_SIZE, Main.TILES_SIZE, null);
		//g.setColor(Color.BLUE);
		//g.drawRect((int)x - xMapOffset, (int)y, Main.TILES_SIZE, Main.TILES_SIZE);
	}
	
	public void updatePosition() {
		//collision
		if(dentaX>0) enemyImg = enemyImgOri;
		else if(dentaX<0)  enemyImg = enemyImgFlip;
		if(moveHandler.isValidStep(x + dentaX, y+dentaY,(float)Main.TILES_SIZE, (float)Main.TILES_SIZE-10*Main.SCALE, mapData, false)) {
			x += dentaX;
			y += dentaY;
		}
		else {
			dentaX = -dentaX;
			dentaY = -dentaY;
		}
		if(moveHandler.isTouchFish()) {
			touchFish=true;
		}
		else {
			touchFish=false;
		}
		
	
	}

	public void setFishPos(ArrayList<Pair<Float, Float>> fishPos) {
		this.fishPos = fishPos;
		moveHandler.setFishPos(fishPos);
	}

	public boolean isTouchFish() {
		return touchFish;
	}

	
	
	
}
