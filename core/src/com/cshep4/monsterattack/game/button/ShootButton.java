package com.cshep4.monsterattack.game.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.SHOOT_BUTTON;

@EqualsAndHashCode(callSuper=true)
public class ShootButton extends GameObject {

	private ShootButton(Rectangle rectangle, Texture texture) {
		super(rectangle, texture, 1, 1);
	}

	public static ShootButton create(float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
		Texture texture = TextureFactory.create(SHOOT_BUTTON);

		return new ShootButton(rectangle, texture);
	}
}
