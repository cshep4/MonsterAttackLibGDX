package com.cshep4.monsterattack.game;

import java.util.ArrayList;

public interface ai {
	
	void decisionTree(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets);
	
	boolean checkBulletClose(ArrayList<Bullet> playerBullets);
	boolean checkEnemyInLineOfBullet(ArrayList<Bullet> playerBullets);
	boolean checkPlayerInLineOfSight(Player player);
}
