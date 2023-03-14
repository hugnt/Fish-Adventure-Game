package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	public LevelManager(Game game) {
		this.game = game;
		//levelSprite = LoadSave.GetSpritesAtlas(LoadSave.LEVEL_ATLAS);
		importOutsideSrrites();
		levelOne= new Level(LoadSave.GetLevelData());
	}
	
	private void importOutsideSrrites() {
		levelSprite = new BufferedImage[48];//4 height x 12 width
		BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.LEVEL_ATLAS);
		//spread 2d arr -> 1d arr
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				int index = i*12 + j;
				levelSprite[index] = img.getSubimage(j*32, i*32, 32 , 32);
			}
		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
			for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
				int index = levelOne.getSpriteIndex(j, i);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * j , Game.TILES_SIZE * i,
							Game.TILES_SIZE, Game.TILES_SIZE,null);
			}
		}
		
	}
	
	public void update() {
		
	}
}
