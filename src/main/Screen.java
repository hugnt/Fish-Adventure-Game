package main;


import javax.swing.ImageIcon;
import javax.swing.JFrame;

import root.IOHandler;

public class Screen {
	private JFrame screen;

	public Screen(Panel panel) {
		screen = new JFrame();
		screen.setTitle("Fish Adventure Game");
		ImageIcon icon = new ImageIcon(IOHandler.getImage("icon.png"));
		screen.setIconImage(icon.getImage());
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminated window 
		screen.add(panel);//add panel for frame
		screen.setResizable(false);
		screen.pack();//fit size of window
		screen.setLocationRelativeTo(null);//put the window on the center of screen
		screen.setVisible(true);//display the window
	}

	public JFrame getScreen() {
		return screen;
	}

}
