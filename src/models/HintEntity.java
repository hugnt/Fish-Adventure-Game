package models;

public class HintEntity {
	private int id;
	private int levelId;
	private String content;
	public HintEntity(int id, int levelId, String content) {
		super();
		this.id = id;
		this.levelId = levelId;
		this.content = content;
	}
	
	public HintEntity() {}

	public int getLevelId() {
		return levelId;
	}

	public String getContent() {
		return content;
	}

	
}
