package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import entity.Enemy;
import entity.Fish;
import entity.Lock;
import entity.Hint;
import map.Atlas;
import menu.LevelMenu;
import menu.Menu;
import menu.PauseMenu;
import menu.ResultMenu;
import models.HintEntity;
import models.LevelEntity;
import root.AppDbContext;
import root.IOHandler;
import root.MoveHandler;
import root.Pair;

public class Game implements Runnable {

//	private Screen screen;
	private Panel panel;
	private Menu lvlMenu;
	private Atlas map;
	private Fish fish1, fish2;
	private Lock lock;
	private ArrayList<Hint> lstHint;
	private Map<Pair<Integer, Integer>, Integer> memHint;
	
	private ArrayList<Enemy> enemy;

	// game status
	private boolean running;
	private boolean win;
	private boolean lose;
	private boolean touchEnemy;
	

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

	//level status
	private int currentLevel;
	private int lastLevel;
	
	//map&& locks and hints
	private ArrayList<LevelEntity> lvlEntity;
	private ArrayList<HintEntity> hintEntity;

	public Game() {
		importGameConfig();
		lvlMenu = new LevelMenu(this);
   
		panel = new Panel(this, GAME_WIDTH, GAME_HEIGHT);// create panel
		Main.STARTPANEL.setVisible(false);
		Main.SCREEN.getScreen().add(panel);
		
		panel.add(lvlMenu.getMenuPanel());
    	panel.setMenu(lvlMenu);
    	
    	panel.repaint();
    	panel.revalidate();
		
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
	};

	public void start(int level) {
		currentLevel = level;
		lastLevel = Integer.parseInt(IOHandler.getProperty("LAST_LEVEL_PLAYING",Main.UPDATE_FILE).trim());
		win = lose = false;
		map = new Atlas(getLevel(level));
		
		initCharacter();
		initHint();
		initLock();
		initEnemy();
		importPauseMenu();

		movingEvent = new MoveHandler(fish1, fish2);
		panel.addKeyListener(movingEvent);
		
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
		for (var amee : enemy) {
			amee.render(g, xMapOffset);
		}
		for (var q : lstHint) {
			q.render(g, xMapOffset);
		}
		map.render(g, xMapOffset);
		lock.render(xMapOffset);
		fish1.render(g, xMapOffset);
		fish2.render(g, xMapOffset);

	}

	private void importGameConfig() {
		TILES_DEFAULT_SIZE = Integer.parseInt(IOHandler.getProperty("TILES_DEFAULT_SIZE", Main.CONFIG_FILE).trim());
		SCALE = Float.parseFloat(IOHandler.getProperty("SCALE", Main.CONFIG_FILE).trim());
		TILES_IN_WIDTH = Integer.parseInt(IOHandler.getProperty("TILES_IN_WIDTH", Main.CONFIG_FILE).trim());
		TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
		GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
		GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
		FPS_SET = Integer.parseInt(IOHandler.getProperty("FPS_SET", Main.CONFIG_FILE).trim());
		
		lastLevel = Integer.parseInt(IOHandler.getProperty("LAST_LEVEL_PLAYING",Main.UPDATE_FILE).trim());		
		leftBorder = (int) (0.4 * GAME_WIDTH);
		rightBorder = (int) (0.6 * GAME_WIDTH);
		
		lvlEntity = new ArrayList<LevelEntity>();
		hintEntity = new ArrayList<HintEntity>();
		
		//Import data from MySQL
		try {
			AppDbContext _dbContext = new AppDbContext();
			lvlEntity = _dbContext.getLvlEntity();
			hintEntity = _dbContext.getHintEntity();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		
	}

	private void initCharacter() {
		fish1 = new Fish(TILES_SIZE * 2, TILES_SIZE * 6, 64 * SCALE, 64 * SCALE, "red");
		fish2 = new Fish(TILES_SIZE * 2, TILES_SIZE * 16, 64 * SCALE, 64 * SCALE, "blue");
		fish1.setOtherFish(fish2);
		fish2.setOtherFish(fish1);
		fish1.setMapData(map.getMapData());
		fish2.setMapData(map.getMapData());
	}
	
	private void initHint() {
		var lstHintContent = new ArrayList<String>();
		for (var hint : hintEntity) {
			if(hint.getLevelId()==currentLevel) {
				lstHintContent.add(hint.getContent());
			}
		}
		lstHint = new ArrayList<Hint>();
		memHint = new HashMap<>();
		ArrayList<Pair<Integer, Integer>> lstPosHint = map.getLstPosQuestion();
		int i = 0;
		for (var posHint : lstPosHint) {
			memHint.put(posHint, i);
			lstHint.add(new Hint(TILES_SIZE * posHint.first, TILES_SIZE * posHint.second,
					TILES_SIZE, TILES_SIZE, lstHintContent.get(i)));
			i++;
		}
	}
	
	private void initLock() {
		String key1 = lvlEntity.get(currentLevel-1).getKeyAnswer();
		String key2 = lvlEntity.get(currentLevel-1).getKeyAnswer2();
		int limitTyping =  lvlEntity.get(currentLevel-1).getLimitTyping();
		lock = new Lock(TILES_SIZE * map.getPosLockX(), TILES_SIZE * map.getPosLockY(), TILES_SIZE * 4, TILES_SIZE * 2,
				key1, key2, limitTyping);
		
		panel.add(lock.getPasswordPanel());
	}
	
	private void initEnemy() {
		 //enemy
        Enemy.mapData = map.getMapData();
        enemy = new ArrayList<Enemy>();
        var lstPosCrab = map.getLstPosCrab();
        var lstPosOcto = map.getLstPosOcto();
        var lstPosGrid = map.getLstPosGrid();
        for(var c : lstPosCrab) 
        	enemy.add(new Enemy(TILES_SIZE *c.first, TILES_SIZE *c.second, 410, 410, "crab.png", true));
       
       for(var o : lstPosOcto) 
        	enemy.add(new Enemy(TILES_SIZE *o.first, TILES_SIZE *o.second, 728, 728, "octopus.png", true));
       
       for(var g : lstPosGrid)
    	   	enemy.add(new Enemy(TILES_SIZE *g.first, TILES_SIZE *g.second, 560, 560, "grid.png", false));
       
       	touchEnemy = false;
	}
	
	private void importPauseMenu() {
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
		
        PauseMenu pauseMenu = new PauseMenu(Game.this);
        btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseMenu.setRunning(running);
			}
		});
        panel.add(btnPause);
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
			if(currentLevel==getTotalLevel()) return;
			else if(currentLevel==lastLevel) {
				lastLevel+=1;
				IOHandler.setProperty("LAST_LEVEL_PLAYING", Integer.toString(lastLevel), Main.UPDATE_FILE);
				((LevelMenu) lvlMenu).update();
			}
			
		}
		else if(fish1.isTouchTrap()||fish2.isTouchTrap()||touchEnemy||lock.isLose()) {
			lose = true;
		}
	}
	
	private void updatePosEnemy() {
		ArrayList<Pair<Float, Float>> fishPos = new ArrayList<Pair<Float, Float>>();
		fishPos.add(fish1.getFishPos());
		fishPos.add(fish2.getFishPos());
		for (var amee : enemy) {
			amee.updatePosition();
			amee.setFishPos(fishPos);
			
			if(amee.isTouchFish()) touchEnemy = true;
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
				updatePosEnemy();
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



	public Menu getLvlMenu() {
		return lvlMenu;
	}


	public boolean isWin() {
		return win;
	}


	public boolean isLose() {
		return lose;
	}


	public String getLevel(int level) {
		return lvlEntity.get(level-1).getMap();
	}
	
	public int getTotalLevel() {
		return lvlEntity.size();
	}


	public int getCurrentLevel() {
		return currentLevel;
	}


	public int getLastLevel() {
		return lastLevel;
	}


	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	
	


}