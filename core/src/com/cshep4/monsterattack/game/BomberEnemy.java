package com.cshep4.monsterattack.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class BomberEnemy extends Enemy {
	
	//collision handling
//	private Collision collision = new Collision();
	
	public BomberEnemy(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
	}
	
	@Override
	public void decisionTree(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets) {
		super.decisionTree(player, playerBullets, enemyBullets);
		if (this.checkPlayerClose(player)) {
			Gdx.app.log("AI", "Explode");
			this.explode(player);
		}
	}

	public void explode(Player player) {
		Sounds.playExplode();
		MyApp myApp = MyApp.getInstance();
		this.getRectangle().setWidth(myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER *2);
		this.getRectangle().setHeight(myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER *2);
//		this.setNewBitmap(myApp.explosion, 1);
		Gdx.app.log("Death", "BOMBED!");
		player.setHealth(0);
	}
	
	public boolean checkPlayerClose(Player player) {
		Rectangle explosion = new Rectangle();
		MyApp myApp = MyApp.getInstance();
//		explosion.setNewBitmap(myApp.explosion,1);
		
		explosion.setX(this.getRectangle().getX() - this.getRectangle().getWidth()/2);
		explosion.setY(this.getRectangle().getY() - this.getRectangle().getHeight()/2);
	    explosion.setWidth(this.getRectangle().getWidth()*2);
	    explosion.setHeight(this.getRectangle().getHeight()*2);
//	    explosion.setObjectRect(explosion.getXPos(),explosion.getYPos(),explosion.getXPos()+explosion.getWidth(),explosion.getYPos()+explosion.getHeight());

        return explosion.overlaps(player.getRectangle());
	}
}
