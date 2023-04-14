package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import menu.AudioPlayer;
import menu.Menu;
import menu.StartMenu;
import root.IOHandler;

public class Main {
	public static Screen SCREEN;
	public static Panel STARTPANEL;
	
	// config game
	public static int TILES_DEFAULT_SIZE;
	public static float SCALE;
	public static int TILES_IN_WIDTH;
	public final static int TILES_IN_HEIGHT = 22;
	public static int TILES_SIZE;
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	
	//Audio
	public static AudioPlayer AUDIOPLAYER;
	
	private static void importScreenConfig() {
		TILES_DEFAULT_SIZE = Integer.parseInt(IOHandler.getProperty("TILES_DEFAULT_SIZE").trim());
		SCALE = Float.parseFloat(IOHandler.getProperty("SCALE").trim());
		TILES_IN_WIDTH = Integer.parseInt(IOHandler.getProperty("TILES_IN_WIDTH").trim());
		TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
		GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
		GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
		
	}
	public static void main(String[] args) {
		importScreenConfig();
		AUDIOPLAYER = new AudioPlayer("audio1.wav");
		STARTPANEL= new Panel(null, GAME_WIDTH, GAME_HEIGHT);// create panel
		SCREEN = new Screen(STARTPANEL);// Create window
		Menu startMenu = new StartMenu();
		STARTPANEL.setMenu(startMenu);
		STARTPANEL.add(startMenu.getMenuPanel());
		
		STARTPANEL.revalidate(); 
		STARTPANEL.repaint();
		
	}

}
