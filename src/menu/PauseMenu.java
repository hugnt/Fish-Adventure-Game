package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import main.Game;
import main.Main;
import main.SurvivalGame;
import root.IOHandler;
import root.SKeyHandler;

public class PauseMenu extends Menu{
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*5+GAP_BETWEEN_BUTTON.height*6);
	private Font fontTitle;
	private final String title = "PAUSED";
	private JDialog pauseDialog;

	public PauseMenu(SurvivalGame game) {
	
		pauseDialog = new JDialog();
		pauseDialog.setModal(true);
		
		pauseDialog.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height-10));
		pauseDialog.setFocusable(true);
        pauseDialog.getContentPane().setBackground(new Color(121,204,213,255));
		pauseDialog.getRootPane().setBorder(BORDER_PANEL);
	
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/1.5));
		
		JLabel lbPaused = new JLabel(title);
		lbPaused.setPreferredSize(new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height));
		lbPaused.setHorizontalAlignment(SwingConstants.CENTER);
		lbPaused.setBorder(null);
		lbPaused.setFont(fontTitle);
		lbPaused.setOpaque(false);
		
		JButton btnResume = new ButtonMenu("RESUME");
        btnResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	running = false;
            	pauseDialog.setVisible(false);
            	SKeyHandler.paused = false;
            	SKeyHandler.RESET();
            	game.requestFocus();
            }
        });
        pauseDialog.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                	running = false;
                	pauseDialog.setVisible(false);
                    SKeyHandler.RESET();
                }
            }
		});

       
        
        JButton btnRestart = new ButtonMenu("NEW GAME");
        btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setRunning(false);
				game.end();
				game.start();
				game.requestFocus();
				SKeyHandler.RESET();
			}
		});
        
        pauseDialog.add(lbPaused);
        pauseDialog.add(Main.AUDIOPLAYER);
        pauseDialog.add(btnResume);
        pauseDialog.add(btnRestart);
	
        pauseDialog.setTitle("Pause Screen");
        pauseDialog.setSize(PANEL_SIZE);
        pauseDialog.setLocationRelativeTo(null);
        pauseDialog.setUndecorated(true);
        //pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pauseDialog.setVisible(false);
		//menuPanel.setOpaque(false);
        
	}
	
	public PauseMenu(Game game) {
		pauseDialog = new JDialog();
		pauseDialog.setModal(true);
		
		pauseDialog.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height-10));
		pauseDialog.setFocusable(true);
        pauseDialog.getContentPane().setBackground(new Color(121,204,213,255));
		pauseDialog.getRootPane().setBorder(BORDER_PANEL);
	
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/1.5));
		
		JLabel lbPaused = new JLabel(title);
		lbPaused.setPreferredSize(new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height));
		lbPaused.setHorizontalAlignment(SwingConstants.CENTER);
		lbPaused.setBorder(null);
		lbPaused.setFont(fontTitle);
		lbPaused.setOpaque(false);
		
		JButton btnResume = new ButtonMenu("RESUME");
        btnResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	running = false;
            	pauseDialog.setVisible(false);
                game.getPanel().requestFocus();
            }
        });

       
        
        JButton btnRestart = new ButtonMenu("RESTART");
        btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				game.start(game.getCurrentLevel());
				pauseDialog.setVisible(false);
				game.getPanel().requestFocus();
				
			}
		});
        
        
        JButton btnExitToMap = new ButtonMenu("EXIT TO MAP");
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
	public boolean getRunning() {
		return running;
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
