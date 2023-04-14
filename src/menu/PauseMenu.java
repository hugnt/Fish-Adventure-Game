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

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Game;
import main.Main;
import root.IOHandler;

public class PauseMenu extends Menu{
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*5+GAP_BETWEEN_BUTTON.height*6);
	private Font fontBtn;
	private Font fontTitle;
	private final String title = "PAUSED";
	private JDialog pauseDialog;

	
	public PauseMenu(Game game) {
		pauseDialog = new JDialog();
		pauseDialog.setModal(true);
		
		pauseDialog.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height-10));
		pauseDialog.setFocusable(true);
		fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));;
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/1.5));
		
		JLabel lbPaused = new JLabel(title);
		lbPaused.setPreferredSize(new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height));
		lbPaused.setHorizontalAlignment(SwingConstants.CENTER);
		lbPaused.setBorder(null);
		lbPaused.setFont(fontTitle);
		lbPaused.setOpaque(false);
		
		JButton btnResume = new JButton("RESUME");
		btnResume.setPreferredSize(BUTTON_SIZE);
		btnResume.setBackground(COLOR_BUTTON);
        btnResume.setBorder(BORDER_BUTTON);
        btnResume.setFocusPainted(false);
        btnResume.setFont(fontBtn);
        btnResume.setOpaque(false);
        btnResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	running = false;
            	pauseDialog.setVisible(false);
                game.getPanel().requestFocus();
            }
        });

       
        
        JButton btnRestart = new JButton("RESTART");
        btnRestart.setPreferredSize(BUTTON_SIZE);
        btnRestart.setBackground(COLOR_BUTTON);
        btnRestart.setBorder(BORDER_BUTTON);
        btnRestart.setFocusPainted(false);
        btnRestart.setFont(fontBtn);
        btnRestart.setOpaque(false);
        btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				game.start(game.getCurrentLevel());
				pauseDialog.setVisible(false);
				game.getPanel().requestFocus();
				
			}
		});
        
        
        JButton btnExitToMap = new JButton("EXIT TO MAP");
        btnExitToMap.setPreferredSize(BUTTON_SIZE);
        btnExitToMap.setBackground(COLOR_BUTTON);
        btnExitToMap.setBorder(BORDER_BUTTON);
        btnExitToMap.setFocusPainted(false);
        btnExitToMap.setFont(fontBtn);
        btnExitToMap.setOpaque(false);
        btnExitToMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				pauseDialog.setVisible(false);
				var lvlMenu = game.getLvlMenu();
				game.getPanel().add(lvlMenu.getMenuPanel());
				lvlMenu.setRunning(true);
            	game.getPanel().setMenu(lvlMenu);
            	game.getPanel().repaint();
            	
			}
		});
        
        pauseDialog.add(lbPaused);
        pauseDialog.add(Main.AUDIOPLAYER);
        pauseDialog.add(btnResume);
        pauseDialog.add(btnRestart);
        pauseDialog.add(btnExitToMap);
	
        pauseDialog.setTitle("Pause Screen");
        pauseDialog.setSize(PANEL_SIZE);
        pauseDialog.setLocationRelativeTo(null);
        pauseDialog.setUndecorated(true);
        //pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pauseDialog.setVisible(false);
		//menuPanel.setOpaque(false);
	}


	public JDialog getPauseDialog() {
		return pauseDialog;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean running) {
		this.running = running;
		pauseDialog.setVisible(running);
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
