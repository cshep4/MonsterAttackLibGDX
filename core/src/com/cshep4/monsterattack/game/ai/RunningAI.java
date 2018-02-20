package com.cshep4.monsterattack.game.ai;

import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Player;

import java.util.List;

public interface RunningAI extends Enemy {
	void decisionTree(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets);
	
	boolean isBulletClose(List<Bullet> playerBullets);
	boolean isEnemyInLineOfBullet(List<Bullet> playerBullets);
	boolean isPlayerInLineOfSight(Player player);
}
