package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.core.SoundWrapper;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import java.util.List;

import lombok.Data;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_BULLET;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_BULLET;

@Data
public class Bullet extends GameObject {
	private int xVel;

	protected Bullet (Rectangle rectangle, Texture texture, int xVel) {
		super (rectangle, texture, 1, 1);
		this.xVel = xVel;
	}

	public static Bullet create(Character character, float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);

		int xVel;

		Texture texture;
		if (character instanceof Player) {
			texture = TextureFactory.create(PLAYER_BULLET);
			xVel = BULLET_SPEED;
		} else {
			texture = TextureFactory.create(ENEMY_BULLET);
			xVel = -BULLET_SPEED;
		}

		return new Bullet(rectangle, texture, xVel);
	}

	public boolean update(List<Enemy> enemyList, List<ProducerEnemy> producerEnemyList) {
		updatePosition();
		return enemyList.stream().anyMatch(this::checkCollisions) ||
				producerEnemyList.stream().anyMatch(this::checkCollisions);
	}

	public boolean update(Player player) {
		updatePosition();
		return checkCollisions(player);
	}

	private void updatePosition(){
    	//Set the speed of the bullet
    	getRectangle().setX(getRectangle().getX() + getXVelByDeltaTime());
	}

	private float getXVelByDeltaTime() {
		return xVel*Gdx.graphics.getDeltaTime();
	}

	private boolean checkCollisions(Player player) {
		if (getRectangle().overlaps(player.getRectangle())) {
			player.setHealth(player.getHealth()-100);
			collisionSound();
			Gdx.app.log("Death", "SHOT!");
			return true;
		}
		return false;
	}

	private boolean checkCollisions(Enemy enemy) {
		if (getRectangle().overlaps(enemy.getRectangle())) {
			enemyShot(enemy);
			return true;
		}
		return false;
	}

	private void enemyShot(Enemy enemy) {
		if (!enemy.isSheilded()) {
			enemy.setHealth(enemy.getHealth() - 100);
			collisionSound();
		} else {
			enemy.setShieldHealth(enemy.getShieldHealth() - 100);
		}
	}

	public void collisionSound() {
		SoundWrapper.playEnemyHit();
	}
}
