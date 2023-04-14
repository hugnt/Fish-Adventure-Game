package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Game;
import main.Main;

public abstract class Menu {
	protected final static Dimension BUTTON_SIZE = new Dimension((int)(150*Main.SCALE),(int)(50*Main.SCALE));
	protected final static Dimension GAP_BETWEEN_BUTTON = new Dimension((int)(20*Main.SCALE),(int)(20*Main.SCALE));
	protected final Color COLOR_BUTTON = new Color(41, 171, 226, 117);
	protected final Border BORDER_BUTTON =  BorderFactory.createLineBorder(Color.BLACK, 2);
	protected JPanel menuPanel;
	protected boolean running;
	
	abstract public void render(Graphics g);

	abstract public boolean isRunning();

	abstract public void setRunning(boolean running);

	abstract public JPanel getMenuPanel();
	
	abstract public void setMenuPanel(JPanel menuPanel);
	
	
}
