package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.Constants.EXPLOSION;
import static java.lang.Math.sqrt;

public class Bomber extends Enemy implements Mutatable {
	private long mutateTime = System.currentTimeMillis();
	private long explosionTime = 0;
	private static final int EXPLOSION_DELAY = 500;
		
	public Bomber(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int type) {
		super(rectangle, texture, frameCols, frameRows);
		this.type = type;
		setAbility();
		this.xVel = ENEMY_SPEED;
		this.yVel = ENEMY_SPEED;
	}


	public void update(Player player){
		//---------------------------------------------------------------------Make enemy travel towards player
		float dirX = player.getRectangle().getX() - this.getRectangle().getX();
		float dirY = player.getRectangle().getY() - this.getRectangle().getY();

		//normalise direction
		double hypotenuse = sqrt(dirX*dirX + dirY*dirY);
		dirX /= hypotenuse;
		dirY /= hypotenuse;


		this.getRectangle().setX(this.getRectangle().getX() + (dirX*(this.xVel*Gdx.graphics.getDeltaTime())));
		this.getRectangle().setY(this.getRectangle().getY() + (dirY*(this.yVel*Gdx.graphics.getDeltaTime())));
		//-----------------------------------------------------------------------------------------------------

		if (this.isValidMutation(this.type, mutateTime)) {
			this.mutate();
		}
	}

	private void setAbility() {
		if (type == 1) {
			canShoot = false;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 100;
			shieldHealth = 0;
		} else if (type == 2) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 100;
			shieldHealth = 0;
		} else if (type == 3) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = false;
			health = 200;
			shieldHealth = 0;
		} else if (type == 4) {
			canShoot = true;
			canDodge = false;
			canShield = false;
			canShootBombs = true;
			health = 200;
			shieldHealth = 0;
		}
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutation", type + "->" + (type+1));
		SoundWrapper.playMutateStandard();
		this.type += 1;
		changeAnimation(this.xVel);
		setAbility();
		mutateTime = System.currentTimeMillis();
	}

	@Override
	public void changeAnimation(float newXVel) {
		String textureFile;

		switch (type) {
			case 1:
				textureFile = B1_SPRITE_MOVE;
				break;
			case 2:
				textureFile = B2_SPRITE_MOVE;
				break;
			case 3:
				textureFile = B3_SPRITE_MOVE;
				break;
			case 4:
				textureFile = B4_SPRITE_MOVE;
				break;
			default:
				return;
		}

		changeAnimation(new Texture(textureFile), 7, 1);
	}

	@Override
	public void shieldAnimation() {}

	@Override
	public void decisionTree(Player player, ArrayList<Bullet> playerBullets, ArrayList<Bullet> enemyBullets) {
		if (explosionTime == 0) { // has explosion occurred
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
			}//ELSE CANCEL SHIELD
			else {
				this.sheilded = false;
			}
			//------------------------------BOMBER SPECIFIC
			this.update(player);
			if (this.checkPlayerClose(player)) {
				Gdx.app.log("AI", "Explode");
				this.explode(player);
				explosionTime = System.currentTimeMillis();
				this.xVel = 0;
				this.yVel = 0;
			}
			//---------------------------------------------
		} else { // delay killing instance so explosion sprite will be drawn
			if (System.currentTimeMillis() - explosionTime > EXPLOSION_DELAY) {
				this.setHealth(0);
			}
		}
	}

	private void explode(Player player) {
		SoundWrapper.playExplode();
		this.getRectangle().setX(this.getRectangle().getX() - this.getRectangle().getWidth()/2);
		this.getRectangle().setY(this.getRectangle().getY() - this.getRectangle().getHeight()/2);
		this.getRectangle().setWidth((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		this.getRectangle().setHeight((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		changeAnimation(new Texture(Gdx.files.internal(EXPLOSION)),1, 1);
		Gdx.app.log("Death", "BOMBED!");
		player.setHealth(0);
	}

	private boolean checkPlayerClose(Player player) {
		Rectangle explosionRange = new Rectangle();

		explosionRange.setX(this.getRectangle().getX() - this.getRectangle().getWidth()/2);
		explosionRange.setY(this.getRectangle().getY() - this.getRectangle().getHeight()/2);
		explosionRange.setWidth((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		explosionRange.setHeight((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);

		return explosionRange.overlaps(player.getRectangle());
	}
}
