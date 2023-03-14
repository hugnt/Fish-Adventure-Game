package entities;


import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.LoadSave;

public class Player extends Entity{
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
	
	private void setAnimation() {
		int startAni = playerAction;

		if(moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}
		
		if(attacking) {
			playerAction = ATTACK_1;
		}
		
		if(startAni != playerAction) {
			resetAniTick();
		}
		
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	private void updatePos() {
		moving = false;
		
		if(left && !right) {
			x-=playerSpeed;
			moving = true;
		}
		else if(!left && right) {
			x+=playerSpeed;
			moving = true;
		}
		
		if(up&&!down) {
			y-=playerSpeed;
			moving = true;
		}
		else if(!up&&down) {
			y+=playerSpeed;
			moving = true;
		}
		
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex ++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
		
	}
	

	
	
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpritesAtlas(LoadSave.PLAYER_ATLAS);
		
		animations = new BufferedImage[9][6];
		for (int i = 0; i < animations.length; i++) {
			for (int j = 0; j < animations[i].length; j++) {
				animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
			}	
		}
	
	}
	
	public void resetDirBooleans() {
		left = right = up = down = false;
	}
	
	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	
}
