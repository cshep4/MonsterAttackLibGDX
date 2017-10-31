package com.cshep4.monsterattack.game;


public class Standard extends Enemy {
	MyApp myApp = MyApp.getInstance();
	
	public Standard(int aXPos, int aYPos, int aType) {
		super();////////////////////////////////////////////////////////////////////////////////If type ==
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
		
		xPos = aXPos;
		yPos = aYPos;
	    width = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER;
	    height = myApp.getScreenWidth() / Constants.CHARACTER_HEIGHT_DIVIDER;
	    setNewBitmap(myApp.standardMoveIdle[aType-1], Constants.S_MOVE_DIVIDER);
	}
}
