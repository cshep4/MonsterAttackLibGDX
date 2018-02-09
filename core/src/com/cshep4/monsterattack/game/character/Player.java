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

	@Override
	public void update() {
		stopPlayerLeavingTheScreen();

		setX(getX() + getXVelByDeltaTime());
		setY(getY() + getYVelByDeltaTime());
	}

	private void stopPlayerLeavingTheScreen() {
		if (isPlayerLeavingScreenHorizontally()) {
			xVel = 0;
		}
		if (isPlayerLeavingScreenVertiically()) {
			yVel = 0;
		}
	}

	private boolean isPlayerLeavingScreenHorizontally() {
		return (getX() < 0 && xVel < 0) || (getX() > getScreenXMax() - getWidth() && xVel > 0);
	}

	private boolean isPlayerLeavingScreenVertiically() {
		return (getY() < 0 && yVel < 0) || (getY() > getScreenYMax() - getHeight() && yVel > 0);
	}

	public void move(float xPos, float yPos) {
		movePlayerTowardsPoint(this, xPos, yPos);
	}

	public void stand() {
		xVel = 0;
		yVel = 0;
		Texture texture = TextureFactory.create(CHARACTER_IDLE);
		changeAnimation(texture, 2, 1);
	}

	public Bullet shoot() {
		float x = getMidX();
		float y = getMidY();
		float width = getWidth() / BULLET_WIDTH_DIVIDER;
		float height = getHeight() / BULLET_HEIGHT_DIVIDER;

		return Bullet.create(this, x, y, width, height);
	}
}
