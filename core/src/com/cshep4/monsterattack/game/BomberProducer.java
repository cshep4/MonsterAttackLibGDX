package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.Constants.BP_SPRITE_PRODUCE;

public class BomberProducer extends ProducerEnemy {

	public BomberProducer(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	@Override
	public void changeAnimation(float newXVel) {}

	@Override
	public void shieldAnimation() {}

	@Override
	protected void setProducingSprite() {
		changeAnimation(new Texture(Gdx.files.internal(BP_SPRITE_PRODUCE)),1, 1);
	}

	@Override
	protected void resetSprite() {
		changeAnimation(new Texture(Gdx.files.internal(BP_SPRITE_IDLE)),2, 1);
	}
}
