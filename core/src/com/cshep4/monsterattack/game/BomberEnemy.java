package com.cshep4.monsterattack.game;

import java.util.ArrayList;

import android.util.Log;

public class BomberEnemy extends Enemy {
	
	//collision handling
	private Collision collision = new Collision();
	
	public BomberEnemy() {
		super();
	}
	
	@Override
	public void decisionTree(Player aPlayer, ArrayList<Bullet> aPlayerBullets, ArrayList<Bullet> aEnemyBullets) {
		super.decisionTree(aPlayer, aPlayerBullets, aEnemyBullets);
		if (this.checkPlayerClose(aPlayer)) {
			Log.v("AI", "Explode");
			this.explode(aPlayer);
		}
	}

	public void explode(Player aPlayer) {
		Sound sound = new Sound();
		sound.playExplode();
		MyApp myApp = MyApp.getInstance();
		this.width = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER *2;
		this.height = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER *2;
		this.setNewBitmap(myApp.explosion, 1);
		Log.v("Death", "BOMBED!");
		aPlayer.setHealth(0);
	}
	
	public boolean checkPlayerClose(Player aPlayer) {
		GameObject explosion = new GameObject();
		MyApp myApp = MyApp.getInstance();
		explosion.setNewBitmap(myApp.explosion,1);		
		
		explosion.setXPos(this.xPos - this.width/2);
		explosion.setYPos(this.yPos - this.height/2);
	    explosion.setWidth(this.width*2);
	    explosion.setHeight(this.width*2);
	    explosion.setObjectRect(explosion.getXPos(),explosion.getYPos(),explosion.getXPos()+explosion.getWidth(),explosion.getYPos()+explosion.getHeight());

        return collision.objectCollision(aPlayer, explosion);
	}
}
