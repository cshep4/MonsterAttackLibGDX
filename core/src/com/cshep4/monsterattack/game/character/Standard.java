package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.utils.EnemyUtils;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_SHIELD;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getStandardSpriteLeft;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getStandardSpriteRight;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;
import static com.cshep4.monsterattack.game.wrapper.Sound.playMutateStandard;

@EqualsAndHashCode(callSuper=true)
public class Standard extends RunningEnemy {
	private Standard(Rectangle rectangle, String texture, int frameCols, int frameRows, int level) {
		super(rectangle, texture, frameCols, frameRows);
		this.level = level;
		EnemyUtils.setAbility(this);
	}

	public static Standard create(float x, float y, int level) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		int frameCols = 2;
		int frameRows = 1;

		return new Standard(rectangle, getStandardSpriteLeft(level), frameCols, frameRows, level);
	}

	@Override
	public void update(){
		super.update();

		if (isValidMutation()) {
			mutate();
		}
	}

	private boolean isFacingLeft(float xVel) {
		return xVel < 0;
	}

	public void shieldAnimation() {
		changeAnimation(S4_SPRITE_SHIELD, 2, 1);
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutate", level + "->" + (level + 1));
		playMutateStandard();
		level += 1;
		changeAnimation(xVel);
		EnemyUtils.setAbility(this);
		updateMutateTime();
	}

	@Override
    public void checkPlayerHasBeenKilled(Player player) {
        super.checkPlayerHasBeenKilled(player);

		//check if player has collided, if so KILL!!!
        if (hasCollided(this, player)) {
            Gdx.app.log("Hit!", "COLLIDED!");
            player.loseLife();
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

		changeAnimation(textureFile, 2, 1);
	}
}
