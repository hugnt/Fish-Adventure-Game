package menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import main.Main;
import root.IOHandler;

public class LevelMenu extends Menu{
	
	private final String title = "CHOOSE LEVEL";
	private Font fontTitle;
	private JButton[] btnLvl;
	private BufferedImage bgLvlMenu;
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*5, BUTTON_SIZE.height*5+GAP_BETWEEN_BUTTON.height*5);
	private Game game;
	
	public LevelMenu(Game game) {
		this.game = game;
		running = true;
		bgLvlMenu = IOHandler.getImage("seaCave.gif");

		menuPanel = new JPanel(new GridBagLayout());
	    		
		menuPanel.setFocusable(true);
		fontTitle = FONT_TITLE.deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/2));
		
		JLabel titleLabel = new JLabel(title);
	    titleLabel.setFont(fontTitle);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 0, 20, 0);
		c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết số cột còn lại
        c.anchor = GridBagConstraints.CENTER; 
        menuPanel.add(titleLabel, c);
		
        GridBagConstraints cb1 = new GridBagConstraints();
        cb1.insets = new Insets(10, 10, 10, 10);
		btnLvl = new JButton[game.getTotalLevel()];
		for (int i = 0; i < game.getTotalLevel(); i++) {
			final int level = i+1;
			String btnName = "Level "+level;
			if(level > game.getLastLevel()) {
				btnName = "[X] "+btnName;
			}
			btnLvl[i] = new ButtonMenu(btnName);
	        cb1.gridx = i % 3;
            cb1.gridy = i / 3+1;
			btnLvl[i].addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(level > game.getLastLevel()) return;
	            	menuPanel.setVisible(false);
	            	running = false;
	            	game.getPanel().repaint();
//	            	game.getPanel().revalidate();
	            	game.start(level);
	            	
	            }
	        });
			
			menuPanel.add(btnLvl[i], cb1);
		}
		
		//btn-back
		GridBagConstraints cb2 = new GridBagConstraints();
        cb2.gridx = 0;
	    cb2.gridy = 6;
	    cb2.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết số cột còn lại
	    cb2.anchor = GridBagConstraints.CENTER; 
	    cb2.insets = new Insets(20, 0, 20, 0);
	    
		JButton btnBack = new ButtonMenu("<< Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				running = false;
            	menuPanel.setVisible(false);
            	game.getPanel().setVisible(false);
				Main.STARTPANEL.setVisible(true);
				Main.STARTPANEL.repaint();
				Main.STARTPANEL.revalidate();
				
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
	}
	@Override
	public JPanel getMenuPanel() {
		return menuPanel;
	}
	@Override
	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
		
	}
	
	public void update() {
		for (int i = 0; i < btnLvl.length; i++) {
			int level = i+1;
			String btnName = "Level "+level;
			if(level > game.getLastLevel()) {
				btnName = "[X] "+btnName;
			}
			btnLvl[i].setText(btnName);
		}
	}

	public JButton[] getBtnLvl() {
		return btnLvl;
	}
}
	

