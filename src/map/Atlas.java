
package map;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import root.IOHandler;
import root.Pair;

public class Atlas {
	
	private int[][] mapData;
	private BufferedImage[] mapSprites;
	private final String SPRITES_URL = "map_sprites05.png"; 
	private final String MAP_URL = "map005.png"; 
	
	
	//other objects
	private int posLockX;
	private int posLockY;
	private ArrayList<Pair<Integer, Integer>> lstPosQuestion;
	
	
	public Atlas() {
		importMapSprites();
		create();
	}
	
	private void importMapSprites() {
		mapSprites = new BufferedImage[49];//7 height x 7 width
		BufferedImage img = IOHandler.getImage(SPRITES_URL);
		//spread 2d arr -> 1d arr
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				int index = i*7 + j;
				System.out.println(index);
				mapSprites[index] = img.getSubimage(j*512, i*512, 512 , 512);
				
			}
		}
	}
	
	private int[][] generate() {
		int[][] mapMaker = new int[10][10];
		
		return mapMaker;
	}
	
	//can pass create() wwith a matrix to generate map
	private void create() {
		BufferedImage img = IOHandler.getImage(MAP_URL);
		
		mapData = new int[img.getHeight()][img.getWidth()];
		
		lstPosQuestion = new ArrayList<Pair<Integer, Integer>>();
		int flag = 0;
		
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));//get per pixel tile's color of img
				int value = color.getRed();
				System.out.println("value: "+value);
				if(value >= 49)
				{
					value = 6;
				}
				
				//lock
				if(value == 18&& flag == 0) {
//					System.out.println("pos: "+j+", "+i);
					flag = 1;
					posLockX = j;
					posLockY = i;
				}
				
				//question
				if(value == 19) {
					lstPosQuestion.add(new Pair(j, i));
				}
				
				//just save the color red like a spriteID 
				mapData[i][j] = value;
			}
		}
	}
	

	
	public void render(Graphics g, int xMapOffset) {
		for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
			for (int j = 0; j < mapData[0].length; j++) {
				int index = getSpriteIndex(j, i);
				g.drawImage(mapSprites[index], Game.TILES_SIZE * j - xMapOffset , Game.TILES_SIZE * i,
							Game.TILES_SIZE, Game.TILES_SIZE,null);
			}
		}
		
	}

	public ArrayList<Pair<Integer, Integer>> getLstPosQuestion(){
		return lstPosQuestion;
	}
	
	public int getPosLockX() {
		return posLockX;
	}

	public int getPosLockY() {
		return posLockY;
	}

	public int getSpriteIndex(int x, int y) {
		return mapData[y][x];
	}
	
	public int[][] getMapData() {
		return mapData;
	}

	

	
	
	
}