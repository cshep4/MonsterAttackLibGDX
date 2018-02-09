package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.SoundWrapper;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.utils.EnemyUtils;

import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_SHIELD;

public class Standard extends RunningEnemy {
	private static long mutateTime = System.currentTimeMillis();

	private Standard(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int level) {
		super(rectangle, texture, frameCols, frameRows);
		this.level = level;
		EnemyUtils.setAbility(this);
	}

	public static Standard create(float x, float y, int level) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		String sprite;
		int frameCols = 2;
		int frameRows = 1;

		switch (level) {
			case 1:
				sprite = S1_SPRITE_MOVE_LEFT;
				break;
			case 2:
				sprite = S2_SPRITE_MOVE_LEFT;
				break;
			case 3:
				sprite = S3_SPRITE_MOVE_LEFT;
				break;
			case 4:
				sprite = S4_SPRITE_MOVE_LEFT;
				break;
			default:
				sprite = S1_SPRITE_MOVE_LEFT;
				break;
		}

		Texture texture = TextureFactory.create(sprite);

		return new Standard(rectangle, texture, frameCols, frameRows, level);
	}

	@Override
	public void update(){
		super.update();

		if (isValidMutation(level, mutateTime)) {
			mutate();
		}
	}

	private boolean isFacingLeft(float xVel) {
		return xVel < 0;
	}

	public void shieldAnimation() {
		changeAnimation(TextureFactory.create(S4_SPRITE_SHIELD), 2, 1);
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutation", level + "->" + (level +1));
		SoundWrapper.playMutateStandard();
		level += 1;
		changeAnimation(xVel);
		EnemyUtils.setAbility(this);
		updateMutateTime();
	}

	private static void updateMutateTime() {
		mutateTime = System.currentTimeMillis();
	}

	public void changeAnimation(float newXVel) {
		String textureFile;

		// Get texture file depending level and direction facing
		if (isFacingLeft(newXVel)) {
			switch (level) {
				case 1:
					textureFile = S1_SPRITE_MOVE_LEFT;
					break;
				case 2:
					textureFile = S2_SPRITE_MOVE_LEFT;
					break;
				case 3:
					textureFile = S3_SPRITE_MOVE_LEFT;
					break;
				case 4:
					textureFile = S4_SPRITE_MOVE_LEFT;
					break;
				default:
					return;
			}
		} else { // facing right
			switch (level) {
				case 1:
					textureFile = S1_SPRITE_MOVE_RIGHT;
					break;
				case 2:
					textureFile = S2_SPRITE_MOVE_RIGHT;
					break;
				case 3:
					textureFile = S3_SPRITE_MOVE_RIGHT;
					break;
				case 4:
					textureFile = S4_SPRITE_MOVE_RIGHT;
					break;
				default:
					return;
			}
		}

		changeAnimation(TextureFactory.create(textureFile), 2, 1);
	}
}
