package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.core.SoundWrapper;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.utils.EnemyUtils;

import java.util.List;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.EXPLOSION;
import static com.cshep4.monsterattack.game.core.SoundWrapper.playMutateBomb;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getBomberSprite;
import static com.cshep4.monsterattack.game.utils.Utils.moveCharacterTowardsPoint;

@EqualsAndHashCode(callSuper=true)
public class Bomber extends RunningEnemy {
	private static final String RUNNING_AI = "RunningAI";

	private long explosionTime = 0;
	private static final int EXPLOSION_DELAY = 500;
		
	private Bomber(Rectangle rectangle, Texture texture, int frameCols, int frameRows, int level) {
		super(rectangle, texture, frameCols, frameRows);
		this.level = level;
		EnemyUtils.setAbility(this);
		xVel = ENEMY_SPEED;
		yVel = ENEMY_SPEED;
	}

	public static Bomber create(float x, float y, int level) {
		Rectangle rectangle = new Rectangle().setPosition(x,y);
		int frameCols = 7;
		int frameRows = 1;

		String sprite = getBomberSprite(level);

		Texture texture = TextureFactory.create(sprite);

		return new Bomber(rectangle, texture, frameCols, frameRows, level);
	}


	public void update(Player player){
		float playerX = player.getX();
		float playerY = player.getY();
		moveCharacterTowardsPoint(this, playerX, playerY);

		if (isValidMutation()) {
			mutate();
		}
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutate", level + "->" + (level +1));
		playMutateBomb();
		level += 1;
		EnemyUtils.setAbility(this);
		updateMutateTime();
		changeAnimation(xVel);
	}

	@Override
	public void shieldAnimation() {
		// Shield not implemented for Bombers yet
	}

	@Override
	public void decisionTree(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets) {
		if (hasExplosionOccurred()) {
			killSelfIfExplosionFinished();
		} else {
			decisionTreeProcessing(player, playerBullets, enemyBullets);
		}
	}

	private void decisionTreeProcessing(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets) {
		if (isAbleToShield() && isBulletClose(playerBullets)) {
			shield();
		}
		else if (canDodge && isEnemyInLineOfBullet(playerBullets)) {
			dodge();
		}
		else if (canShoot && isPlayerInLineOfSight(player) && checkShootDelay()) {
			enemyBullets.add(shoot());
		}

		if (canStopDodging(playerBullets)) {
			dodging = false;
		}
		if (canStopShielding(playerBullets)) {
			shielding = false;
		}

		// Bomber specific - explode
		update(player);
		if (isPlayerInExplosionRange(player)) {
			Gdx.app.log(RUNNING_AI, "Explode");
			explode(player);
			explosionTime = System.currentTimeMillis();
			xVel = 0;
			yVel = 0;
		}
	}

	private void killSelfIfExplosionFinished() {
		// delay killing instance so explosion sprite will be drawn
		if (hasExplosionFinished()) {
			kill();
		}
	}

	private boolean hasExplosionOccurred() {
		return explosionTime != 0;
	}

	private boolean hasExplosionFinished() {
		return System.currentTimeMillis() - explosionTime > EXPLOSION_DELAY;
	}

	private void explode(Player player) {
		SoundWrapper.playExplode();
		setX(getX() - getWidth()/2);
		setY(getY() - getHeight()/2);
		setWidth((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		setHeight((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		changeAnimation(TextureFactory.create(EXPLOSION),1, 1);
		Gdx.app.log("Death", "BOMBED!");
		player.kill();
	}

	private boolean isPlayerInExplosionRange(Player player) {
		Rectangle explosionRange = new Rectangle();

		explosionRange.setX(getX() - getWidth()/2);
		explosionRange.setY(getY() - getHeight()/2);
		explosionRange.setWidth((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		explosionRange.setHeight((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);

		return explosionRange.overlaps(player.getRectangle());
	}

	@Override
	public void changeAnimation(float newXVel) {
		String textureFile = getBomberSprite(level);

		changeAnimation(TextureFactory.create(textureFile), 7, 1);
	}
}
