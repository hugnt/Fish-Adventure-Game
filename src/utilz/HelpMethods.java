package utilz;

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
	private static boolean IsSolid(float x, float y,  int[][] lvlData) {
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
}
