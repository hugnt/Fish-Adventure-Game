package root;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Fish;
import main.Game;
import main.Panel;

public class MoveHandler implements KeyListener {

	private Fish f1, f2;
	private Panel panel;
	
	//special Object collition
	private boolean touchHint;
	private Pair<Integer, Integer> hintPos;
	
	private boolean touchLock;
	private Pair<Integer, Integer> lockPos;
	
	//touching trap
	private boolean touchTrap;
	
	
	public MoveHandler() {
		hintPos = new Pair<Integer, Integer> (0, 0);
		lockPos = new Pair<Integer, Integer> (0, 0);
	}
	
	public MoveHandler(Fish f1, Fish f2,Panel panel) {
		this.f1 = f1;
		this.f2 = f2;
		this.panel = panel;
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
		int maxWidth = mapData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth) {
			//System.out.println("x OUT OF Width" + Game.GAME_WIDTH );
			return false;
		}
		if(y < 0 || y >= Game.GAME_HEIGHT) {
			//System.out.println("y OUT OF Width" + Game.GAME_HEIGHT );
			return false;
		}
		//if both above are false => Inside the game window -> continue checking the details position
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = mapData[(int)yIndex][(int)xIndex];
		
		
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
