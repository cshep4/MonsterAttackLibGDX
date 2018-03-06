package com.cshep4.monsterattack.game.character;

public interface Enemy {
	void moveForward();
	void checkPlayerHasBeenKilled(Player player);
}
