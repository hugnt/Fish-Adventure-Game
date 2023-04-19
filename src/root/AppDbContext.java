package root;

import java.sql.*;
import java.util.ArrayList;

import main.Main;
import models.HintEntity;
import models.LevelEntity;

public class AppDbContext {
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static String DB_NAME = "fishadventure";
	static String HOST_NAME = "localhost";
	static String PORT;
	static String DB_URL = "jdbc:mysql://localhost/fishadvanture?serverTimezone=UTC";
	static String USER = "root";
	static String PASS = "";
	private ArrayList<LevelEntity> lvlEntity;
	private ArrayList<HintEntity> hintEntity;
	
	public AppDbContext() throws SQLException, ClassNotFoundException {
		
//		DRIVER_CLASS = IOHandler.getProperty("DRIVER_CLASS",Main.CONFIG_FILE).trim();
		HOST_NAME = IOHandler.getProperty("HOST_NAME",Main.CONFIG_FILE).trim();
		PORT = IOHandler.getProperty("PORT",Main.CONFIG_FILE).trim();
		DB_NAME = IOHandler.getProperty("DB_NAME",Main.CONFIG_FILE).trim();
		USER = IOHandler.getProperty("USER",Main.CONFIG_FILE).trim();
		PASS = IOHandler.getProperty("PASS",Main.CONFIG_FILE).trim();
		DB_URL = "jdbc:mysql://"+HOST_NAME+"/"+DB_NAME+"?serverTimezone=UTC";
		
		lvlEntity = new ArrayList<LevelEntity>();
		hintEntity = new ArrayList<HintEntity>();
		
		Connection conn = null;
		Statement stmt = null;
			
		try {
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection successfully!");
			stmt = conn.createStatement();
			ResultSet rsLevel = stmt.executeQuery("SELECT id, KeyAnswer, KeyAnswer2, map, limitTyping FROM tLevel");
			while (rsLevel.next()) {
				lvlEntity.add(new LevelEntity(
						rsLevel.getInt("id"),
						rsLevel.getString("KeyAnswer"),
						rsLevel.getString("KeyAnswer2"),
						rsLevel.getString("map"),
						rsLevel.getInt("limitTyping")
				));
			}
			
			ResultSet rsHint = stmt.executeQuery("SELECT id, LevelId, content FROM tHint");
			while (rsHint.next()) {
				hintEntity.add(new HintEntity(
						rsHint.getInt("id"),
						rsHint.getInt("LevelId"),
						rsHint.getString("content")
				));
			}
			
			rsLevel.close();
			rsHint.close();
			System.out.println("Retrived data successfully!");
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public ArrayList<LevelEntity> getLvlEntity() {
		return lvlEntity;
	}

	public ArrayList<HintEntity> getHintEntity() {
		return hintEntity;
	}

	
}
