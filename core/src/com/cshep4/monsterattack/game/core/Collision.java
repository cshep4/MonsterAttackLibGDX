package com.cshep4.monsterattack.game.core;

import com.cshep4.monsterattack.game.bullet.Bullet;

public final class Collision {
	private Collision() {}
	public static boolean bulletCollision(Bullet bullet, GameObject object) {
		return bullet.getRectangle().overlaps(object.getRectangle());
	}

	public static boolean objectCollision(GameObject object1, GameObject object2) {
		return object1.getRectangle().overlaps(object2.getRectangle());
	}
	
}
