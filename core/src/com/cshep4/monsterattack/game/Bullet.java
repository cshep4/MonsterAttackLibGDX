package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

import static com.cshep4.monsterattack.game.Constants.BULLET_SPEED;

public class Bullet extends GameObject {
	protected int xVel;

	public Bullet (Rectangle rectangle, Texture texture) {
		super (rectangle, texture, 1, 1);
		xVel = BULLET_SPEED;
	}

	public boolean update(List<Enemy> enemyList, List<ProducerEnemy> producerEnemyList) {
		this.updatePosition();
		return enemyList.stream().anyMatch(this::checkCollisionsWithShield) ||
				producerEnemyList.stream().anyMatch(this::checkCollisions);
	}

	public boolean update(Player player) {
		this.updatePosition();
		return checkCollisions(player);
	}

	public void updatePosition(){
    	//Set the speed of the bullet
    	getRectangle().setX(getRectangle().getX() + this.xVel);
	}

	public boolean checkCollisions(Character character) {
		if (this.getRectangle().overlaps(character.getRectangle())) {
			character.setHealth(character.getHealth()-100);
			if (character instanceof Player) { Gdx.app.log("Death", "SHOT!"); }
			collisionSound();
			return true;
		}
		return false;
	}

	public boolean checkCollisionsWithShield(Enemy enemy) {
		if (this.getRectangle().overlaps(enemy.getRectangle())) {
			if (!enemy.isSheilded()) {
				enemy.setHealth(enemy.getHealth() - 100);
				collisionSound();
			}
			return true;
		}
		return false;
	}
	
	public void setXVel(int xVel){
		this.xVel = xVel;
	}
	
	public int getXVel() {
		return this.xVel;
	}

	public void collisionSound() {
		Sounds.playEnemyHit();
	}
}
