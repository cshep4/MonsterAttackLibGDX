package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.SP_SPRITE_PRODUCE;
import static com.cshep4.monsterattack.game.Constants.SP_SPRITE_IDLE;

public class StandardProducer extends ProducerEnemy {
	public StandardProducer(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	@Override
	public void changeAnimation(float newXVel) {}

	@Override
	public void shieldAnimation() {}

	@Override
	protected void setProducingSprite() {
		changeAnimation(new Texture(Gdx.files.internal(SP_SPRITE_PRODUCE)),2, 1);
	}

	@Override
	protected void resetSprite() {
		changeAnimation(new Texture(Gdx.files.internal(SP_SPRITE_IDLE)),8, 1);
	}

}
