package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Game;
import menu.ResultMenu;
import root.IOHandler;
import root.MoveHandler;
import root.Pair;

public class Enemy {
	private float x, y;
	private float width, height;
	private BufferedImage enemyImg;
	private BufferedImage enemyImgOri;
	private BufferedImage enemyImgFlip;
	
	
	//constant
	private float denta = 1.0f*Game.SCALE;
	private float dentaX = 0;
	private float dentaY = 0;
	private boolean horizon = true;
	
	
	//Collision
	private MoveHandler moveHandler;
	public static int[][] mapData;
	private boolean touchFish;
	
	private ArrayList<Pair<Float, Float>> fishPos;
	
	public Enemy(float x, float y, int width, int height, String img, boolean horizon) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enemyImg = IOHandler.getImage(img);
		this.horizon = horizon;
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
		g.drawImage(enemyImg,(int)x - xMapOffset, (int)y, Game.TILES_SIZE, Game.TILES_SIZE, null);
		//g.setColor(Color.BLUE);
		//g.drawRect((int)x - xMapOffset, (int)y, Game.TILES_SIZE, Game.TILES_SIZE);
	}
	
	public void updatePosition() {
		//collision
		if(dentaX>0) enemyImg = enemyImgOri;
		else if(dentaX<0)  enemyImg = enemyImgFlip;
		if(moveHandler.isValidStep(x + dentaX, y+dentaY,(float)Game.TILES_SIZE, (float)Game.TILES_SIZE-10*Game.SCALE, mapData, false)) {
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
	public Pair<Float, Float> getPos(){
		return new Pair(x, y);
	}

	public void setFishPos(ArrayList<Pair<Float, Float>> fishPos) {
		this.fishPos = fishPos;
		moveHandler.setFishPos(fishPos);
	}

	public boolean isTouchFish() {
		return touchFish;
	}

	
	
	
}