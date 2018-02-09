package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;

public class StandardProducer extends ProducerEnemy {

	private StandardProducer(Rectangle rectangle, Texture texture) {
		super(rectangle, texture, SP_IDLE_COLS, SP_IDLE_ROWS);
	}

	public static StandardProducer create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		Texture texture = TextureFactory.create(SP_SPRITE_IDLE);

		return new StandardProducer(rectangle, texture);
	}
}
