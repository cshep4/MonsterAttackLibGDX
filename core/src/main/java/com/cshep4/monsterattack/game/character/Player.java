package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.cshep4.monsterattack.game.constants.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_NUMBER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static com.cshep4.monsterattack.game.utils.MovementUtils.movePlayerTowardsPoint;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerIdleSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerRunningSprite;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Player extends Character {
	private static final float PERIOD = 1f;

	private int numberOfBullets;
	private int numberOfBombs;
	private int remainingShieldTime;
	private float timeSeconds = 0f;
	private float destinationX;
	private float destinationY;

	private Player(Rectangle rectangle, String texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
		health = 5;
		numberOfBullets = BULLET_NUMBER;
		remainingShieldTime = 0;
		numberOfBombs = 0;
		destinationX = getMidX();
		destinationY = getMidY();
	}

	public static Player create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		return new Player(rectangle, CHARACTER_IDLE, 2, 1);
	}

	@Override
	public void update() {
		movePlayerTowardsPoint(this);

		if (xVel != 0 && yVel != 0) {
			changeAnimation(getPlayerRunningSprite(xVel, remainingShieldTime), 6, 1);
		}

		stopPlayerLeavingTheScreen();

		setX(getX() + xVel);
		setY(getY() + yVel);

		updateShield();
	}

	@Override
	public void loseLife() {
		if (remainingShieldTime <= 0) {
			super.loseLife();
		}
	}

	public void loseLifeRegardlessOfShield() {
		super.loseLife();
	}

	private void updateShield() {
		if (remainingShieldTime > 0) {
			decrementShieldPerSecond();
		}
	}

	private void decrementShieldPerSecond() {
		timeSeconds += Gdx.graphics.getRawDeltaTime();

		if(timeSeconds > PERIOD){
			timeSeconds-= PERIOD;
			remainingShieldTime -= 1;
			changeAnimationIfShieldRunsOut();
		}
	}

	private void changeAnimationIfShieldRunsOut() {
		if (remainingShieldTime == 0) {
			changeAnimation(CHARACTER_IDLE, 2, 1);
		}
	}

	private void stopPlayerLeavingTheScreen() {
		if (isPlayerLeavingScreenHorizontally()) {
			xVel = 0;
		}
		if (isPlayerLeavingScreenVertically()) {
			yVel = 0;
		}
	}

	private boolean isPlayerLeavingScreenHorizontally() {
		return (getX() < 0 && xVel < 0) || (getX() > getScreenXMax() - getWidth() && xVel > 0);
	}

	private boolean isPlayerLeavingScreenVertically() {
		return (getY() < 0 && yVel < 0) || (getY() > getScreenYMax() - getHeight() && yVel > 0);
	}

	public void stand() {
		xVel = 0;
		yVel = 0;
		changeAnimation(getPlayerIdleSprite(remainingShieldTime), 2, 1);
	}

	public Bullet shoot() {
        if (numberOfBombs > 0) {
            return shootBomb();
        }

        if (numberOfBullets > 0) {
            return shootBullet();
        }

        return null;
    }

	private Bullet shootBullet() {
		float x = getMidX();
		float y = getMidY();
		float width = getWidth() / BULLET_WIDTH_DIVIDER;
		float height = getHeight() / BULLET_HEIGHT_DIVIDER;

		numberOfBullets -= 1;

		return Bullet.create(PLAYER, x, y, width, height);
	}

	private Bomb shootBomb() {
		float x = getMidX();
		float y = getMidY();
		float width = (getWidth() / BOMB_SIZE_DIVIDER) * 4;
		float height = (getHeight() / BOMB_SIZE_DIVIDER) * 4;

		numberOfBombs -= 1;

		return Bomb.create(PLAYER, x, y, width, height);
	}

	@Override
	public void kill() {
		if (remainingShieldTime > 0) {
			remainingShieldTime = 0;
			return;
		}

		health = 0;
	}
}
