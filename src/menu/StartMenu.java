package menu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;
import root.IOHandler;

public class StartMenu extends Menu {
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*4+GAP_BETWEEN_BUTTON.height*5);
	private Font fontTitle;
	private final String gameName = "FISH ADVANTURE";
	private BufferedImage fish1, fish2, intro;
	private final int fishSize = Main.TILES_SIZE*6;
	
	//private LevelMenu lvlMenu;
	private OptionPlaying optionMenu;
	private SettingMenu settingMenu;
	private Instruction instruction;
	
	public StartMenu(){
		running = true;
		
		fish1 = IOHandler.getImage("blue_fish.png");
		fish2 = IOHandler.getImage("red_fish.png");
		intro = IOHandler.getImage("intro.png");
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height));
		menuPanel.setFocusable(true);
	
		fontTitle = FONT_BIG_TITLE.deriveFont(Font.PLAIN, (float)(Main.GAME_HEIGHT/10));
		
		//lvlMenu = new LevelMenu(game);
		optionMenu = new OptionPlaying(this);
		settingMenu = new SettingMenu(this);
		instruction = new Instruction(this);
		
		JButton btnPlay = new ButtonMenu("PLAY");
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	running = false;
            	menuPanel.setVisible(false);
            	optionMenu.setRunning(true);
            	if(!Main.STARTPANEL.isAncestorOf(optionMenu.getMenuPanel())) {
            		Main.STARTPANEL.add(optionMenu.getMenuPanel());
            	}
            	Main.STARTPANEL.repaint();
            	Main.STARTPANEL.setMenu(optionMenu);

            }
        });
       
        
        JButton btnInstruction = new ButtonMenu("INSTRUCTION");
        btnInstruction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//            	running = false;
//            	menuPanel.setVisible(false);
            	setRunning(false);
            	instruction.setRunning(true);
            	if(!Main.STARTPANEL.isAncestorOf(instruction.getMenuPanel())) {
            		Main.STARTPANEL.add(instruction.getMenuPanel());
            	}
            	Main.STARTPANEL.repaint();
            	Main.STARTPANEL.setMenu(instruction);

            }
        });
        
        
        JButton btnSetting = new ButtonMenu("SETTINGS");
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	running = false;
            	menuPanel.setVisible(false);
            	settingMenu.setRunning(true);
            	if(!Main.STARTPANEL.isAncestorOf(settingMenu.getMenuPanel())) {
            		Main.STARTPANEL.add(settingMenu.getMenuPanel());
            	}
            	Main.STARTPANEL.repaint();
            	Main.STARTPANEL.setMenu(settingMenu);

            }
        });
        
        JButton btnExit = new ButtonMenu("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
       
		menuPanel.add(btnPlay);
		menuPanel.add(btnInstruction);
		menuPanel.add(btnSetting);
		menuPanel.add(btnExit);
	
		
		menuPanel.setSize(PANEL_SIZE);
		menuPanel.setLocation(Main.GAME_WIDTH/2 - PANEL_SIZE.width/2, Main.GAME_HEIGHT/2 - PANEL_SIZE.height/2);
		menuPanel.setVisible(running);
		menuPanel.setOpaque(false);
	}
	
	@Override
	public void render(Graphics g) {
		g.setFont(fontTitle);
		FontMetrics metrics = g.getFontMetrics(fontTitle);
		int x = (Main.GAME_WIDTH - metrics.stringWidth(gameName)) / 2; 
		int y = metrics.getAscent()+BUTTON_SIZE.height; 
		g.drawString(gameName,x,y);
		g.drawImage(fish1, fishSize/2, Main.GAME_HEIGHT/2-fishSize/2, fishSize, fishSize, menuPanel);
		g.drawImage(fish2, Main.GAME_WIDTH- fishSize-fishSize/2,Main.GAME_HEIGHT/2-fishSize/2,fishSize,fishSize, menuPanel);
		g.drawImage(intro, 0,Main.GAME_HEIGHT-fishSize,Main.GAME_WIDTH,fishSize, null);
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean running) {
		this.running = running;
		menuPanel.setVisible(running);
	}

	@Override
	public JPanel getMenuPanel() {
		return menuPanel;
	}

	@Override
	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
		
	}

	public OptionPlaying getOptionMenu() {
		return optionMenu;
	}


	
	
	
}
