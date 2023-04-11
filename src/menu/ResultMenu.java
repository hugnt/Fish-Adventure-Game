package menu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Game;
import root.IOHandler;

public class ResultMenu extends Menu{
	private final Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*4+GAP_BETWEEN_BUTTON.height*4);
	private Font fontBtn;
	private Font fontTitle;
	private String title = "YOU WIN";
	private JDialog pauseDialog;
	
	public ResultMenu(Game game) {
		pauseDialog = new JDialog();
		pauseDialog.setModal(true);
		
		pauseDialog.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height-10));
		pauseDialog.setFocusable(true);
		fontBtn = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/3));;
		
		fontTitle = IOHandler.getFont("RussoOne-Regular.ttf").deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/1.5));
		
		JLabel lbResult = new JLabel(title);
		lbResult.setPreferredSize(new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height));
		lbResult.setHorizontalAlignment(SwingConstants.CENTER);
		lbResult.setBackground(COLOR_BUTTON);
		lbResult.setBorder(null);
		lbResult.setFont(fontTitle);
		lbResult.setOpaque(false);
		
		JButton btnNextLevel = new JButton("NEXT LEVEL");
		btnNextLevel.setPreferredSize(BUTTON_SIZE);
		btnNextLevel.setBackground(COLOR_BUTTON);
		btnNextLevel.setBorder(BORDER_BUTTON);
		btnNextLevel.setFocusPainted(false);
		btnNextLevel.setFont(fontBtn);
		btnNextLevel.setOpaque(false);
		btnNextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
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
				game.start("map005.png");
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
				var lvlMenu = ((StartMenu)game.getStartMenu()).getLvlMenu();
				game.getPanel().add(lvlMenu.getMenuPanel());
				lvlMenu.setRunning(true);
            	game.getPanel().setMenu(lvlMenu);
            	game.getPanel().repaint();
            	
			}
		});
        
        pauseDialog.add(lbResult);
        pauseDialog.add(btnNextLevel);
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
