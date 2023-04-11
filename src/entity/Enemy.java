package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
	
	
	//constant
	private float denta = 1.0f;
	float dentaX = 0;
	
	//Collision
	private MoveHandler moveHandler;
	public static int[][] mapData;
	
	public Enemy(float x, float y, int width, int height, String img) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enemyImg = IOHandler.getImage(img);
		enemyImg = enemyImg.getSubimage(0, 0, width, height);
		moveHandler = new MoveHandler();
	}
	
	public void render(Graphics g, int xMapOffset) {
		g.drawImage(enemyImg,(int)x - xMapOffset, (int)y, Game.TILES_SIZE, Game.TILES_SIZE, null);
	}
	
	public void updatePosition() {
		//collision
		if(moveHandler.isValidStep(x + denta, y,(float)Game.TILES_SIZE- 20 * Game.SCALE, (float)Game.TILES_SIZE- 12 * Game.SCALE, mapData, false)) {
			x += denta;
		}
		else {
			denta = -denta;
		}
		
		
	}

	
	


	
	
	
}
