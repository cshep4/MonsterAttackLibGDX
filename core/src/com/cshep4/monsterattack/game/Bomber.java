package com.cshep4.monsterattack.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B4_SPRITE_MOVE;

public class Bomber extends BomberEnemy {
	MyApp myApp = MyApp.getInstance();
		
	public Bomber(Rectangle rectangle, Texture texture, int type) {
		super(rectangle, texture);
		this.type = type;
		if (type == 1) {
			canShoot = false;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 100;
		} else if (type == 2) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 100;
		} else if (type == 3) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 200;
		} else if (type == 4) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = true;
			health = 200;
		}

//		xPos = aXPos;
//		yPos = aYPos;
//	    width = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER;
//	    height = myApp.getScreenWidth() / Constants.CHARACTER_HEIGHT_DIVIDER;
//	    setNewBitmap(myApp.bomberMoveIdle[aType-1], Constants.B_MOVE_DIVIDER);
	}
}
