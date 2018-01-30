package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_PRODUCE;

public class StandardProducer extends ProducerEnemy {
	private StandardProducer(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
	}

	public static StandardProducer create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		Texture texture = TextureFactory.create(SP_SPRITE_IDLE);
		int frameCols = 8;
		int frameRows = 1;

		return new StandardProducer(rectangle, texture, frameCols, frameRows);
	}

	@Override
	public void changeAnimation(float newXVel) {
		// not implemented yet
	}

	@Override
	public void shieldAnimation() {
		// not implemented yet
	}

	@Override
	protected void setProducingSprite() {
		changeAnimation(TextureFactory.create(SP_SPRITE_PRODUCE),2, 1);
	}

	@Override
	protected void resetSprite() {
		changeAnimation(TextureFactory.create(SP_SPRITE_IDLE),8, 1);
	}

}
