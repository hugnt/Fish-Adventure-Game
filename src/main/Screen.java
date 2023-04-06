package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class Screen {
	private JFrame screen;

	public Screen(Panel panel) {
		screen = new JFrame();
//		screen.setSize(2000, 2000);//size window
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminated window 
		screen.add(panel);//add panel for frame
		screen.setLocationRelativeTo(null);//put the window on the center of screen
		screen.setResizable(false);
		screen.pack();//fit size of window
		screen.setVisible(true);//display the window
	}
}