package root;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SKeyHandler implements KeyListener{
	public static boolean paused, shiftPressed, upPressed, downPressed, leftPressed, rightPressed; //shared for all class KeyListener
	public static void RESET() {
		paused= shiftPressed= upPressed= downPressed= leftPressed= rightPressed = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}		
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}		
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			paused = !paused;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}		
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}	
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}	
	}
}
