package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.Constants.SHOOT_DELAY;

public abstract class Enemy extends Character implements AI {
	protected boolean canShoot;
	protected boolean canDodge;
	protected boolean canShield;
	protected boolean canShootBombs;
	protected boolean sheilded;
	private float bulletY;
	private long shootTime = System.currentTimeMillis();
	protected int type;
	protected int shieldHealth;
	
	public Enemy(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
		this.xVel = -ENEMY_SPEED;
	}
	
	public Bullet shoot(){
		// create and return a bullet
		shootTime = System.currentTimeMillis();
		Bullet bullet;
		if (this.canShootBombs) {	
			float w = (getRectangle().getWidth() / BOMB_SIZE_DIVIDER) * 4;
			float h = (getRectangle().getHeight() / BOMB_SIZE_DIVIDER) * 4;
			float x = getRectangle().getX() +(getRectangle().getWidth()/2)-w/2;
			float y = getRectangle().getY() +(getRectangle().getHeight()/2)-h/2;
			bullet = Create.bomb(x, y, w, h);
			Gdx.app.log("AI","Shoot Bomb!");
		}
		else {
			float x = getRectangle().getX()+(getRectangle().getWidth() /2);
			float y = getRectangle().getY()+(getRectangle().getHeight() /2);
			float w = getRectangle().getWidth() / BULLET_WIDTH_DIVIDER;
			float h = getRectangle().getHeight() / BULLET_HEIGHT_DIVIDER;
			bullet = Create.enemyBullet(x, y, w, h);
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

		if (xVel != ENEMY_SPEED && getRectangle().getX() + getRectangle().getWidth() < getScreenXMax()) {
			changeAnimation(ENEMY_SPEED);
			xVel = ENEMY_SPEED;
		}
		//IF THE BULLET IS LOW MOVE UP, ELSE MOVE DOWN
		if ((bulletY != 0) && (getRectangle().getY()+(getRectangle().getHeight()/2) < bulletY)) {
			if (getRectangle().getY() < 0) {
				this.yVel = ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = -ENEMY_SPEED;
				}
			}
		}
		else {
			if (getRectangle().getY() + getRectangle().getHeight() > getScreenYMax()) {
				yVel = -ENEMY_SPEED;
			} else {
				if (yVel == 0) {
					yVel = ENEMY_SPEED;
				}
			}

		}
	}	
	
	public void moveForward() {
		Gdx.app.log("AI","Move Forward");
		if (this.xVel != -ENEMY_SPEED) {
			changeAnimation(-ENEMY_SPEED);
		}

		this.xVel = -ENEMY_SPEED;

		if (getRectangle().getY() < 0) {
			this.yVel = ENEMY_SPEED;
		} else {
			this.yVel = 0;
		}
		if (getRectangle().getY() + getRectangle().getHeight() > getScreenYMax()) {
			yVel = -ENEMY_SPEED;
		} else {
			if (this.yVel != ENEMY_SPEED) {
				this.yVel = 0;
			}
		}
	}
	
	public void setShielded(boolean shielded) {
		this.sheilded = shielded;
	}
	
	public void decisionTree(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets) {
		// check decision		
		//IS BULLET CLOSE? & CAN ENEMY SHIELD?
		if (canShield() && this.checkBulletClose(playerBullets)) {
			if(!this.sheilded) {
				shieldAnimation();
			}
			this.shield();
		}//IS ENEMY IN LINE OF BULLET? & CAN ENEMY DODGE?
		else if (checkEnemyInLineOfBullet(playerBullets) && this.canDodge) {
			this.sheilded = false;
			this.dodge();
		}//IS PLAYER IN LINE OF ENEMY SIGHT? & CAN ENEMY SHOOT?
		else if (checkPlayerInLineOfSight(player) && this.canShoot) {
			this.sheilded = false;
			if (checkShootDelay()) {
				enemyBullets.add(this.shoot());
			}
			this.moveForward();
		}//ELSE RUN FORWARD
		else {
			this.sheilded = false;
			this.moveForward();
		}
		this.update();
	}
	
	public boolean checkBulletClose(ArrayList<Bullet> playerBullets) {
		if (playerBullets.size() == 0) {
			return false;
		}

		Predicate<Bullet> inLineAndClose = (this::checkBulletInLineAndClose);

		return playerBullets.stream().anyMatch(inLineAndClose);
	}

	public boolean checkEnemyInLineOfBullet(ArrayList<Bullet> playerBullets) {
		if (playerBullets.size() == 0) {
			return false;
		}

		Predicate<Bullet> inLine = (this::checkBulletInLine);

		return playerBullets.stream().anyMatch(inLine);
	}

	private boolean checkBulletInLineAndClose(Bullet bullet) {
		return Math.abs(this.getRectangle().getX()-bullet.getRectangle().getX())<100 && checkBulletInLine(bullet);
	}

	private boolean checkBulletInLine(Bullet bullet) {
		float bulletTop = bullet.getRectangle().getY() + bullet.getRectangle().getHeight();
		float bulletBottom = bullet.getRectangle().getY();
		float top = this.getRectangle().getY() + this.getRectangle().getHeight();
		float bottom = this.getRectangle().getY();

		float bulletRight = bullet.getRectangle().getX() + bullet.getRectangle().getWidth();
		float left = this.getRectangle().getX() + this.getRectangle().getWidth();

		if 	(bulletTop > bottom && bulletBottom < top && bulletRight < left) {
			bulletY = bullet.getRectangle().getY();
			return true;
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
	
	protected boolean checkShootDelay() {
		return System.currentTimeMillis() - shootTime > SHOOT_DELAY;
	}

	public void update(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets){
		decisionTree(player, playerBullets, enemyBullets);

//		super.update();

		//check if player has collided, if so KILL!!!
		if (this.getRectangle().overlaps(player.getRectangle())) {
			Gdx.app.log("Death", "COLLIDED!");
			player.setHealth(player.getHealth()-100);
		}

		//check if enemy gets to edge of screen, game over
		if (this.getRectangle().getX() <= 0) {
			Gdx.app.log("Death", "LET THROUGH! Type: " + type);
			player.setHealth(player.getHealth()-100);
		}
	}

	public boolean canShield() {
		return this.canShield && shieldHealth > 0;
	}

	public abstract void changeAnimation(float newXVel);

	public abstract void shieldAnimation();

	public void setType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public boolean isCanDodge() {
		return canDodge;
	}

	public void setCanDodge(boolean canDodge) {
		this.canDodge = canDodge;
	}

	public boolean isCanShield() {
		return canShield;
	}

	public void setCanShield(boolean canShield) {
		this.canShield = canShield;
	}

	public boolean isCanShootBombs() {
		return canShootBombs;
	}

	public void setCanShootBombs(boolean canShootBombs) {
		this.canShootBombs = canShootBombs;
	}

	public boolean isSheilded() {
		return sheilded;
	}

	public void setSheilded(boolean sheilded) {
		this.sheilded = sheilded;
	}

	public float getBulletY() {
		return bulletY;
	}

	public void setBulletY(float bulletY) {
		this.bulletY = bulletY;
	}

	public long getShootTime() {
		return shootTime;
	}

	public void setShootTime(long shootTime) {
		this.shootTime = shootTime;
	}

	public int getShieldHealth() {
		return shieldHealth;
	}

	public void setShieldHealth(int shieldHealth) {
		this.shieldHealth = shieldHealth;
	}
}
