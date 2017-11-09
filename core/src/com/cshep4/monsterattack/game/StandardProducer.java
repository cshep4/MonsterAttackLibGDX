package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class StandardProducer extends ProducerEnemy {

	public StandardProducer(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	@Override
	public void changeAnimation(float newXVel) {}

	@Override
	public void shieldAnimation() {}

}
