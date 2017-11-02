package com.cshep4.monsterattack.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy extends Character implements ai {
	protected boolean canShoot;
	protected boolean canDodge;
	protected boolean canShield;
	protected boolean canShootBombs;
	protected boolean sheilded;
	private float bulletY;
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
		// create and return a bullet
		this.sheilded = false;
		shootTime = System.currentTimeMillis();
		Bullet bullet;
		if (this.canShootBombs) {	
			float w = getRectangle().getWidth()/ Constants.BULLET_SIZE_DIVIDER *6;
			float h = getRectangle().getHeight()/ Constants.BULLET_SIZE_DIVIDER *6;
			float x = getRectangle().getX() +(getRectangle().getWidth()/2)-w/2;
			float y = getRectangle().getY() +(getRectangle().getHeight()/2)-h/2;
			bullet = Create.bomb(x, y, w, h);
			Gdx.app.log("AI","Shoot Bomb!");
		}
		else {
			float x = getRectangle().getX()+(getRectangle().getWidth() /2);
			float y = getRectangle().getY()+(getRectangle().getHeight() /2);
			float w = getRectangle().getWidth() / Constants.BULLET_SIZE_DIVIDER;
			float h = getRectangle().getHeight() / Constants.BULLET_SIZE_DIVIDER;
			bullet = Create.bullet(x, y, w, h);
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
		if ((bulletY != 0) && ((getRectangle().getY()+getRectangle().getHeight()/2) < bulletY)) {
			if (getRectangle().getY() < 0) {
				this.yVel = Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = -Constants.ENEMY_SPEED;
				}
			}
		}
		else {
			if (getRectangle().getY() > myApp.getScreenHeight() - getRectangle().getHeight()) {
				yVel = -Constants.ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = Constants.ENEMY_SPEED;
				}
			}

		}
	}	
	
	public void moveForward() {
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

		if (getRectangle().getY() < 0) {
			this.yVel = Constants.ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
		if (getRectangle().getY() > myApp.getScreenHeight() - getRectangle().getHeight()) {
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
					if 	(Math.abs(this.getRectangle().getX()-playerBullets.get(bulletLoop).getRectangle().getX())<100 &&
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
					float bulletTop = playerBullets.get(bulletLoop).getRectangle().getY() + playerBullets.get(bulletLoop).getRectangle().getHeight();
					float bulletBottom = playerBullets.get(bulletLoop).getRectangle().getY();
					float top = this.getRectangle().getY() + this.getRectangle().getHeight();
					float bottom = this.getRectangle().getY();

					float bulletRight = playerBullets.get(bulletLoop).getRectangle().getX() + playerBullets.get(bulletLoop).getRectangle().getWidth();
					float left = this.getRectangle().getX() + this.getRectangle().getWidth();

					if 	(bulletTop > bottom && bulletBottom < top && bulletRight < left) {
						bulletY = playerBullets.get(bulletLoop).getRectangle().getY();
						return true;
					}
				}		
			}
		}
		return false;
	}	
	
	public boolean checkPlayerInLineOfSight(Player player) {
		float playerTop = player.getRectangle().getY() + player.getRectangle().getHeight();
		float playerBottom = player.getRectangle().getY();
		float top = this.getRectangle().getY() + this.getRectangle().getHeight();
		float bottom = this.getRectangle().getY();

		float playerRight = player.getRectangle().getX() + player.getRectangle().getWidth();
		float left = this.getRectangle().getX() + this.getRectangle().getWidth();

		return playerTop > bottom && playerBottom < top && playerRight < left;
	}
	
	private boolean checkShootDelay() {
		return System.currentTimeMillis() - shootTime > Constants.SHOOT_DELAY;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
