package main;

import java.awt.Graphics;

import entity.Fish;
import map.Atlas;
import root.MoveHandler;

public class Game implements Runnable {

	private Screen screen;
	private Panel panel;
	private Atlas map;
	private Fish fish1, fish2;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 22;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	// Events
	private MoveHandler movingEvent;

	// update view thread
	private Thread viewUpdateThread;
	private final int FPS_SET = 120;

	//Extend map
	private int xMapOffset = 0;
	private int leftBorder = (int) (0.4 * GAME_WIDTH);
	private int rightBorder = (int) (0.6 * GAME_WIDTH);
	
	
	public Game() {
	};

	public void start() {
		map = new Atlas();
		fish1 = new Fish(100, 200, 64*SCALE, 64*SCALE, "red");
		fish2 = new Fish(100, 500, 64*SCALE, 64*SCALE, "blue");
		fish1.setMapData(map.getMapData());
		fish2.setMapData(map.getMapData());

		panel = new Panel(this, GAME_WIDTH, GAME_HEIGHT);// create panel
		screen = new Screen(panel);// Create window
		
		
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		movingEvent = new MoveHandler(fish1, fish2, panel);
		panel.addKeyListener(movingEvent);
		panel.requestFocus();
		
		// thread
		viewUpdateThread = new Thread(this);
		viewUpdateThread.start();
		
	}

	public void end() {

	}

	public void render(Graphics g) {
		map.render(g,xMapOffset);
		fish1.render(g, xMapOffset);
		fish2.render(g, xMapOffset);
	}
	
	public void extendMap() {
		int mapTilesWide = map.getMapData()[0].length;
		int maxTilesOffset = mapTilesWide - TILES_IN_WIDTH;
		int maxMapOffsetX = maxTilesOffset * TILES_SIZE;
		
		int fishPosX = (int)fish1.getHitbox().x;
		int diff = fishPosX - xMapOffset;
		if(diff > rightBorder) {
			xMapOffset += diff - rightBorder;
		}
		else if(diff < leftBorder) {
			xMapOffset += diff - leftBorder;
		}
		if(xMapOffset > maxMapOffsetX) {
			xMapOffset = maxMapOffsetX;
		}
		else if(xMapOffset < 0) {
			xMapOffset = 0;
		}
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		long lastCheck =  System.currentTimeMillis();
		
		double deltaF = 0;
		while(true) {
		
			long currentTime = System.nanoTime();
	
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaF >= 1) {
				fish1.updatePosition();
				fish2.updatePosition();
				extendMap();
				panel.repaint();
				frames++;
				deltaF --;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: "+ frames);
				frames = 0;
			}
		}
	}
		
}