package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		//top-left
		if(!IsSolid(x, y, lvlData)) {
			//bottom - right
			if(!IsSolid(x + width, y + height, lvlData)) {
				//top - right
				if(!IsSolid(x + width, y, lvlData)) {
					//bottom left
					if(!IsSolid(x , y + height, lvlData)) {
						System.out.println("FORTH: x: "+x +"y: "+y + height);
						return true;
					}
						
				}
			}
		}
			
				
					
		
		return false;
	}
	
	
	//check position
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if(x < 0 || x >= Game.GAME_WIDTH) {
			System.out.println("x OUT OF Width" + Game.GAME_WIDTH );
			return true;
		}
		if(y < 0 || y >= Game.GAME_HEIGHT) {
			System.out.println("y OUT OF Width" + Game.GAME_HEIGHT );
			return true;
		}
		//if both above are false => Inside the game wwindow -> continue checking the details position
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = lvlData[(int)yIndex][(int)xIndex];
//		System.out.println("Pos imgage: "+ value);
		if(value >= 48 || value < 0|| value != 11) {
			System.out.println("index OUT OF Value" + value);
			return true;
		}
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		}else {
			// Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		//Check the pixel below bottom-left and bottom-right
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
				return false;
			}
		}
		return true;
	}
	
}
