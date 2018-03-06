package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.wrapper.Sound;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.cshep4.monsterattack.game.constants.Constants.BOMB;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;
import static java.util.stream.Collectors.toList;

public class Bomb extends Bullet {

	private Bomb(Rectangle rectangle, String texture, int xVel) {
		super(rectangle, texture, xVel);
	}

	public static Bomb create(int character, float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);

		int xVel;

		if (character  == PLAYER) {
			xVel = BULLET_SPEED;
		} else {
			xVel = -BULLET_SPEED;
		}

		return new Bomb(rectangle, BOMB, xVel);
	}

	@Override
	public void collisionSound() {
		Sound.playExplode();
	}

	@Override
	protected boolean hasCollidedWithPlayer(Player player) {
		if (hasCollided(this, player)) {
			player.kill();
			collisionSound();
			Gdx.app.log("Death!", "BOMBED!");
			return true;
		}
		return false;
	}

	@Override
	public boolean update(List<RunningEnemy> enemyList, List<ProducerEnemy> producerEnemyList) {
		updatePosition();

		// create a single list from both types of enemy
		List<? extends Character> enemies = Stream.of(enemyList, producerEnemyList)
				.flatMap(Collection::stream)
				.collect(toList());

		//kill all enemies that are in range
		long enemiesDead = enemies.stream()
				.filter(this::hasCollidedWithEnemy)
				.count();

		return enemiesDead > 0;
	}

	private boolean hasCollidedWithEnemy(Character enemy) {
		if (hasCollided(this, enemy)) {
			enemy.kill();
			collisionSound();
			return true;
		}
		return false;
	}

}
