package com.cshep4.monsterattack.game;

public final class Collision {

	public static boolean bulletCollision(Bullet bullet, GameObjectOld object) {
        return bullet.getRect().left < object.getRect().right &&
				bullet.getRect().right > object.getRect().left &&
				bullet.getRect().top < object.getRect().bottom &&
				bullet.getRect().bottom > object.getRect().top;
	}
	
	public static boolean objectCollision(GameObjectOld object1, GameObjectOld object2) {
        return object1.getRect().left < object2.getRect().right &&
				object1.getRect().right > object2.getRect().left &&
				object1.getRect().top < object2.getRect().bottom &&
				object1.getRect().bottom > object2.getRect().top;
	}
	
}
