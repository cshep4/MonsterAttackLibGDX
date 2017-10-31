package com.cshep4.monsterattack.game;

public class Collision {

	public boolean bulletCollision(Bullet aBullet, GameObject aObject) {
        return aBullet.getRect().left < aObject.getRect().right &&
                aBullet.getRect().right > aObject.getRect().left &&
                aBullet.getRect().top < aObject.getRect().bottom &&
                aBullet.getRect().bottom > aObject.getRect().top;
	}
	
	public boolean objectCollision(GameObject aObject1, GameObject aObject2) {
        return aObject1.getRect().left < aObject2.getRect().right &&
                aObject1.getRect().right > aObject2.getRect().left &&
                aObject1.getRect().top < aObject2.getRect().bottom &&
                aObject1.getRect().bottom > aObject2.getRect().top;
	}
	
}
