package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.Game;
import root.IOHandler;

public class StartMenu extends Menu {
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*4+GAP_BETWEEN_BUTTON.height*5);
	private final Color COLOR_BUTTON = new Color(41, 171, 226, 117);
	private final Border BORDER_BUTTON =  BorderFactory.createLineBorder(Color.BLACK, 2);
	private Font fontBtn;
	private Font fontTitle;
	private final String gameName = "FISH ADVANTURE";
	private BufferedImage fish1;
	private BufferedImage fish2;
	private BufferedImage intro;
	private final int fishSize = Game.TILES_SIZE*6;
	
	public StartMenu(){
		running = true;
		
		fish1 = IOHandler.getImage("blue_fish.png");
		fish2 = IOHandler.getImage("red_fish.png");
		intro = IOHandler.getImage("intro.png");
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height));
		menuPanel.setFocusable(true);
		fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));;
		fontTitle = IOHandler.getFont("BungeeShade-Regular.ttf").deriveFont(Font.PLAIN, (float)(Game.GAME_HEIGHT/10));
		
		
		JButton btnStart = new JButton("START");
        btnStart.setPreferredSize(BUTTON_SIZE);
        btnStart.setBackground(COLOR_BUTTON);
        btnStart.setBorder(BORDER_BUTTON);
        btnStart.setFocusPainted(false);
        btnStart.setFont(fontBtn);
        btnStart.setOpaque(false);
       
        
        JButton btnInstruction = new JButton("INSTRUCTION");
        btnInstruction.setPreferredSize(BUTTON_SIZE);
        btnInstruction.setBackground(COLOR_BUTTON);
        btnInstruction.setBorder(BORDER_BUTTON);
        btnInstruction.setFocusPainted(false);
        btnInstruction.setFont(fontBtn);
        btnInstruction.setOpaque(false);
        //btnInstruction.setContentAreaFilled(false);
        
        
        JButton btnSetting = new JButton("SETTINGS");
        btnSetting.setPreferredSize(BUTTON_SIZE);
        btnSetting.setBackground(COLOR_BUTTON);
        btnSetting.setBorder(BORDER_BUTTON);
        btnSetting.setFocusPainted(false);
        btnSetting.setFont(fontBtn);
        btnSetting.setOpaque(false);
        //btnSetting.setContentAreaFilled(false);
        
        JButton btnExit = new JButton("EXIT");
        btnExit.setPreferredSize(BUTTON_SIZE);
        btnExit.setBackground(COLOR_BUTTON);
        btnExit.setBorder(BORDER_BUTTON);
        btnExit.setFocusPainted(false);
        btnExit.setFont(fontBtn);
        btnExit.setOpaque(false);
        //btnExit.setContentAreaFilled(false);
        
       
		menuPanel.add(btnStart);
		menuPanel.add(btnInstruction);
		menuPanel.add(btnSetting);
		menuPanel.add(btnExit);
	
		
		menuPanel.setSize(PANEL_SIZE);
		menuPanel.setLocation(Game.GAME_WIDTH/2 - PANEL_SIZE.width/2, Game.GAME_HEIGHT/2 - PANEL_SIZE.height/2);
		menuPanel.setVisible(running);
		menuPanel.setOpaque(false);
	}
	
	@Override
	public void render(Graphics g) {
		g.setFont(fontTitle);
		FontMetrics metrics = g.getFontMetrics(fontTitle);
		int x = (Game.GAME_WIDTH - metrics.stringWidth(gameName)) / 2; 
		int y = metrics.getAscent()+BUTTON_SIZE.height; 
		g.drawString(gameName,x,y);
		g.drawImage(fish1, fishSize/2, Game.GAME_HEIGHT/2-fishSize/2, fishSize, fishSize, menuPanel);
		g.drawImage(fish2, Game.GAME_WIDTH- fishSize-fishSize/2,Game.GAME_HEIGHT/2-fishSize/2,fishSize,fishSize, menuPanel);
		g.drawImage(intro, 0,Game.GAME_HEIGHT-fishSize,Game.GAME_WIDTH,fishSize, null);
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean running) {
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
	
}
