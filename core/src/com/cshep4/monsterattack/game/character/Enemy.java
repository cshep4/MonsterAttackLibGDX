package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;

import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;

public interface Enemy {
	public void moveForward();
	public void checkPlayerHasBeenKilled(Player player);
}
