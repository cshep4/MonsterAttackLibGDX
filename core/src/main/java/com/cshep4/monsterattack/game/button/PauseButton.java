package com.cshep4.monsterattack.game.button;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.PAUSE_BUTTON;

@EqualsAndHashCode(callSuper=true)
public class PauseButton extends GameObject {
	
	private PauseButton(Rectangle rectangle, String texture) {
		super(rectangle, texture, 1, 1);
	}

	public static PauseButton create(float x, float y, float width, float height) {
		Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
		return new PauseButton(rectangle, PAUSE_BUTTON);
	}
}
