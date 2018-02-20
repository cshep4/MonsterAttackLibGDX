package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.SoundWrapper;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.SHOOT_BUTTON;

public class Bomb extends Bullet {

	private Bomb(Rectangle rectangle, Texture texture) {
		super(rectangle, texture, -BULLET_SPEED);
	}

	public static Bomb create(float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
		Texture texture = TextureFactory.create(SHOOT_BUTTON);

		return new Bomb(rectangle, texture);
	}

	@Override
	public void collisionSound() {
		SoundWrapper.playExplode();
	}	

}
