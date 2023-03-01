package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/*
 * I. Jframe
 * - the backbone for our window
 * - the 'frame' of painting
 * - part of a larger collections of GUI components
 * - JFrame, JPanel, JTextField, JScrollPane, JLable and many more ...
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */


public class GameWindow {
	
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		
//		jframe.setSize(400, 400);//size window
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminated window 
		jframe.add(gamePanel);//add panel for frame
		jframe.setLocationRelativeTo(null);//put the window on the center of screen
		jframe.setResizable(false);
		jframe.pack();//fit size of window
		jframe.setVisible(true);//display the window
		jframe.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
				
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				
				
			}
		});
		
	}
}
