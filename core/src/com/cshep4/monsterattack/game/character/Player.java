package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.utils.Utils.movePlayerTowardsPoint;

public class Player extends Character {

	private Player(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	public static Player create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		Texture texture = TextureFactory.create(CHARACTER_IDLE);

		return new Player(rectangle, texture, 2, 1);
	}
	
	public void update() {
		stopPlayerLeavingTheScreen();

		getRectangle().setX(getRectangle().getX()+getXVelByDeltaTime());
		getRectangle().setY(getRectangle().getY()+getYVelByDeltaTime());
	}

	private void stopPlayerLeavingTheScreen() {
		if (playerLeavingScreenHorizontally()) {
			xVel = 0;
		}
		if (playerLeavingScreenVertiically()) {
			yVel = 0;
		}
	}

	private boolean playerLeavingScreenHorizontally() {
		return getRectangle().getX() < 0 && xVel < 0 ||
				getRectangle().getX() > getScreenXMax() - getRectangle().getWidth() && xVel > 0;
	}

	private boolean playerLeavingScreenVertiically() {
		return getRectangle().getY() < 0 && yVel < 0 ||
				getRectangle().getY() > getScreenYMax() - getRectangle().getHeight() && yVel > 0;
	}

	public void move(float xPos, float yPos) {
		movePlayerTowardsPoint(this, xPos, yPos);
	}

	public void stand() {
		xVel = 0;
		yVel = 0;
		Texture texture = TextureFactory.create(CHARACTER_IDLE);
		setTexture(texture);
		changeAnimation(texture, 2, 1);
	}

	public Bullet shoot() {
		float x = getMidX();
		float y = getMidY();
		float width = getRectangle().getWidth() / BULLET_WIDTH_DIVIDER;
		float height = getRectangle().getHeight() / BULLET_HEIGHT_DIVIDER;

		return Bullet.create(this, x, y, width, height);
	}
}
