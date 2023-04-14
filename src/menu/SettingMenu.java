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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.Game;
import main.Main;
import root.IOHandler;

public class SettingMenu extends Menu{
	
	private int numberOfMap = 6;
	private final String title = "CHOOSE LEVEL";
	private Font fontBtn;
	private Font fontTitle;
	private JButton[] btnLvl;
	private BufferedImage bgLvlMenu;
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*5, BUTTON_SIZE.height*5+GAP_BETWEEN_BUTTON.height*5);
	
	private Game game;
	private LevelMenu lvlMenu;
	
	public SettingMenu(StartMenu startMenu) {
		running = true;
		bgLvlMenu = IOHandler.getImage("seaCave.gif");
		menuPanel = new JPanel(new GridBagLayout());
		
		menuPanel.setFocusable(true);
		fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));;
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/2));
		
		JLabel titleLabel = new JLabel("SETTINGS");
	    titleLabel.setFont(fontTitle);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 0, 20, 0);
		c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết số cột còn lại
        c.anchor = GridBagConstraints.CENTER; 
        menuPanel.add(titleLabel, c);
		
        
       
      
		
		
		GridBagConstraints cb2 = new GridBagConstraints();
        cb2.gridx = 0;
	    cb2.gridy = 3;
	    cb2.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết số cột còn lại
	    cb2.anchor = GridBagConstraints.CENTER; 
	    cb2.insets = new Insets(20, 0, 20, 0);
	    
		JButton btnBack = new JButton("<< Back");
		btnBack.setBorder(BORDER_BUTTON);
		btnBack.setPreferredSize(BUTTON_SIZE);
		btnBack.setBackground(COLOR_BUTTON);
		btnBack.setFocusPainted(false);
		btnBack.setFont(fontBtn);
		btnBack.setOpaque(false);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				running = false;
            	menuPanel.setVisible(false);
            	startMenu.setRunning(true);
            	Main.STARTPANEL.revalidate(); 
            	Main.STARTPANEL.repaint();
            	Main.STARTPANEL.setMenu(startMenu);
			}
		});
		menuPanel.add(btnBack, cb2);
		
		menuPanel.setSize(PANEL_SIZE);
		menuPanel.setLocation(Main.GAME_WIDTH/2 - PANEL_SIZE.width/2, Main.GAME_HEIGHT/2 - PANEL_SIZE.height/2);
		menuPanel.setVisible(running);
		menuPanel.setOpaque(false);
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(bgLvlMenu, 0, 0,Main.GAME_WIDTH, Main.GAME_HEIGHT, null);	
	}
	@Override
	public boolean isRunning() {
		return running;
	}
	@Override
	public void setRunning(boolean running) {
		menuPanel.setVisible(running);
		this.running = running;
		if(running == true) {
			GridBagConstraints ca = new GridBagConstraints();
			ca.insets = new Insets(20, 0, 20, 0);
			ca.gridx = 0;
		    ca.gridy = 1;
		    ca.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết số cột còn lại
	        ca.anchor = GridBagConstraints.CENTER; 
	        menuPanel.add(Main.AUDIOPLAYER, ca);
		}
		
	}
	@Override
	public JPanel getMenuPanel() {
		return menuPanel;
	}
	@Override
	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
		
	}
	
	public LevelMenu getLvlMenu() {
		return lvlMenu;
	}

}
	

