package root;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Fish;
import main.Game;
import main.Panel;

public class MoveHandler implements KeyListener {

	Fish f1, f2;
	Panel panel;
	
	public MoveHandler(Fish f1, Fish f2,Panel panel) {
		this.f1 = f1;
		this.f2 = f2;
		this.panel = panel;
	}
	
	public static boolean isValidStep(float x, float y, float width, float height, int[][] lvlData) {
		//top-left
		if(isValidPoint(x, y, lvlData)) {
			//bottom - right
			if(isValidPoint(x + width, y + height, lvlData)) {
				//top - right
				if(isValidPoint(x + width, y, lvlData)) {
					//bottom left
					if(isValidPoint(x , y + height, lvlData)) {
						System.out.println("FORTH: x: "+x +"y: "+y + height);
						return true;
					}	
				}
			}
		}
						
		return false;
	}
	
	
	//check position
	private static boolean isValidPoint(float x, float y, int[][] mapData) {
		int maxWidth = mapData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth) {
			System.out.println("x OUT OF Width" + Game.GAME_WIDTH );
			return false;
		}
		if(y < 0 || y >= Game.GAME_HEIGHT) {
			System.out.println("y OUT OF Width" + Game.GAME_HEIGHT );
			return false;
		}
		//if both above are false => Inside the game window -> continue checking the details position
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = mapData[(int)yIndex][(int)xIndex];
//		System.out.println("Pos imgage: "+ value);
		if(value >= 48 || value < 0|| value != 6) {
			System.out.println("index OUT OF Value" + value);
			return false;
		}
		return true;
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
