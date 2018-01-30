package com.cshep4.monsterattack.game.core;

import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.character.Player;

import java.util.List;

public interface AI {
	
	void decisionTree(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets);
	
	boolean checkBulletClose(List<Bullet> playerBullets);
	boolean checkEnemyInLineOfBullet(List<Bullet> playerBullets);
	boolean checkPlayerInLineOfSight(Player player);
}
