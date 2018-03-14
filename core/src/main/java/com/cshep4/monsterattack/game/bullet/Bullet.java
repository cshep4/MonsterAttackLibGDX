package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.core.CharacterType;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.wrapper.Sound;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_BULLET;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_BULLET;
import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;

@EqualsAndHashCode(callSuper=false)
@Data
public class Bullet extends GameObject {
	private int xVel;

	Bullet(Rectangle rectangle, String texture, int xVel) {
		super (rectangle, texture, 1, 1);
		this.xVel = xVel;
	}

	public static Bullet create(CharacterType characterType, float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);

		int xVel;
		String texture;

		if (characterType  == PLAYER) {
			texture = PLAYER_BULLET;
			xVel = BULLET_SPEED;
		} else {
			texture = ENEMY_BULLET;
			xVel = -BULLET_SPEED;
		}

		return new Bullet(rectangle, texture, xVel);
	}

	public boolean update(List<RunningEnemy> enemyList, List<ProducerEnemy> producerEnemyList) {
		updatePosition();

		return enemyList.stream().anyMatch(this::hasCollidedWithRunningEnemy) ||
				producerEnemyList.stream().anyMatch(this::hasCollidedWithProducerEnemy);
	}


	public boolean update(Player player) {
		updatePosition();
		return hasCollidedWithPlayer(player);
	}

	protected void updatePosition(){
    	//Set the speed of the bullet
    	setX(getX() + getXVelByDeltaTime());
	}

	private float getXVelByDeltaTime() {
		return xVel*Gdx.graphics.getDeltaTime();
	}

	protected boolean hasCollidedWithPlayer(Player player) {
		if (hasCollided(this, player)) {
			player.loseLife();
			collisionSound();
			Gdx.app.log("Death!", "SHOT!");
			return true;
		}
		return false;
	}

	private boolean hasCollidedWithRunningEnemy(RunningEnemy runningEnemy) {
		if (hasCollided(this, runningEnemy)) {
			runningEnemyShot(runningEnemy);
			return true;
		}
		return false;
	}

	private boolean hasCollidedWithProducerEnemy(ProducerEnemy producerEnemy) {
		if (hasCollided(this, producerEnemy)) {
			producerEnemy.loseLife();
			collisionSound();
			return true;
		}
		return false;
	}

	private void runningEnemyShot(RunningEnemy runningEnemy) {
		if (!runningEnemy.isShielding()) {
			runningEnemy.loseLife();
			collisionSound();
		} else {
			runningEnemy.setShieldHealth(runningEnemy.getShieldHealth() - 1);
		}
	}

	public void collisionSound() {
		Sound.playEnemyHit();
	}
}
