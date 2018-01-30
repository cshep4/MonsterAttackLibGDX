package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_PRODUCE;

public class BomberProducer extends ProducerEnemy {

	private BomberProducer(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	public static BomberProducer create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		Texture texture = TextureFactory.create(BP_SPRITE_IDLE);
		int frameCols = 2;
		int frameRows = 1;

		return new BomberProducer(rectangle, texture, frameCols, frameRows);
	}

	@Override
	public void changeAnimation(float newXVel) {
		// not yet implemented
	}

	@Override
	public void shieldAnimation() {
		// not yet implemented
	}

	@Override
	protected void setProducingSprite() {
		Texture texture = TextureFactory.create(BP_SPRITE_PRODUCE);
		changeAnimation(texture,1, 1);
	}

	@Override
	protected void resetSprite() {
		Texture texture = TextureFactory.create(BP_SPRITE_IDLE);
		changeAnimation(texture,2, 1);
	}
}
