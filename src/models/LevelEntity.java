package models;

public class LevelEntity {
	private int id;
	private String keyAnswer;
	private String keyAnswer2;
	private String map;
	private int limitTyping;
	
	public LevelEntity(int id, String keyAnswer, String keyAnswer2, String map, int limitTyping) {
		super();
		this.id = id;
		this.keyAnswer = keyAnswer;
		this.keyAnswer2 = keyAnswer2;
		this.map = map;
		this.limitTyping = limitTyping;
	}
	
	public String getKeyAnswer() {
		return keyAnswer;
	}

	public String getKeyAnswer2() {
		return keyAnswer2;
	}

	public String getMap() {
		return map;
	}

	public int getLimitTyping() {
		return limitTyping;
	}

	public LevelEntity() {}	
	
	
}
