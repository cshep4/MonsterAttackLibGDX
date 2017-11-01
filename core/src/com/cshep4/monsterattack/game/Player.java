package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.CHARACTER_IDLE;

public class Player extends Character {
	private int destX;
	private int destY;

	private float touchCentreX;
	private float touchCentreY;

	MyApp myApp = MyApp.getInstance();
	
	public Player(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
//		xPos = aXPos;
//		yPos = aYPos;
//		destX = xPos;
//		destY = yPos;
//		width = aWidth;
//		height = aHeight;
		this.directionFacing = Constants.RIGHT;
//		setNewBitmap(myApp.playerIdle, Constants.CHARACTER_IDLE_DIVIDER);
	}
	
	public void setDestX(int aDestX){
		this.destX = aDestX;
	}
	
	public int getDestX() {
		return this.destX;
	}	
	
	public void setDestY(int aDestY){
		this.destY = aDestY;
	}
	
	public int setDestY() {
		return this.destY;
	}		
	
	public void update() {
		if ((this.xPos < 0 && xVel < 0) || (this.xPos > myApp.getScreenWidth() + this.width && xVel > 0)) {
			xVel = 0;
		}
		if ((this.yPos < 0 && yVel < 0) || (this.yPos > myApp.getScreenHeight() + this.height && yVel > 0)) {
			yVel = 0;
		}

		if (xVel < 0 && (directionFacing != Constants.LEFT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
			this.setDirectionFacing(Constants.LEFT);
			this.setNewBitmap(myApp.playerRunLeft, Constants.CHARACTER_RUNNING_DIVIDER);
		}
		if (xVel > 0 && (directionFacing != Constants.RIGHT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
			this.setDirectionFacing(Constants.RIGHT);
			this.setNewBitmap(myApp.playerRun, Constants.CHARACTER_RUNNING_DIVIDER);
		}

		if (xVel == 0 && yVel != 0) {
			if (yVel < 0 && (directionFacing != Constants.LEFT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
				this.setDirectionFacing(Constants.LEFT);
				this.setNewBitmap(myApp.playerRunLeft, Constants.CHARACTER_RUNNING_DIVIDER);
			}
			if (yVel > 0 && (directionFacing != Constants.RIGHT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
				this.setDirectionFacing(Constants.RIGHT);
				this.setNewBitmap(myApp.playerRun, Constants.CHARACTER_RUNNING_DIVIDER);
			}
		}

		xPos += xVel;
		yPos += yVel;
	}

	public void move(float xPos, float yPos) {
		int left = this.getXPos();
		int top = this.getYPos();
		int right = this.getXPos() + this.getWidth();
		int bottom = this.getYPos() + this.getHeight();
		int xMiddle = this.getXPos() + (this.getWidth()/2);

		if (!(yPos<=bottom && yPos>=top)) {
			if (yPos>bottom) {
				this.setYVel(Constants.PLAYER_SPEED);
			}
			if (yPos<top) {
				this.setYVel(-Constants.PLAYER_SPEED);
			}
		}

        if (!(xPos>=left && xPos<=right)) {
			if (xPos<xMiddle) {
				this.setXVel(-Constants.PLAYER_SPEED);
			}
			if (xPos>xMiddle) {
				this.setXVel(Constants.PLAYER_SPEED);
			}
        }

	}

	public void standBitmap() {
		this.setNewBitmap(myApp.playerIdle, Constants.CHARACTER_IDLE_DIVIDER);
	}

	public int getDestY() {
		return destY;
	}

	public float getTouchCentreX() {
		return touchCentreX;
	}

	public void setTouchCentreX(float touchCentreX) {
		this.touchCentreX = touchCentreX;
	}

	public float getTouchCentreY() {
		return touchCentreY;
	}

	public void setTouchCentreY(float touchCentreY) {
		this.touchCentreY = touchCentreY;
	}
}
