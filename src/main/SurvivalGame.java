package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import root.*;
import entity.*;
import map.SurvivalMap;
import menu.PauseMenu;
import menu.ResultMenu;

public class SurvivalGame extends JPanel implements Runnable{ 
	public static final int WORLD_COL = 50;
	public static final int WORLD_ROW = 50;
	public static final int WORLD_WIDTH = (int)(Main.TILES_SIZE * WORLD_COL);
	public static final int WORLD_HEIGHT = (int)(Main.TILES_SIZE * WORLD_ROW);
	public static final int FPS_SET = Main.FPS_SET;
	private static final double OBS_PERCENTAGE = 0.15;
	private static final double ORB_PERCENTAGE = 0.05;
	private static final double BLADE = 0.003;
	private static final double BLUE_GUARD = 0.003;
	private static final double CHASER = 0.0025;
	private BufferedImage backgroundImg;
	private Thread gameThread;
	private SurvivalFish player;
	private Goldfish goldfish;
	private Spawner spawner;
	private SurvivalMap survivalMap;
	private boolean lose;
	private boolean gamePaused;
	public SurvivalGame(){
 		Main.STARTPANEL.setVisible(false);
		Main.SCREEN.getScreen().add(this);
		PauseMenu pauseMenu= new PauseMenu(this);
		pauseMenu.setRunning(false);

        this.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                	gamePaused = true;
                	pauseMenu.setRunning(true);
                }
            }
		});
		this.requestFocusInWindow();
		this.requestFocus();
		this.setPreferredSize(new Dimension(Main.GAME_WIDTH, Main.GAME_HEIGHT));
		this.setBackground(Color.black);
		this.addKeyListener(new SKeyHandler());
		this.setFocusable(true);
	}
	public void start() {
		lose = false;
        SKeyHandler.RESET();
        backgroundImg = IOHandler.getImage("water.png");
		player = new SurvivalFish(this, WORLD_WIDTH/2, WORLD_HEIGHT/2);
		player.giveInvincibility((long)11e9);
		goldfish = new Goldfish(this, WORLD_WIDTH/2, WORLD_HEIGHT/2, 0.03);
		survivalMap = new SurvivalMap(this, OBS_PERCENTAGE, ORB_PERCENTAGE, 0.1, 0.05, 0.01);
		spawner = new Spawner(this, BLADE, BLUE_GUARD, CHASER);
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void run() { 
		double drawInterval = 1000000000/FPS_SET;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(true&&!gameThread.isInterrupted()) {
			currentTime = System.nanoTime();
			if(gamePaused) {
				lastTime = currentTime;
				continue;
			}
			if(lose) {
				//lose = false;
			    ResultMenu resMenu = new ResultMenu(this);
				resMenu.setRunning(true);	
				break;
			};
			delta += (currentTime - lastTime)/drawInterval;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
			lastTime = currentTime;
		}	
	}
	public void update() {
		player.update();
		goldfish.update();
		spawner.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
	    player.render(g2);
	    goldfish.render(g2);
	    survivalMap.render(g2);
	    spawner.render(g2);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));	
		g2.drawImage(backgroundImg, 0, 0, (int)(Math.max(Main.GAME_WIDTH, Main.GAME_HEIGHT)*Main.SCALE)*2, (int)(Math.max(Main.GAME_WIDTH, Main.GAME_HEIGHT)*Main.SCALE)*2, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	    g2.setColor(Color.WHITE);
	    g2.drawString("Point: " + player.getPoint(), Main.TILES_SIZE, Main.TILES_SIZE);
	}
	public SurvivalFish getPlayer() {
		return player;
	}
	public Goldfish getGoldfish() {
		return goldfish;
	}
	public SurvivalMap getMap() { 
		return survivalMap;
	}
	public Spawner getSpawner() {
		return spawner;
	}
	public void end() {
		gameThread.interrupt();
		setIgnoreRepaint(true);
		removeAll();
	}
	public void setLose(boolean lose) {
		this.lose = lose;
	}
	public boolean isLose() {
		return lose;
	}
	public void setPauseGame(boolean pause) {
		gamePaused = pause;
	}
	public boolean getPauseGame() {
		return gamePaused;
	}
}