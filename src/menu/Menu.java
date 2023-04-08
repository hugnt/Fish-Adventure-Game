package menu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Game;

public abstract class Menu {
	protected final Dimension BUTTON_SIZE = new Dimension((int)(150*Game.SCALE),(int)(50*Game.SCALE));
	protected final Dimension GAP_BETWEEN_BUTTON = new Dimension((int)(20*Game.SCALE),(int)(20*Game.SCALE));
	protected JPanel menuPanel;
	protected boolean running;
	
	abstract public void render(Graphics g);

	abstract public boolean isRunning();

	abstract public void setRunning(boolean running);

	abstract public JPanel getMenuPanel();
	
	abstract public void setMenuPanel(JPanel menuPanel);
	
	
}
