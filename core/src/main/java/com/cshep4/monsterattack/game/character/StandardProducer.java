package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.math.Rectangle;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;

@EqualsAndHashCode(callSuper=true)
public class StandardProducer extends ProducerEnemy {
	private StandardProducer(Rectangle rectangle, String texture) {
		super(rectangle, texture, SP_IDLE_COLS, SP_IDLE_ROWS);
	}

	public static StandardProducer create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		return new StandardProducer(rectangle, SP_SPRITE_IDLE);
	}
}
