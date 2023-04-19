package main;
import root.*;
import entity.*;
import map.*;
import menu.PauseMenu;
import menu.ResultMenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.server.Skeleton;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SurvivalGame extends JPanel implements Runnable{ //Panel + Thread
	public static final int WORLD_COL = 50;
	public static final int WORLD_ROW = 50;
	public static final int WORLD_WIDTH =  Main.TILES_SIZE * WORLD_COL;
	public static final int WORLD_HEIGHT = Main.TILES_SIZE * WORLD_ROW;
	private int FPS_SET = 60;
	
	private boolean gameOver;
	private boolean GamePaused;
	
	private SurvivalFish player;
	private Goldfish goldfish;
	private Creature[] creature;
	private SurvivalMap smap;
	private Thread gameThread;
	
	//game status
	private boolean lose;
	
	
	public SurvivalGame(){
		Main.STARTPANEL.setVisible(false);
		Main.SCREEN.getScreen().add(this);
		PauseMenu pauseMenu= new PauseMenu(this);
		pauseMenu.setRunning(false);
        this.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                	pauseMenu.setRunning(!pauseMenu.getRunning());
                }
            }
		});
		//player.giveInvincibility((long)10e9);
		this.requestFocusInWindow();
		this.requestFocus();
		this.setPreferredSize(new Dimension(Main.GAME_WIDTH, Main.GAME_HEIGHT));
		this.setBackground(Color.black);
		this.addKeyListener(new SKeyHandler());
		this.setFocusable(true); //game panel can be focused to receive key input
	
	}

	//Private methods
	public void start() {
		lose = false;
		//pause menu
	
        //other 
        SKeyHandler.RESET();
		player = new SurvivalFish(this, WORLD_WIDTH/2, WORLD_HEIGHT/2);
		goldfish = new Goldfish(this, WORLD_WIDTH/2, WORLD_HEIGHT/2, 0.03);
		smap = new SurvivalMap(this, 0.15, 0.05, 0.1, 0.05, 0.01);
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() { 
		double drawInterval = 1000000000/FPS_SET;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(true&&!gameThread.isInterrupted()) {
			if(lose) {
				lose= false;
			    ResultMenu resMenu = new ResultMenu(this);
				resMenu.setRunning(true);	
		
				break;
			};
			currentTime = System.nanoTime();
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
		

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
	    player.render(g2);
	    goldfish.render(g2);
	    smap.render(g2);

	    /*Replace with JDialog*/

	 
		
	    g2.setColor(Color.WHITE);
	    g2.drawString("Point: " + player.getPoint(), (int)(20*Main.SCALE), (int)(20*Main.SCALE));

	}

	//Get methods
	public boolean isOver() {
		return gameOver;
	}
	public boolean isPaused() {
		return GamePaused;
	}
	
	public SurvivalFish getPlayer() {
		return player;
	}

	

	//Player's methods
	public int playerWorldX() {
		return player.getWorldX();
	}
	public int playerWorldY() {
		return player.getWorldY();
	}
	public int playerScreenX() {
		return player.getScreenX();
	}
	public int playerScreenY() {
		return player.getScreenY();
	}
	public void setDeathMessage(String str) {
		player.setDeathMessage(str);
	}
	//Map's method
	public boolean tileIsMoveable(int code) {
		return smap.tileIsMoveable(code);
	}
	public boolean tileIsConsumable(int code) {
		return smap.tileIsConsumable(code);
	}
	public boolean tileIsTrap(int code) {
		return smap.tileIsTrap(code);
	}
	public int[] traceMap(int[][] hitbox, int deltax, int deltay, boolean isConsumable) {
		return smap.traceMap(hitbox, deltax, deltay, isConsumable);
	}
	//Set methods
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

}