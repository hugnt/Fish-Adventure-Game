package map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import root.IOHandler;

public class Atlas {
	
	private int[][] mapData;
	private BufferedImage[] mapSprites;
	private final String SPRITES_URL = "map_sprites3.png"; 
	private final String MAP_URL = "map004.png"; 
	
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
		
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));//get per pixel tile's color of img
				int value = color.getRed();
				if(value >= 49) value = 6;
				//just save the color red like a spriteID 
				mapData[i][j] = value;
			}
		}
	}
	
	public int getSpriteIndex(int x, int y) {
		return mapData[y][x];
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

	public int[][] getMapData() {
		return mapData;
	}

	

	
	
	
}
