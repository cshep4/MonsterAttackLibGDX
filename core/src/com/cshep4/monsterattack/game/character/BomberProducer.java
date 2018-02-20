package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;

@EqualsAndHashCode(callSuper=true)
public class BomberProducer extends ProducerEnemy {
	private BomberProducer(Rectangle rectangle, Texture texture) {
		super(rectangle, texture, BP_IDLE_COLS, BP_IDLE_ROWS);
	}

	public static BomberProducer create(float x, float y) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		Texture texture = TextureFactory.create(BP_SPRITE_IDLE);

		return new BomberProducer(rectangle, texture);
	}
}