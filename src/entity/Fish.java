package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import root.IOHandler;
import root.MoveHandler;
import root.Pair;

public class Fish{
	private float x, y;
	private float width, height;
	private BufferedImage fishImg;
	
	//move;
	private boolean up = false;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	
	//constant
	private final float denta = 2.0f;
	
	//Collision
	private MoveHandler moveHandler;
	private Rectangle2D.Float hitbox;
	private int[][] mapData;
	private float xOffset = 20 * Game.SCALE;
	private float yOffset = 12 * Game.SCALE;
	
	//Key lock 
	private boolean unlock; 
	private boolean touchLock;
	
	//hints
	private Pair<Integer, Integer> hintPos;
	private boolean touchHint;
	
	public Fish(float x, float y, float width, float height, String color) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		hitbox = new Rectangle2D.Float(x - xOffset, y - yOffset, 40* Game.SCALE, 26* Game.SCALE);
		
		String fishColor = getFishColor(color);
		fishImg = IOHandler.getImage(fishColor);
		fishImg = fishImg.getSubimage(12, 20, 40, 26);
		
		moveHandler = new MoveHandler();
		hintPos = new Pair<Integer, Integer> (0, 0);
	}
	
	private String getFishColor(String color) {
		if(color == "red") return "red_fish.png";
		else if(color == "blue") return "blue_fish.png";
		return "yellow_fish.png";
	}
	
	public void render(Graphics g,int xMapOffset) {
		g.drawImage(fishImg,(int)hitbox.x - xMapOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height, null);
		g.setColor(Color.PINK);
		//g.drawRect((int)hitbox.x - xMapOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}
	
	public void updatePosition() {
		float dentaX = 0, dentaY = 0;
		
		if (left && !right) dentaX -= denta;
		else if (right && !left) dentaX += denta;
		
		if (up && !down) dentaY -= denta;
		else if (down && !up) dentaY += denta;
		

		//collision
		if(moveHandler.isValidStep(hitbox.x + dentaX, hitbox.y + dentaY, hitbox.width-10, hitbox.height, mapData, unlock)) {
			hitbox.x += dentaX;
			hitbox.y += dentaY;
		}
		//check touch lock
		if(moveHandler.isTouchLock()) {
			touchLock=true;
		}
		else {
			touchLock=false;
		}
		
		//check touch hints
		if(moveHandler.isTouchHint()) {
			touchHint=true;
			int xHint = moveHandler.getHintPos().first;
			int yHint = moveHandler.getHintPos().second;
			hintPos = new Pair<Integer, Integer>(xHint, yHint);
		}
		else {
			touchHint=false;
		}
		
		
	}
	
	public void updateLockStatus(boolean unlock) {
		this.unlock = unlock;
		//System.out.println("isUnlock:"+unlock);
	}
	
	
	//getters & setters
	public Pair<Integer, Integer> getHintPos() {
		return hintPos;
	}
	
	public boolean isTouchLock() {
		return touchLock;
	}
	public boolean isTouchHint() {
		return touchHint;
	}
	
	public int[][] getMapData() {
		return mapData;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle2D.Float hitbox) {
		this.hitbox = hitbox;
	}

	public void setMapData(int[][] mapData) {
		this.mapData = mapData;
	}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}


	
	
	
}
