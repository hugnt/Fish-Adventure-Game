package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import menu.Menu;
import root.IOHandler;

public class Panel extends JPanel{
	private Game game;
	private int width,height;
	private BufferedImage background;
	private final String BG_URL = "bg05.jpg";
	private Menu menu;
	
	public Panel(Game game, int width, int height) {
		super();
		this.game = game;
		this.height = height;
		this.width = width;
		background = IOHandler.getImage(BG_URL);
		
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setLayout(null);
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//pre-work clean surface
		g.drawImage(background, 0, 0, width, height, null);
		if(game!=null&&game.isRunning()) {
			game.render(g);
		}
		else if(menu!=null&&menu.isRunning()){
//			System.out.println("MENU-PANEl");
			menu.render(g);
		}
		else {
			System.out.println("NGUUU");
		}
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	

	
}
