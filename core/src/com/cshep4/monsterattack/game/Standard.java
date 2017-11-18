package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.S1_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.Constants.S1_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.Constants.S2_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.Constants.S2_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.Constants.S3_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.Constants.S3_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.Constants.S4_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.Constants.S4_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.Constants.S4_SPRITE_SHIELD;

public class Standard extends Enemy implements Mutatable {
	private static long mutateTime = 0;
	
	public Standard(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int type) {
		super(rectangle, texture, frameCols, frameRows);
		this.type = type;
		setAbility();
	}

	@Override
	public void update(){
		super.update();

		if (this.isValidMutation(this.type, mutateTime)) {
			this.mutate();
		}
	}

	private void setAbility() {
		if (type == 1) {
			canShoot = false;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			shieldHealth = 0;
		} else if (type == 2) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			shieldHealth = 0;
		} else if (type == 3) {
			canShoot = true;
			canDodge = true;
			canShield = false;
			canShootBombs = false;
			shieldHealth = 0;
		} else if (type == 4) {
			canShoot = true;
			canDodge = true;
			canShield = true;
			canShootBombs = false;
			shieldHealth = 200;
		}
	}

	public void changeAnimation(float newXVel) {
		String textureFile;

		// Get texture file depending type and direction facing
		if (newXVel < 0) {// facing left
			switch (type) {
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
			switch (type) {
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

		changeAnimation(new Texture(textureFile), 2, 1);
	}

	public void shieldAnimation() {
		changeAnimation(new Texture(S4_SPRITE_SHIELD), 2, 1);
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutation", type + "->" + (type+1));
		Sounds.playMutateStandard();
		this.type += 1;
		changeAnimation(this.xVel);
		setAbility();
		mutateTime = System.currentTimeMillis();
	}
}
