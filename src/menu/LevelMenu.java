package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Game;
import root.IOHandler;

public class LevelMenu extends Menu{
	
	private int numberOfMap = 6;
	private final String title = "CHOOSE LEVEL";
	private Font fontBtn;
	private Font fontTitle;
	private JButton[] btnLvl;
	private BufferedImage bgLvlMenu;
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*5, BUTTON_SIZE.height*3+GAP_BETWEEN_BUTTON.height*3);
	private Map<Integer, String> memLvl;
	private String[] lvlUrl;
	
	public LevelMenu(Game game) {
		//running = true;
		bgLvlMenu = IOHandler.getImage("seaCave.gif");
		memLvl = new HashMap<>();
		lvlUrl = new String[]{
			"map005.png",
			"map006.png",
			"map007.png",
			"map008.png",
			"map008.png",
			"map010.png"
		};
		
		
		menuPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(10, 10, 10, 10);
	    
		//menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height));
		menuPanel.setFocusable(true);
		fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));;
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/2));
		btnLvl = new JButton[numberOfMap];
		
		for (int i = 0; i < numberOfMap; i++) {
			btnLvl[i] = new JButton("Level "+(i+1));
	        btnLvl[i].setPreferredSize(BUTTON_SIZE);
	        btnLvl[i].setBackground(COLOR_BUTTON);
	        btnLvl[i].setBorder(BORDER_BUTTON);
	        btnLvl[i].setFocusPainted(false);
	        btnLvl[i].setFont(fontBtn);
	        btnLvl[i].setOpaque(false);
	        c.gridx = i % 3;
            c.gridy = i / 3;
	        menuPanel.add(btnLvl[i], c);
	        
	        memLvl.put(i, lvlUrl[i]);
	        final String lvlUrl = memLvl.get(i);
			btnLvl[i].addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	running = false;
	            	menuPanel.setVisible(false);
	            	game.start(lvlUrl);
	            }
	        });
		}
		
		menuPanel.setSize(PANEL_SIZE);
		menuPanel.setLocation(Game.GAME_WIDTH/2 - PANEL_SIZE.width/2, Game.GAME_HEIGHT/2 - PANEL_SIZE.height/2);
		menuPanel.setVisible(running);
		menuPanel.setOpaque(false);
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(bgLvlMenu, 0, 0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.setFont(fontTitle);
		FontMetrics metrics = g.getFontMetrics(fontTitle);
		int x = (Game.GAME_WIDTH - metrics.stringWidth(title)) / 2; 
		int y = metrics.getAscent()+BUTTON_SIZE.height*4; 
		g.drawString(title,x,y);
		
	}
	@Override
	public boolean isRunning() {
		return running;
	}
	@Override
	public void setRunning(boolean running) {
		menuPanel.setVisible(running);
		this.running = running;
		
	}
	@Override
	public JPanel getMenuPanel() {
		return menuPanel;
	}
	@Override
	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
		
	}
	
	public String getSelectedLvl(int i) {
		return memLvl.get(i);
	}
	
	public JButton[] getBtnLvl() {
		return btnLvl;
	}
}
	

