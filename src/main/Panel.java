package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import root.IOHandler;

public class Panel extends JPanel{
	private Game game;
	private int width,height;
	private BufferedImage background;
	private final String BG_URL = "bg05.jpg";
		
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
		g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
//		g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
		game.render(g);
	}
}
