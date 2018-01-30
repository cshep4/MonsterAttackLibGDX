package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.core.AI;

import java.util.List;
import java.util.function.Predicate;

import lombok.Data;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.SHOOT_DELAY;

@Data
public abstract class Enemy extends Character implements AI {
	protected boolean canShoot;
	protected boolean canDodge;
	protected boolean canShield;
	protected boolean canShootBombs;
	protected boolean sheilded;
	private float bulletY;
	private long shootTime = System.currentTimeMillis();
	protected int level;
	protected int shieldHealth;
	
	protected Enemy(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
		super(rectangle, texture, frameCols, frameRows);
		xVel = -ENEMY_SPEED;
	}
	
	protected Bullet shoot(){
		// create and return a bullet
		shootTime = System.currentTimeMillis();
		Bullet bullet;
		if (canShootBombs) {
			float w = (getRectangle().getWidth() / BOMB_SIZE_DIVIDER) * 4;
			float h = (getRectangle().getHeight() / BOMB_SIZE_DIVIDER) * 4;
			float x = getRectangle().getX() +(getRectangle().getWidth()/2)-w/2;
			float y = getRectangle().getY() +(getRectangle().getHeight()/2)-h/2;
			bullet = Bomb.create(x, y, w, h);
			Gdx.app.log("AI","Shoot Bomb!");
		}
		else {
			float w = getRectangle().getWidth() / BULLET_WIDTH_DIVIDER;
			float h = getRectangle().getHeight() / BULLET_HEIGHT_DIVIDER;
			float x = getRectangle().getX()+(getRectangle().getWidth() /2);
			float y = getRectangle().getY()+(getRectangle().getHeight() /2);
			bullet = Bullet.create(this, x, y, w, h);
			Gdx.app.log("AI","Shoot Bullet");
		}
		return bullet;		
	}
	
	protected void shield() {
		shieldAnimation();
		Gdx.app.log("AI","Shield");
		xVel = 0;
		yVel = 0;
		sheilded = true;
	}

	protected void dodge() {
		Gdx.app.log("AI","Dodge");

		if (enemyRetreatingOffEndOfScreen()) {
			changeAnimation(ENEMY_SPEED);
			xVel = ENEMY_SPEED;
		}

		if (bulletLowToEnemy()) {
			enemyRetreatUpwards();
		} else {
			enemyRetreatDownwards();
		}
	}

	private void enemyRetreatUpwards() {
		if (enemyOffBottomOfScreen()) {
			yVel = ENEMY_SPEED;
		} else if (yVel == 0) {
			yVel = -ENEMY_SPEED;
		}
	}
	private void enemyRetreatDownwards() {
		if (enemyOffTopOfScreen()) {
			yVel = -ENEMY_SPEED;
		} else if (yVel == 0) {
			yVel = ENEMY_SPEED;
		}
	}

	protected void moveForward() {
		Gdx.app.log("AI","Move Forward");
		xVel = -ENEMY_SPEED;
		changeAnimation(xVel);

		if (enemyOffBottomOfScreen()) {
			yVel = ENEMY_SPEED;
		} else if (enemyOffTopOfScreen()) {
			yVel = -ENEMY_SPEED;
		}
		else {
			yVel = 0;
		}
	}
	
	public void decisionTree(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets) {
		sheilded = false;
		if (ableToShield() && checkBulletClose(playerBullets)) {
			shield();
		}
		else if (canDodge && checkEnemyInLineOfBullet(playerBullets)) {
			dodge();
		}
		else if (canShoot && checkPlayerInLineOfSight(player) && checkShootDelay()) {
			enemyBullets.add(shoot());
			moveForward();
		} else {
			moveForward();
		}
		update();
	}
	
	public boolean checkBulletClose(List<Bullet> playerBullets) {
		if (playerBullets.isEmpty()) {
			return false;
		}

		Predicate<Bullet> inLineAndClose = (this::checkBulletInLineAndClose);

		return playerBullets.stream().anyMatch(inLineAndClose);
	}

	public boolean checkEnemyInLineOfBullet(List<Bullet> playerBullets) {
		if (playerBullets.isEmpty()) {
			return false;
		}

		Predicate<Bullet> inLine = (this::checkBulletInLine);

		return playerBullets.stream().anyMatch(inLine);
	}

	private boolean checkBulletInLineAndClose(Bullet bullet) {
		return checkBulletClose(bullet) && checkBulletInLine(bullet);
	}

	private boolean checkBulletClose(Bullet bullet) {
		return Math.abs(getRectangle().getX()-bullet.getRectangle().getX())<100;
	}

	private boolean checkBulletInLine(Bullet bullet) {
		float bulletTop = bullet.getRectangle().getY() + bullet.getRectangle().getHeight();
		float bulletBottom = bullet.getRectangle().getY();
		float top = getRectangle().getY() + getRectangle().getHeight();
		float bottom = getRectangle().getY();

		float bulletRight = bullet.getRectangle().getX() + bullet.getRectangle().getWidth();
		float left = getRectangle().getX() + getRectangle().getWidth();

		if 	(bulletTop > bottom && bulletBottom < top && bulletRight < left) {
			bulletY = bullet.getRectangle().getY();
			return true;
		}
		return false;
	}
	
	public boolean checkPlayerInLineOfSight(Player player) {
		float playerTop = player.getRectangle().getY() + player.getRectangle().getHeight();
		float playerBottom = player.getRectangle().getY();
		float top = getRectangle().getY() + getRectangle().getHeight();
		float bottom = getRectangle().getY();

		float playerRight = player.getRectangle().getX() + player.getRectangle().getWidth();
		float left = getRectangle().getX() + getRectangle().getWidth();

		return playerTop > bottom && playerBottom < top && playerRight < left;
	}
	
	protected boolean checkShootDelay() {
		return System.currentTimeMillis() - shootTime > SHOOT_DELAY;
	}

	public void update(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets){
		decisionTree(player, playerBullets, enemyBullets);
		checkPlayerDeath(player);
	}

	protected void checkPlayerDeath(Player player) {
		//check if player has collided, if so KILL!!!
		if (getRectangle().overlaps(player.getRectangle())) {
			Gdx.app.log("Death", "COLLIDED!");
			player.setHealth(player.getHealth()-100);
		}

		//check if enemy gets to edge of screen, game over
		if (getRectangle().getX() <= 0) {
			Gdx.app.log("Death", "LET THROUGH! Level: " + level);
			player.setHealth(player.getHealth()-100);
		}
	}

	protected boolean ableToShield() {
		return canShield && shieldHealth > 0;
	}

	private boolean bulletLowToEnemy() {
		return getMidY() < bulletY;
	}

	private boolean enemyRetreatingOffEndOfScreen() {
		return xVel != ENEMY_SPEED && getRectangle().getX() + getRectangle().getWidth() < getScreenXMax();
	}

	private boolean enemyOffBottomOfScreen() {
		return getRectangle().getY() < 0;
	}

	private boolean enemyOffTopOfScreen() {
		return getRectangle().getY() + getRectangle().getHeight() > getScreenYMax();
	}

	public abstract void changeAnimation(float newXVel);

	public abstract void shieldAnimation();
}
