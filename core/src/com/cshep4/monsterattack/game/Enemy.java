package com.cshep4.monsterattack.game;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class Enemy extends GameObject implements ai {
	protected boolean canShoot;
	protected boolean canDodge;
	protected boolean canShield;
	protected boolean canShootBombs;
	protected boolean sheilded;
	private int bulletY;
	private long shootTime = System.currentTimeMillis();
	protected MyApp myApp = MyApp.getInstance();
	protected int type;
	
	public Enemy() {
		super();
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
		// create and return a bullet
		this.sheilded = false;
		shootTime = System.currentTimeMillis();
		Bullet bullet;
		if (this.canShootBombs) {	
			int W = width/ Constants.BULLET_SIZE_DIVIDER *6;
			int H = width/ Constants.BULLET_SIZE_DIVIDER *6;
			int x = xPos +(width/2)-W/2;
			int y = yPos +(height/2)-H/2;
			bullet = new Bomb(x, y, W, H);
			Log.v("AI","Shoot Bomb!");
		}
		else {
			bullet = new Bullet(xPos+(width /2), yPos+(height /2), height / Constants.BULLET_SIZE_DIVIDER, width / Constants.BULLET_SIZE_DIVIDER);
			Log.v("AI","Shoot Bullet");
		}
		bullet.setXVel(-bullet.getXVel());
		return bullet;		
	}
	
	public void shield() {
		Log.v("AI","Shield");
		xVel = 0;
		yVel = 0;
		sheilded = true;
	}
	
	public void dodge() {
		Log.v("AI","Dodge");
		//String string = "BulletY: " + Integer.toString(bulletY);
		//Log.v("BulletPos",string);
		this.sheilded = false;		
		if (xVel != Constants.ENEMY_SPEED) {
			directionChange = true;
			xVel = Constants.ENEMY_SPEED;
			this.directionFacing = Constants.RIGHT;
		}
		 if (this.directionFacing == Constants.RIGHT) {
			 this.setNewBitmap(myApp.standardMoveIdleLeft[this.type-1], Constants.S_MOVE_DIVIDER);
		 } else {
			 this.setNewBitmap(myApp.standardMoveIdle[this.type-1], Constants.S_MOVE_DIVIDER);
		 }		
		//IF THE BULLET IS LOW MOVE UP, ELSE MOVE DOWN
		if ((bulletY != 0) && ((this.yPos+this.height/2) < bulletY)) {
			if (this.yPos < 0) {
				this.yVel = Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = -Constants.ENEMY_SPEED;
				}
			}
		}
		else {
			if (this.yPos > myApp.getScreenHeight() - this.height) {
				yVel = -Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = Constants.ENEMY_SPEED;
				}
			}

		}
	}	
	
	public void moveForward() {
		Log.v("AI","Move Forward");
		if (this.canDodge) {
			if (this.directionFacing == Constants.RIGHT) {
				this.setNewBitmap(myApp.standardMoveIdleLeft[this.type-1], Constants.S_MOVE_DIVIDER);
			} else {
				this.setNewBitmap(myApp.standardMoveIdle[this.type-1], Constants.S_MOVE_DIVIDER);
			}	
		}
		this.sheilded = false;
		this.xVel = -Constants.ENEMY_SPEED;
//		this.yVel = 0;

		if (this.yPos < 0) {
			this.yVel = Constants.ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
		if (this.yPos > myApp.getScreenHeight() - this.height) {
			yVel = -Constants.ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
	}
	
	public void setShielded(boolean aShielded) {
		sheilded = aShielded;
	}
	
	public void decisionTree(Player aPlayer, ArrayList<Bullet> aPlayerBullets, ArrayList<Bullet> aEnemyBullets) {		
		// check decision		
		//IS BULLET CLOSE? & CAN ENEMY SHIELD?
		if (this.checkBulletClose(aPlayerBullets) && this.canShield) {
			if(!this.sheilded) {
				this.setNewBitmap(myApp.standardShield, Constants.S_SHIELD_DIVIDER);
			}
			this.shield();
		}//IS ENEMY IN LINE OF BULLET? & CAN ENEMY DODGE?
		else if (checkEnemyInLineOfBullet(aPlayerBullets) && this.canDodge) {
			if(this.sheilded) {
				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}
			this.dodge();
		}//IS PLAYER IN LINE OF ENEMY SIGHT? & CAN ENEMY SHOOT?
		else if (checkPlayerInLineOfSight(aPlayer) && this.canShoot) {
			if(this.sheilded) {
				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}			
			if (checkShootDelay()) {
				aEnemyBullets.add(this.shoot());
			}
		}//ELSE RUN FORWARD
		else {
			if(this.sheilded) {
				this.setNewBitmap(myApp.standardMoveIdle[3], Constants.S_MOVE_DIVIDER);
			}			
			this.moveForward();
		}
		this.update();
	}
	
	public boolean checkBulletClose(ArrayList<Bullet> aPlayerBullets) {
		if (aPlayerBullets != null) {
			for (int bulletLoop =0; bulletLoop < aPlayerBullets.size(); bulletLoop++){
				if (aPlayerBullets.get(bulletLoop) != null) {
					if 	(Math.abs(this.getXPos()-aPlayerBullets.get(bulletLoop).getXPos())<100 &&
						 checkEnemyInLineOfBullet(aPlayerBullets)) {
						return true;
					}
				}		
			}
		}
		return false;
	}	
	
	public boolean checkEnemyInLineOfBullet(ArrayList<Bullet> aPlayerBullets) {
		if (aPlayerBullets != null) {
			for (int bulletLoop =0; bulletLoop < aPlayerBullets.size(); bulletLoop++){
				if (aPlayerBullets.get(bulletLoop) != null) {	
					if 	(aPlayerBullets.get(bulletLoop).getRect().top < this.getRect().bottom &&
						 aPlayerBullets.get(bulletLoop).getRect().bottom > this.getRect().top && 
						 aPlayerBullets.get(bulletLoop).getRect().right < this.getRect().left) {
						bulletY = aPlayerBullets.get(bulletLoop).getYPos();
						return true;
					}
				}		
			}
		}
		return false;
	}	
	
	public boolean checkPlayerInLineOfSight(Player aPlayer) {
		return aPlayer.getRect().top < this.getRect().bottom &&
				aPlayer.getRect().bottom > this.getRect().top;
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
