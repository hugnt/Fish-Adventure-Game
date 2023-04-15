package menu;

import java.awt.Color;
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

public class ResultMenu extends Menu{
	private Dimension PANEL_SIZE = new Dimension(BUTTON_SIZE.width*2, BUTTON_SIZE.height*4+GAP_BETWEEN_BUTTON.height*5);
	private Font fontTitle;
	private String title = "RESULT";
	private JDialog resDialog;
	
	public ResultMenu(Game game) {
		resDialog = new JDialog();
		resDialog.setModal(true);
		
		resDialog.setLayout(new FlowLayout(FlowLayout.CENTER, GAP_BETWEEN_BUTTON.width, GAP_BETWEEN_BUTTON.height-10));
		resDialog.setFocusable(true);
		resDialog.getContentPane().setBackground(new Color(243,139,117,255));
		resDialog.getRootPane().setBorder(BORDER_PANEL);
	
		fontTitle = FONT_TITLE.deriveFont(Font.PLAIN, (float)(BUTTON_SIZE.height/1.5));
		
		JLabel lbResult = new JLabel(title);
		lbResult.setPreferredSize(new Dimension(BUTTON_SIZE.width*3, BUTTON_SIZE.height));
		lbResult.setHorizontalAlignment(SwingConstants.CENTER);
		lbResult.setBorder(null);
		lbResult.setFont(fontTitle);
		lbResult.setOpaque(false);
		
		JButton btnNextLevel = new ButtonMenu("NEXT LEVEL");
		btnNextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.end();
            	if(game.getCurrentLevel()==game.getTotalLevel()) {
            		return;
            	}
				game.start(game.getCurrentLevel()+1);
				resDialog.setVisible(false);
				game.getPanel().requestFocus();
            }
        });

        JButton btnRestart = new ButtonMenu("RESTART");
        btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				game.start(game.getCurrentLevel());
				resDialog.setVisible(false);
				game.getPanel().requestFocus();
				
			}
		});
        
        
        JButton btnExitToMap = new ButtonMenu("EXIT TO MAP");
        btnExitToMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				resDialog.setVisible(false);
				var lvlMenu = game.getLvlMenu();
				game.getPanel().add(lvlMenu.getMenuPanel());
				lvlMenu.setRunning(true);
            	game.getPanel().setMenu(lvlMenu);
            	game.getPanel().repaint();
            	
			}
		});
        
        if(game.isWin()==true) {
        	resDialog.getContentPane().setBackground(new Color(255,247,120,255));
        	lbResult.setText("YOU WIN");
        }
        else if(game.isLose()==true) {
        	lbResult.setText("GAME OVER");
        }
        if(game.getCurrentLevel()==game.getTotalLevel()) {
        	lbResult.setPreferredSize(new Dimension(BUTTON_SIZE.width*5, BUTTON_SIZE.height));
        	lbResult.setText("!CONGRATULATION YOU WON ALL LEVELS!");
    		btnNextLevel.setVisible(false);
    		PANEL_SIZE = new Dimension(BUTTON_SIZE.width*6, BUTTON_SIZE.height*4);
    	}
        
        resDialog.add(lbResult);
        if(game.isWin()==true) resDialog.add(btnNextLevel);
        resDialog.add(btnRestart);
        resDialog.add(btnExitToMap);
	
        resDialog.setSize(PANEL_SIZE);
        resDialog.setLocationRelativeTo(null);
        resDialog.setUndecorated(true);
        //resDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        resDialog.setVisible(false);
		//menuPanel.setOpaque(false);
	}
	

	public JDialog getPauseDialog() {
		return resDialog;
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
		resDialog.setVisible(running);
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
