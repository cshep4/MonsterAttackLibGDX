package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Standard extends Enemy implements Mutatable {
	private long mutateTime = 0;
	
	public Standard(Rectangle rectangle, Texture texture, int aType) {
		super(rectangle, texture);
		type = aType;
		if (type == 1) {
			canShoot = false;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
		} else if (type == 2) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
		} else if (type == 3) {
			canShoot = true;
			canDodge = true;
			canShield = false;
			canShootBombs = false;
		} else if (type == 4) {
			canShoot = true;
			canDodge = true;
			canShield = true;
			canShootBombs = false;
		}

	}

	@Override
	public void update(){
		super.update();

		if (this.isValidMutation(this.type, mutateTime)) {
			this.mutate();
		}
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutation", type + "->" + type+1);
		Sounds.playMutateStandard();
		this.type += 1;
		// TODO - Change sprite
		mutateTime = System.currentTimeMillis();
	}
}
