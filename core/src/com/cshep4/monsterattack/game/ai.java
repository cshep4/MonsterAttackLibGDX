package com.cshep4.monsterattack.game;

import java.util.ArrayList;

public interface ai {
	
	void decisionTree(Player aPlayer, ArrayList<Bullet> aPlayerBullets, ArrayList<Bullet> aEnemyBullets);
	
	boolean checkBulletClose(ArrayList<Bullet> aPlayerBullets);
	boolean checkEnemyInLineOfBullet(ArrayList<Bullet> aPlayerBullets);
	boolean checkPlayerInLineOfSight(Player aPlayer);
}
