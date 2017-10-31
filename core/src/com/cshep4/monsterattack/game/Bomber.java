package com.cshep4.monsterattack.game;


public class Bomber extends BomberEnemy {
	MyApp myApp = MyApp.getInstance();
		
	public Bomber(int aXPos, int aYPos, int aType) {
		super(/*R.drawable.background*/);
		type = aType;
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

		xPos = aXPos;
		yPos = aYPos;
	    width = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER;
	    height = myApp.getScreenWidth() / Constants.CHARACTER_HEIGHT_DIVIDER;
	    setNewBitmap(myApp.bomberMoveIdle[aType-1], Constants.B_MOVE_DIVIDER);
	}
}
