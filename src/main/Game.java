package main;

import java.awt.Graphics;
import java.util.*;

import entity.Fish;
import entity.Lock;
import entity.Hint;
import map.Atlas;
import root.MoveHandler;
import root.Pair;

public class Game implements Runnable {

	private Screen screen;
	private Panel panel;
	private Atlas map;
	private Fish fish1, fish2;
	private Lock lock;
	private ArrayList<Hint> lstHint;

	//config game
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.2f;
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
		fish1 = new Fish(100*SCALE, 200*SCALE, 64*SCALE, 64*SCALE, "red");
		fish2 = new Fish(100*SCALE, 500*SCALE, 64*SCALE, 64*SCALE, "blue");
		fish1.setMapData(map.getMapData());
		fish2.setMapData(map.getMapData());
		
		String[] question = new String[] {
			"The first character of key is S",
			"The key contains character K",
			"The key relevants to a big fish",
			"The key contains character H",
			"More than 30000 teaths",
			"Living in the ocean",
			"... Tank"
		};
		
		lstHint = new ArrayList<Hint>();
		int i =0;
		for(var q: map.getLstPosQuestion()) {
//			System.out.println(q.first+" "+q.second);
			lstHint.add(new Hint(TILES_SIZE*q.first, TILES_SIZE*q.second,TILES_SIZE ,TILES_SIZE,question[i]));
			i++;
		}
		lock = new Lock(TILES_SIZE*map.getPosLockX(), TILES_SIZE*map.getPosLockY(), TILES_SIZE*4, TILES_SIZE*2);
		

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
		for(var q: lstHint) {
			q.render(g, xMapOffset);
		}
//		lock.render(g,xMapOffset);
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
				fish1.updateLockStatus(lock.isUnlock());
				fish2.updateLockStatus(lock.isUnlock());
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