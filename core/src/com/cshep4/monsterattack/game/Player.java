package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_MOVE_LEFT;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.Constants.PLAYER_SPEED;

public class Player extends Character {
	private boolean standingTexture = true;

	public Player(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}
	
	public void update() {
		// Stop player leaving the screan
		if ((this.getRectangle().getX() < 0 && xVel < 0) || (this.getRectangle().getX() > getScreenXMax() - this.getRectangle().getWidth() && xVel > 0)) {
			xVel = 0;
		}
		if ((this.getRectangle().getY() < 0 && yVel < 0) || (this.getRectangle().getY() > getScreenYMax() - this.getRectangle().getHeight() && yVel > 0)) {
			yVel = 0;
		}

		getRectangle().setX(getRectangle().getX()+(xVel*Gdx.graphics.getDeltaTime()));
		getRectangle().setY(getRectangle().getY()+(yVel*Gdx.graphics.getDeltaTime()));
	}

	public void move(float xPos, float yPos) {
		double left = this.getRectangle().getX();
		double top = this.getRectangle().getY() + this.getRectangle().getHeight();
		double right = this.getRectangle().getX() + this.getRectangle().getWidth();
		double bottom = this.getRectangle().getY();
		double xMiddle = this.getRectangle().getX() + (this.getRectangle().getWidth()/2);

        if (!(xPos>=left && xPos<=right)) {
			if (xPos<xMiddle) {
				if (this.getXVel() != -PLAYER_SPEED) {
					standingTexture = false;
					changeAnimation(new Texture(Gdx.files.internal(CHARACTER_MOVE_LEFT)),6, 1);
				}
				this.setXVel(-PLAYER_SPEED);
			}
			if (xPos>xMiddle) {
				if (this.getXVel() != PLAYER_SPEED) {
					standingTexture = false;
					changeAnimation(new Texture(Gdx.files.internal(CHARACTER_MOVE_RIGHT)),6, 1);
				}
				this.setXVel(PLAYER_SPEED);
			}
        }

		if (!(yPos>=bottom && yPos<=top)) {
			if (yPos>bottom) {
				if (standingTexture) {
					standingTexture = false;
					changeAnimation(new Texture(Gdx.files.internal(CHARACTER_MOVE_RIGHT)),6, 1);
				}
				this.setYVel(PLAYER_SPEED);
			}
			if (yPos<top) {
				if (standingTexture) {
					standingTexture = false;
					changeAnimation(new Texture(Gdx.files.internal(CHARACTER_MOVE_LEFT)),6, 1);
				}
				this.setYVel(-PLAYER_SPEED);
			}
		}

	}

	public void stand() {
		this.setXVel(0);
		this.setYVel(0);
		standingTexture = true;
		setTexture(new Texture(Gdx.files.internal(CHARACTER_IDLE)));
		changeAnimation(new Texture(Gdx.files.internal(CHARACTER_IDLE)), 2, 1);
	}

	public Bullet shoot() {
		float x = getRectangle().getX()+(getRectangle().getWidth() /2);
		float y = getRectangle().getY()+(getRectangle().getHeight() /2);
		float width = getRectangle().getWidth() / BULLET_WIDTH_DIVIDER;
		float height = getRectangle().getHeight() / BULLET_HEIGHT_DIVIDER;

		return Create.playerBullet(x, y, width, height);
	}
}
