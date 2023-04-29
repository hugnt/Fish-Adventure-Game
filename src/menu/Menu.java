package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Main;
import root.IOHandler;

public abstract class Menu {
	protected final static Dimension BUTTON_SIZE = new Dimension((int)(150*Main.SCALE),(int)(50*Main.SCALE));
	protected final static Dimension GAP_BETWEEN_BUTTON = new Dimension((int)(20*Main.SCALE),(int)(20*Main.SCALE));
	protected final static Border BORDER_PANEL=  BorderFactory.createLineBorder(Color.WHITE, 20);
	protected JPanel menuPanel;
	protected boolean running;
	protected final static Font FONT_TITLE = IOHandler.getFont("RussoOne-Regular.ttf");
	protected final static Font FONT_BIG_TITLE = IOHandler.getFont("BungeeShade-Regular.ttf");
	
	abstract public void render(Graphics g);

	abstract public boolean isRunning();

	abstract public void setRunning(boolean running);

	abstract public JPanel getMenuPanel();
	
	abstract public void setMenuPanel(JPanel menuPanel);
}
