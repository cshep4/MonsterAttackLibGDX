package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.utils.EnemyUtils;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_SHIELD;
import static com.cshep4.monsterattack.game.core.SoundWrapper.playMutateStandard;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getStandardSpriteLeft;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getStandardSpriteRight;

@EqualsAndHashCode(callSuper=true)
public class Standard extends RunningEnemy {
	private static long mutateTime = System.currentTimeMillis();

	private Standard(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int level) {
		super(rectangle, texture, frameCols, frameRows);
		this.level = level;
		EnemyUtils.setAbility(this);
	}

	public static Standard create(float x, float y, int level) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		int frameCols = 2;
		int frameRows = 1;

		String sprite = getStandardSpriteLeft(level);

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
		Gdx.app.log("Mutation", level + "->" + (level + 1));
		playMutateStandard();
		level += 1;
		changeAnimation(xVel);
		EnemyUtils.setAbility(this);
		updateMutateTime();
	}

	private static void updateMutateTime() {
		mutateTime = System.currentTimeMillis();
	}

	@Override
    public void checkPlayerHasBeenKilled(Player player) {
        super.checkPlayerHasBeenKilled(player);

		//check if player has collided, if so KILL!!!
        if (getRectangle().overlaps(player.getRectangle())) {
            Gdx.app.log("Death", "COLLIDED!");
            player.setHealth(player.getHealth()-1);
            kill();
        }
    }


	public void changeAnimation(float newXVel) {
		String textureFile;

		// Get texture file depending level and direction facing
		if (isFacingLeft(newXVel)) {
			textureFile = getStandardSpriteLeft(level);
		} else { // facing right
			textureFile = getStandardSpriteRight(level);
		}

		changeAnimation(TextureFactory.create(textureFile), 2, 1);
	}
}
