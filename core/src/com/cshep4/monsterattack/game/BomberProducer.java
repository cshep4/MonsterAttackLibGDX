package com.cshep4.monsterattack.game;

public class BomberProducer extends ProducerEnemy {
	public BomberProducer(int aXPos, int aYPos) {
		super(aXPos, aYPos);
		setNewBitmap(myApp.bomberMoveIdle[4], Constants.B_P_IDLE_DIVIDER);
	}

}
