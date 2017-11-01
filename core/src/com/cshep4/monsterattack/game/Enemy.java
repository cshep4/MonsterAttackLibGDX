package com.cshep4.monsterattack.game;

import java.util.ArrayList;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Character implements ai {
	protected boolean canShoot;
	protected boolean canDodge;
	protected boolean canShield;
	protected boolean canShootBombs;
	protected boolean sheilded;
	private int bulletY;
	private long shootTime = System.currentTimeMillis();
	protected MyApp myApp = MyApp.getInstance();
	protected int type;
	
	public Enemy(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
		this.directionFacing = Constants.LEFT;
	}
	
	public boolean CanShoot(){
		return canShoot;
	}
	
	public boolean CanDodge(){
		return canShoot;
	}
	
	public boolean CanShield(){
		return canShoot;
	}
	
	public boolean CanShootBombs(){
		return canShoot;
	}	
	
	public Bullet shoot(){
		// cast libGDX values to original
		int width = (int) getRectangle().getWidth();
		int height = (int) getRectangle().getHeight();
		int xPos = (int) getRectangle().getX();
		int yPos = (int) getRectangle().getY();

		// create and return a bullet
		this.sheilded = false;
		shootTime = System.currentTimeMillis();
		Bullet bullet;
		if (this.canShootBombs) {	
			int W = width/ Constants.BULLET_SIZE_DIVIDER *6;
			int H = height/ Constants.BULLET_SIZE_DIVIDER *6;
			int x = xPos +(width/2)-W/2;
			int y = yPos +(height/2)-H/2;
			bullet = new Bomb(x, y, W, H);
			Gdx.app.log("AI","Shoot Bomb!");
		}
		else {
			bullet = new Bullet(xPos+(width /2), yPos+(height /2), height / Constants.BULLET_SIZE_DIVIDER, width / Constants.BULLET_SIZE_DIVIDER);
			Gdx.app.log("AI","Shoot Bullet");
		}
		bullet.setXVel(-bullet.getXVel());
		return bullet;		
	}
	
	public void shield() {
		Gdx.app.log("AI","Shield");
		xVel = 0;
		yVel = 0;
		sheilded = true;
	}
	
	public void dodge() {
		// cast libGDX values to original
		int width = (int) getRectangle().getWidth();
		int height = (int) getRectangle().getHeight();
		int xPos = (int) getRectangle().getX();
		int yPos = (int) getRectangle().getY();

		Gdx.app.log("AI","Dodge");
		//String string = "BulletY: " + Integer.toString(bulletY);
		//Log.v("BulletPos",string);
		this.sheilded = false;		
		if (xVel != Constants.ENEMY_SPEED) {
			//directionChange = true;
			xVel = Constants.ENEMY_SPEED;
			this.directionFacing = Constants.RIGHT;
		}
		 if (this.directionFacing == Constants.RIGHT) {
			// this.setNewBitmap(myApp.standardMoveIdleLeft[this.type-1], Constants.S_MOVE_DIVIDER);
		 } else {
			 //this.setNewBitmap(myApp.standardMoveIdle[this.type-1], Constants.S_MOVE_DIVIDER);
		 }		
		//IF THE BULLET IS LOW MOVE UP, ELSE MOVE DOWN
		if ((bulletY != 0) && ((yPos+height/2) < bulletY)) {
			if (yPos < 0) {
				this.yVel = Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = -Constants.ENEMY_SPEED;
				}
			}
		}
		else {
			if (yPos > myApp.getScreenHeight() - height) {
				yVel = -Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = Constants.ENEMY_SPEED;
				}
			}

		}
	}	
	
	public void moveForward() {
		// cast libGDX values to original
		int width = (int) getRectangle().getWidth();
		int height = (int) getRectangle().getHeight();
		int xPos = (int) getRectangle().getX();
		int yPos = (int) getRectangle().getY();

		Gdx.app.log("AI","Move Forward");
		if (this.canDodge) {
			if (this.directionFacing == Constants.RIGHT) {
//				this.setNewBitmap(myApp.standardMoveIdleLeft[this.type-1], Constants.S_MOVE_DIVIDER);
			} else {
//				this.setNewBitmap(myApp.standardMoveIdle[this.type-1], Constants.S_MOVE_DIVIDER);
			}	
		}
		this.sheilded = false;
		this.xVel = -Constants.ENEMY_SPEED;
//		this.yVel = 0;

		if (yPos < 0) {
			this.yVel = Constants.ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
		if (yPos > myApp.getScreenHeight() - height) {
			yVel = -Constants.ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
	}
	
	public void setShielded(boolean shielded) {
		this.sheilded = shielded;
	}
	
	public void decisionTree(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets) {
		// check decision		
		//IS BULLET CLOSE? & CAN ENEMY SHIELD?
		if (this.checkBulletClose(playerBullets) && this.canShield) {
			if(!this.sheilded) {
//				this.setNewBitmap(myApp.standardShield, Constants.S_SHIELD_DIVIDER);
			}
			this.shield();
		}//IS ENEMY IN LINE OF BULLET? & CAN ENEMY DODGE?
		else if (checkEnemyInLineOfBullet(playerBullets) && this.canDodge) {
			if(this.sheilded) {
//				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}
			this.dodge();
		}//IS PLAYER IN LINE OF ENEMY SIGHT? & CAN ENEMY SHOOT?
		else if (checkPlayerInLineOfSight(player) && this.canShoot) {
			if(this.sheilded) {
//				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}			
			if (checkShootDelay()) {
				enemyBullets.add(this.shoot());
			}
		}//ELSE RUN FORWARD
		else {
			if(this.sheilded) {
//				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}			
			this.moveForward();
		}
		this.update();
	}
	
	public boolean checkBulletClose(ArrayList<Bullet> playerBullets) {
		if (playerBullets != null) {
			for (int bulletLoop =0; bulletLoop < playerBullets.size(); bulletLoop++){
				if (playerBullets.get(bulletLoop) != null) {
					if 	(Math.abs(this.getRectangle().getX()-playerBullets.get(bulletLoop).getXPos())<100 &&
						checkEnemyInLineOfBullet(playerBullets)) {
						return true;
					}
				}		
			}
		}
		return false;
	}	
	
	public boolean checkEnemyInLineOfBullet(ArrayList<Bullet> playerBullets) {
		if (playerBullets != null) {
			for (int bulletLoop =0; bulletLoop < playerBullets.size(); bulletLoop++){
				if (playerBullets.get(bulletLoop) != null) {
					if 	(playerBullets.get(bulletLoop).getRect().top < this.getRect().bottom &&
						playerBullets.get(bulletLoop).getRect().bottom > this.getRect().top &&
						playerBullets.get(bulletLoop).getRect().right < this.getRect().left) {
						bulletY = playerBullets.get(bulletLoop).getYPos();
						return true;
					}
				}		
			}
		}
		return false;
	}	
	
	public boolean checkPlayerInLineOfSight(Player player) {
		return player.getRect().top < this.getRect().bottom &&
				player.getRect().bottom > this.getRect().top;
	}
	
	private boolean checkShootDelay() {
		return System.currentTimeMillis() - shootTime > Constants.SHOOT_DELAY;
	}

	public void setType(int aType){
		type = aType;
	}

	public int getType() {
		return type;
	}
}
