package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B4_SPRITE_MOVE;

public class Bomber extends BomberEnemy implements Mutatable {
	private long mutateTime = 0;
		
	public Bomber(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int type) {
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
			health = 100;
			shieldHealth = 0;
		} else if (type == 2) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 100;
			shieldHealth = 0;
		} else if (type == 3) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 200;
			shieldHealth = 0;
		} else if (type == 4) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = true;
			health = 200;
			shieldHealth = 0;
		}
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

	@Override
	public void changeAnimation(float newXVel) {
		String textureFile;

		switch (type) {
			case 1:
				textureFile = B1_SPRITE_MOVE;
				break;
			case 2:
				textureFile = B2_SPRITE_MOVE;
				break;
			case 3:
				textureFile = B3_SPRITE_MOVE;
				break;
			case 4:
				textureFile = B4_SPRITE_MOVE;
				break;
			default:
				return;
		}

		changeAnimation(new Texture(textureFile), 7, 1);
	}

	@Override
	public void shieldAnimation() {}
}
