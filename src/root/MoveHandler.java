package root;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import entity.Fish;
import main.Game;
import main.Main;

public class MoveHandler implements KeyListener {

	private Fish f1, f2;
	
	//special Object collition
	private boolean touchHint;
	private Pair<Integer, Integer> hintPos;
	
	private boolean touchLock;
	private Pair<Integer, Integer> lockPos;
	
	//touching trap
	private boolean touchTrap;
	
	
	//fish
	private ArrayList<Pair<Float, Float>> fishPos;
	private boolean enemyHandle;
	private boolean touchFish;
	
	public MoveHandler() {
		hintPos = new Pair<Integer, Integer> (0, 0);
		lockPos = new Pair<Integer, Integer> (0, 0);
		fishPos = new ArrayList<Pair<Float, Float>>();
		enemyHandle = false;
	}
	
	public MoveHandler(Fish f1, Fish f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public boolean isValidStep(float x, float y, float width, float height, int[][] lvlData, boolean unlock) {
		//top-left
		if(isValidPoint(x, y, lvlData, unlock)) {
			//bottom - right
			if(isValidPoint(x + width, y + height, lvlData,unlock)) {
				//top - right
				if(isValidPoint(x + width, y, lvlData, unlock)) {
					//bottom left
					if(isValidPoint(x , y + height, lvlData, unlock)) {
						//System.out.println("FORTH: x: "+x +"y: "+y + height);
						return true;
					}	
				}
			}
		}
						
		return false;
	}

	

	//check position
	private boolean isValidPoint(float x, float y, int[][] mapData, boolean unlock) {
		int maxWidth = mapData[0].length * Main.TILES_SIZE;
		if(x < 0 || x >= maxWidth) {
			//System.out.println("x OUT OF Width" + Main.GAME_WIDTH );
			return false;
		}
		if(y < 0 || y >= Main.GAME_HEIGHT) {
			//System.out.println("y OUT OF Width" + Main.GAME_HEIGHT );
			return false;
		}
		//if both above are false => Inside the game window -> continue checking the details position
		float xIndex = x / Main.TILES_SIZE;
		float yIndex = y / Main.TILES_SIZE;
		
		int value = mapData[(int)yIndex][(int)xIndex];
		
		//check  enemy touch fish
		if(enemyHandle) {
			for(var fish:fishPos) {
				float xFishA = fish.first;
				float yFishA = fish.second;
				
				float xFishB = xFishA + 40* Main.SCALE;
				float yFishC = yFishA + 26* Main.SCALE;
				
				if((x>=xFishA&&x<=xFishB)&&(y>=yFishA&&y<=yFishC)) {
					touchFish = true;
					return false;
				}
				
			}
		}
		
		
	
		//check in plant
		if(value == 48||value ==47||value == 46) return true;
		
		//check in trap
		if(value == 25||value ==26||value == 27) {
			touchTrap = true;
			return false;
		}
		
		
		
		//checkInHint
		if(value == 19) {
			touchHint = true;
			hintPos = new Pair<Integer, Integer> ((int)xIndex, (int)yIndex);
			//System.out.println((int)xIndex+" "+(int)yIndex);
		}
		if(Math.abs(hintPos.first-(int)xIndex)>2 || Math.abs(hintPos.second-(int)yIndex)>2) {
			touchHint = false;
		}
		
		
		//check lock
		if(unlock == false) {
			if(value == 28||value==29||value==30||value==31||value==35||value==36||value==37||value==38) {
				touchLock = true;
				//key lock check opening
				lockPos = new Pair<Integer, Integer> ((int)xIndex, (int)yIndex);
				return false;
			}
			if(Math.abs(lockPos.first-(int)xIndex)>2 || Math.abs(lockPos.second-(int)yIndex)>2) {
				touchLock = false;
			}
		}
		else {
			touchLock = false;
			if(value == 28||value==29||value==30||value==31||value==35||value==36||value==37||value==38) {
				return true;
			}
		}
		
//		System.out.println("value:"+value);
		if(value >= 48 || value < 0|| value != 6) {
//			System.out.println("index OUT OF Value" + value);d
			return false;
		}
		return true;

	}
	
	//getter & setter
	public Pair<Integer, Integer> getHintPos() {
		return hintPos;
	}
	
	
	public boolean isTouchHint() {
		return touchHint;
	}
	
	public boolean isTouchLock() {
		return touchLock;
	}
	
	
	public boolean isTouchTrap() {
		return touchTrap;
	}
	public boolean isTouchFish() {
		return touchFish;
	}


	public void setEnemyHandle(boolean enemyHandle) {
		this.enemyHandle = enemyHandle;
	}

	public void setFishPos(ArrayList<Pair<Float, Float>> fishPos) {
		this.fishPos = fishPos;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				f1.setUp(true);
				f2.setUp(true);
				break;
			case KeyEvent.VK_A:
				f1.setLeft(true);
				f2.setLeft(true);
				break;
			case KeyEvent.VK_S:
				f1.setDown(true);
				f2.setDown(true);
				break;
			case KeyEvent.VK_D:
				f1.setRight(true);
				f2.setRight(true);
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				f1.setUp(false);
				f2.setUp(false);
				break;
			case KeyEvent.VK_A:
				f1.setLeft(false);
				f2.setLeft(false);
				break;
			case KeyEvent.VK_S:
				f1.setDown(false);
				f2.setDown(false);
				break;
			case KeyEvent.VK_D:
				f1.setRight(false);
				f2.setRight(false);
				break;
		}
	}

}
