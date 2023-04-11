package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import entity.Enemy;
import entity.Fish;
import entity.Lock;
import entity.Hint;
import map.Atlas;
import menu.AudioPlayer;
import menu.LevelMenu;
import menu.Menu;
import menu.PauseMenu;
import menu.ResultMenu;
import menu.StartMenu;
import root.IOHandler;
import root.MoveHandler;
import root.Pair;

public class Game implements Runnable {

	private Screen screen;
	private Panel panel;
	private Menu startMenu;
	private Atlas map;
	private Fish fish1, fish2;
	private Lock lock;
	private ArrayList<Hint> lstHint;
	private Map<Pair<Integer, Integer>, Integer> memHint;
	
	private ArrayList<Enemy> crab, octo;

	// game status
	private boolean running;
	private boolean win;
	private boolean lose;

	// config game
	public static int TILES_DEFAULT_SIZE;
	public static float SCALE;
	public static int TILES_IN_WIDTH;
	public final static int TILES_IN_HEIGHT = 22;
	public static int TILES_SIZE;
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;

	// Events
	private MoveHandler movingEvent;

	// display thread
	private Thread displayThread;
	private int FPS_SET;

	// Extend map
	private int xMapOffset = 0;
	private int leftBorder;
	private int rightBorder;
	
	//audio
	private AudioPlayer audioPlayer;

	public Game() {
		importConfig();
		startMenu = new StartMenu(this);
		panel = new Panel(this, GAME_WIDTH, GAME_HEIGHT);// create panel
		screen = new Screen(panel);// Create window
		
		//panel.setMenu(startMenu);
		//panel.add(startMenu.getMenuPanel());

		panel.setFocusable(true);
		panel.requestFocusInWindow();
		panel.requestFocus();
		panel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (panel.contains(e.getPoint())) {
		            panel.requestFocusInWindow();
		        }
		    }
		});
		
		audioPlayer = new AudioPlayer("audio1.wav");
		start("map005.png");

	};
	

	private void importConfig() {
		TILES_DEFAULT_SIZE = Integer.parseInt(IOHandler.getProperty("TILES_DEFAULT_SIZE").trim());
		SCALE = Float.parseFloat(IOHandler.getProperty("SCALE").trim());
		TILES_IN_WIDTH = Integer.parseInt(IOHandler.getProperty("TILES_IN_WIDTH").trim());
		TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
		GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
		GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
		FPS_SET = Integer.parseInt(IOHandler.getProperty("FPS_SET").trim());

		leftBorder = (int) (0.4 * GAME_WIDTH);
		rightBorder = (int) (0.6 * GAME_WIDTH);
	}

	public void start(String level) {
		win = lose = false;
		map = new Atlas(level);
		fish1 = new Fish(TILES_SIZE * 2, TILES_SIZE * 6, 64 * SCALE, 64 * SCALE, "red");
		fish2 = new Fish(TILES_SIZE * 2, TILES_SIZE * 16, 64 * SCALE, 64 * SCALE, "blue");
		fish1.setOtherFish(fish2);
		fish2.setOtherFish(fish1);
		fish1.setMapData(map.getMapData());
		fish2.setMapData(map.getMapData());

		String[] question = new String[] { "The first character of key is S", "The key contains character K",
				"The key relevants to a big fish", "The key contains character H", "More than 30000 teaths",
				"Living in the ocean", "... Tank" };

		lstHint = new ArrayList<Hint>();
		ArrayList<Pair<Integer, Integer>> posQuestion = map.getLstPosQuestion();
		memHint = new HashMap<>();

		for (int i = 0; i < posQuestion.size(); i++) {
			memHint.put(posQuestion.get(i), i);
			lstHint.add(new Hint(TILES_SIZE * posQuestion.get(i).first, TILES_SIZE * posQuestion.get(i).second,
					TILES_SIZE, TILES_SIZE, question[i]));
		}

		lock = new Lock(TILES_SIZE * map.getPosLockX(), TILES_SIZE * map.getPosLockY(), TILES_SIZE * 4, TILES_SIZE * 2,
				"shark");
		panel.add(lock.getPasswordPanel());

		movingEvent = new MoveHandler(fish1, fish2, panel);
		panel.addKeyListener(movingEvent);
		
		
		Font fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, 14f);;
		
		JButton btnPause = new JButton("| |");
		btnPause.setFocusPainted(false);
        btnPause.setOpaque(false);
        btnPause.setForeground(new Color(18,219,242,255));
        btnPause.setBounds((int)(GAME_WIDTH-TILES_SIZE*1.5),(int)(TILES_SIZE/3), TILES_SIZE, TILES_SIZE);
        btnPause.setFont(fontBtn);
        btnPause.setBackground(new Color(41,171,226,255));
        btnPause.setBorder(BorderFactory.createLineBorder(new Color(18,219,242,255), 2));
        btnPause.setMargin(new Insets(0,0, 0, 0));
        
        //pauseMenu.getPauseDialog().dispose();
		
        PauseMenu pauseMenu = new PauseMenu(Game.this);
        btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseMenu.setRunning(running);
			}
		});
        panel.add(btnPause);
        
        Enemy.mapData = map.getMapData();
        crab = new ArrayList<Enemy>();
        octo = new ArrayList<Enemy>();
        var lstPosCrab = map.getLstPosCrab();
        var lstPosOcto = map.getLstPosOcto();
        
        for(var c : lstPosCrab) {
        	crab.add(new Enemy(TILES_SIZE *c.first, TILES_SIZE *c.second, 410, 410, "crab.png"));
  
        }
       
        for(var o : lstPosOcto) {
       	 	crab.add(new Enemy(TILES_SIZE *o.first, TILES_SIZE *o.second, 728, 728, "octopus.png"));
       }
        
		running = true;
		// thread
		displayThread = new Thread(this);
		displayThread.start();

	}

	public void end() {
		displayThread.interrupt();
		panel.setIgnoreRepaint(true);
		panel.removeAll();
		running = false;
		
	}

	public void render(Graphics g) {
		for (var c : crab) {
			c.render(g, xMapOffset);
		}
		for (var o : octo) {
			o.render(g, xMapOffset);
		}
		for (var q : lstHint) {
			q.render(g, xMapOffset);
		}
		map.render(g, xMapOffset);
		lock.render(xMapOffset);
		fish1.render(g, xMapOffset);
		fish2.render(g, xMapOffset);

	}
	
	

	private void updateLock() {
		if (fish1.isTouchLock() || fish2.isTouchLock()) {
			lock.setShowInput(true);
		} else {
			lock.setShowInput(false);
		}
		
	}

	private void updateHint() {
		for (var hint : lstHint)
			hint.setShowHint(false);

		if (fish1.isTouchHint()) {
			int selectedHint = memHint.get(fish1.getHintPos());
			lstHint.get(selectedHint).setShowHint(true);
		}

		if (fish2.isTouchHint()) {
			int selectedHint = memHint.get(fish2.getHintPos());
			lstHint.get(selectedHint).setShowHint(true);
		}

	}
	
	private void updateRes() {
		if(lock.isUnlock()) {
			win = true;
		}
		else if(fish1.isTouchTrap()||fish1.isTouchTrap()) {
			lose = true;
		}
	}

	private void extendMap() {
		int mapTilesWide = map.getMapData()[0].length;
		int maxTilesOffset = mapTilesWide - TILES_IN_WIDTH;
		int maxMapOffsetX = maxTilesOffset * TILES_SIZE;

		int fishPosX = (int) fish1.getHitbox().x;
		int diff = fishPosX - xMapOffset;
		if (diff > rightBorder) {
			xMapOffset += diff - rightBorder;
		} else if (diff < leftBorder) {
			xMapOffset += diff - leftBorder;
		}
		if (xMapOffset > maxMapOffsetX) {
			xMapOffset = maxMapOffsetX;
		} else if (xMapOffset < 0) {
			xMapOffset = 0;
		}
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long previousTime = System.nanoTime();

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaF = 0;
		while (true&&!displayThread.isInterrupted()) {
			if(win == true||lose == true) {
				lock.reset();
				ResultMenu resMenu = new ResultMenu(this);
				resMenu.setRunning(true);	
				break;
			}
			long currentTime = System.nanoTime();

			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaF >= 1) {
				map.setUnlock(lock.isUnlock());
				fish1.updateLockStatus(lock.isUnlock());
				fish2.updateLockStatus(lock.isUnlock());
				fish1.updatePosition();
				fish2.updatePosition();
				for (var c : crab) {
					c.updatePosition();
				}
				for (var o : octo) {
					o.updatePosition();
				}
				updateRes();
				updateHint();
				updateLock();
				extendMap();
				panel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Panel getPanel() {
		return this.panel;
	}


	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}


	public Menu getStartMenu() {
		return startMenu;
	}


	public boolean isWin() {
		return win;
	}


	public void setWin(boolean win) {
		this.win = win;
	}


	public boolean isLose() {
		return lose;
	}


	public void setLose(boolean lose) {
		this.lose = lose;
	}
	


}