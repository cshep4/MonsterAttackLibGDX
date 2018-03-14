package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.utils.EnemyUtils;
import com.cshep4.monsterattack.game.wrapper.Sound;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.EXPLOSION;
import static com.cshep4.monsterattack.game.constants.Constants.EXPLOSION_DELAY;
import static com.cshep4.monsterattack.game.utils.MovementUtils.moveCharacterTowardsPoint;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getBomberSprite;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;
import static com.cshep4.monsterattack.game.wrapper.Sound.playMutateBomb;

@EqualsAndHashCode(callSuper=true)
public class Bomber extends RunningEnemy {
	@Getter @Setter
	private long explosionTime = 0;
		
	private Bomber(Rectangle rectangle, String texture, int frameCols, int frameRows, int level) {
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

		return new Bomber(rectangle, getBomberSprite(level), frameCols, frameRows, level);
	}


	public void update(Player player){
		moveCharacterTowardsPoint(this, player.getX(), player.getY());

		if (isValidMutation()) {
			mutate();
		}
	}

	@Override
	public void mutate() {
		Gdx.app.log("Mutate!", level + "->" + (level +1));
		playMutateBomb();
		level += 1;
		EnemyUtils.setAbility(this);
		updateMutateTime();
		changeAnimation(xVel);
	}

	@Override
	public void shieldAnimation() {
		throw new UnsupportedOperationException();
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
		super.decisionTree(player, playerBullets, enemyBullets);

		// Bomber specific - explode
		update(player);
		if (isPlayerInExplosionRange(player)) {
			Gdx.app.log(getLogName(), "EXPLODE!");
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
		Sound.playExplode();
		setX(getX() - getWidth()/2);
		setY(getY() - getHeight()/2);
		setWidth((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		setHeight((getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2);
		changeAnimation(EXPLOSION,1, 1);
		Gdx.app.log("Death!", "BOMBED!");
		player.kill();
	}

	private boolean isPlayerInExplosionRange(Player player) {
		float x = getX() - getWidth()/2;
		float y = getY() - getHeight()/2;
		float size = (getScreenXMax() / CHARACTER_WIDTH_DIVIDER) * 2;

		Rectangle explosionRange = new Rectangle().setPosition(x,y).setSize(size);

		return hasCollided(explosionRange, player);
	}

	@Override
	public void moveForward() {
		//does nothing as movement is controlled differently for bombers
	}

	@Override
	public void update(){
		//does nothing as movement is controlled differently for bombers
	}

	@Override
	public void changeAnimation(float newXVel) {
		changeAnimation(getBomberSprite(level), 7, 1);
	}
}
