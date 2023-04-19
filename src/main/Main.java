package main;

import java.sql.SQLException;

import menu.AudioPlayer;
import menu.Menu;
import menu.StartMenu;
import root.AppDbContext;
import root.IOHandler;

public class Main {
	public static Screen SCREEN;
	public static Panel STARTPANEL;
	public static final String CONFIG_FILE = "config.properties";
	public static final String UPDATE_FILE = "update.properties";

	// config game
	public static int TILES_DEFAULT_SIZE = 40;
	public static float SCALE;
	public static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 22;
	public static int TILES_SIZE;
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	public static int FPS_SET;
	public static AppDbContext DBContext;

	// Audio
	public static AudioPlayer AUDIOPLAYER;

	private static void importGameConfig() {
		//Import from file
		SCALE = Float.parseFloat(IOHandler.getProperty("SCALE", CONFIG_FILE).trim()) * 0.8f;
		TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
		GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
		GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
		FPS_SET = Integer.parseInt(IOHandler.getProperty("FPS_SET", Main.CONFIG_FILE).trim());

		// Import data from MySQL
		try {
			DBContext = new AppDbContext();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		importGameConfig();
		AUDIOPLAYER = new AudioPlayer("audio1.wav");
		//AUDIOPLAYER.stop();
		STARTPANEL = new Panel(null, GAME_WIDTH, GAME_HEIGHT);// create panel
		SCREEN = new Screen(STARTPANEL);// Create window

		Menu startMenu = new StartMenu();

		STARTPANEL.setMenu(startMenu);
		STARTPANEL.add(startMenu.getMenuPanel());

		// reset panel
		STARTPANEL.revalidate();
		STARTPANEL.repaint();

	}

}
