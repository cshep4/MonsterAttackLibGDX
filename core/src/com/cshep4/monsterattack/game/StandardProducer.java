package com.cshep4.monsterattack.game;

public class StandardProducer extends ProducerEnemy {

	public StandardProducer(int aXPos, int aYPos) {
		super(aXPos, aYPos);
		setNewBitmap(myApp.standardMoveIdle[4], Constants.S_P_IDLE_DIVIDER);
	}

}
