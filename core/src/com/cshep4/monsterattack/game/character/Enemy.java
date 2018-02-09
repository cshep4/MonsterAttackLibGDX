package com.cshep4.monsterattack.game.character;

public interface Enemy {
	public void moveForward();
	public void checkPlayerHasBeenKilled(Player player);
}
