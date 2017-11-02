package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.TimeoutException;

import static com.cshep4.monsterattack.game.Constants.CHARACTER_IDLE;

public class Player extends Character {
	private int destX;
	private int destY;

	private float touchCentreX;
	private float touchCentreY;

	private int frameNr;

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
	
	public void setDestX(int destX){
		this.destX = destX;
	}
	
	public int getDestX() {
		return this.destX;
	}	
	
	public void setDestY(int destY){
		this.destY = destY;
	}
	
	public int setDestY() {
		return this.destY;
	}		
	
	public void update() {
		// Stop player leaving the screan
		if ((this.getRectangle().getX() < 0 && xVel < 0) || (this.getRectangle().getX() > myApp.getScreenWidth() + this.getRectangle().getWidth() && xVel > 0)) {
			xVel = 0;
		}
		if ((this.getRectangle().getY() < 0 && yVel < 0) || (this.getRectangle().getY() > myApp.getScreenHeight() + this.getRectangle().getHeight() && yVel > 0)) {
			yVel = 0;
		}

		// Set direction player is facing
		if (xVel < 0 && (directionFacing != Constants.LEFT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
			this.setDirectionFacing(Constants.LEFT);
//			this.setNewBitmap(myApp.playerRunLeft, Constants.CHARACTER_RUNNING_DIVIDER);
		}
		if (xVel > 0 && (directionFacing != Constants.RIGHT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
			this.setDirectionFacing(Constants.RIGHT);
//			this.setNewBitmap(myApp.playerRun, Constants.CHARACTER_RUNNING_DIVIDER);
		}

		// Set direction player is facing
		if (xVel == 0 && yVel != 0) {
			if (yVel < 0 && (directionFacing != Constants.LEFT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
				this.setDirectionFacing(Constants.LEFT);
//				this.setNewBitmap(myApp.playerRunLeft, Constants.CHARACTER_RUNNING_DIVIDER);
			}
			if (yVel > 0 && (directionFacing != Constants.RIGHT || this.frameNr != Constants.CHARACTER_RUNNING_DIVIDER)) {
				this.setDirectionFacing(Constants.RIGHT);
//				this.setNewBitmap(myApp.playerRun, Constants.CHARACTER_RUNNING_DIVIDER);
			}
		}

		getRectangle().setX(getRectangle().getX()+xVel);
		getRectangle().setY(getRectangle().getY()+yVel);
	}

	public void move(float xPos, float yPos) {
		double left = this.getRectangle().getX();
		double top = this.getRectangle().getY();
		double right = this.getRectangle().getX() + this.getRectangle().getWidth();
		double bottom = this.getRectangle().getY() + this.getRectangle().getHeight();
		double xMiddle = this.getRectangle().getX() + (this.getRectangle().getWidth()/2);

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

	public void stand() {
		//this.setNewBitmap(myApp.playerIdle, Constants.CHARACTER_IDLE_DIVIDER);
		setTexture(new Texture(Gdx.files.internal(CHARACTER_IDLE)));
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
