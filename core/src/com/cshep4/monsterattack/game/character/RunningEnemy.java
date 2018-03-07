package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.ai.RunningAI;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;

import java.util.List;
import java.util.function.Predicate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.SHOOT_DELAY;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class RunningEnemy extends Mutant implements RunningAI {
    protected boolean canShoot;
    protected boolean canDodge;
    protected boolean canShield;
    protected boolean canShootBombs;
    protected boolean shielding;
    protected boolean dodging;
    private float bulletY;
    private long shootTime = System.currentTimeMillis();
    protected int shieldHealth;

    protected RunningEnemy(Rectangle rectangle, String texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
        xVel = -ENEMY_SPEED;
    }

    protected Bullet shoot(){
        // create and return a bullet
        shootTime = System.currentTimeMillis();
        if (canShootBombs) {
            return shootBomb();
        } else {
            return shootBullet();
        }
    }

    private Bullet shootBullet() {
        Gdx.app.log(getLogName(),"Shoot Bullet");
        float w = getWidth() / BULLET_WIDTH_DIVIDER;
        float h = getHeight() / BULLET_HEIGHT_DIVIDER;
        float x = getX()+(getWidth() /2);
        float y = getY()+(getHeight() /2);
        return Bullet.create(ENEMY, x, y, w, h);
    }

    private Bomb shootBomb() {
        Gdx.app.log(getLogName(),"Shoot Bomb!");
        float w = (getWidth() / BOMB_SIZE_DIVIDER) * 4;
        float h = (getHeight() / BOMB_SIZE_DIVIDER) * 4;
        float x = getX() +(getWidth()/2)-w/2;
        float y = getY() +(getHeight()/2)-h/2;
        return Bomb.create(ENEMY, x, y, w, h);
    }

    protected void shield() {
        shieldAnimation();
        Gdx.app.log(getLogName(),"Shield");
        xVel = 0;
        yVel = 0;
        shielding = true;
    }

    protected void dodge() {
        // if already dodging then continue doing so
        if (dodging) {
            return;
        }

        Gdx.app.log(getLogName(),"Dodge");
        dodging = true;

        if (isEnemyRetreatingOffEndOfScreen()) {
            changeAnimation(ENEMY_SPEED);
            xVel = ENEMY_SPEED;
        }

        if (isBulletLowToEnemy()) {
            enemyRetreatUpwards();
        } else {
            enemyRetreatDownwards();
        }
    }

    private void enemyRetreatUpwards() {
        if (isEnemyOffBottomOfScreen()) {
            yVel = ENEMY_SPEED/2;
        } else if (yVel == 0) {
            yVel = -ENEMY_SPEED/2;
        }
    }

    private void enemyRetreatDownwards() {
        if (isEnemyOffTopOfScreen()) {
            yVel = -ENEMY_SPEED/2;
        } else if (yVel == 0) {
            yVel = ENEMY_SPEED/2;
        }
    }

    public void decisionTree(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets) {
        if (isAbleToShield() && isBulletClose(playerBullets)) {
            shield();
        }
        else if (canDodge && isEnemyInLineOfBullet(playerBullets)) {
            dodge();
            keepEnemyOnScreen();
        }
        else if (canShoot && isPlayerInLineOfSight(player) && checkShootDelay()) {
            enemyBullets.add(shoot());
            moveForward();
        } else {
            moveForward();
        }
        if (canStopDodging(playerBullets)) {
            dodging = false;
        }
        if (canStopShielding(playerBullets)) {
            shielding = false;
        }


        update();
    }

    private void keepEnemyOnScreen() {
        if (isEnemyOffBottomOfScreen() && yVel < 0) {
            yVel = ENEMY_SPEED/2;
        }

        if (isEnemyOffTopOfScreen() && yVel > 0) {
            yVel = -ENEMY_SPEED/2;
        }
    }

    protected boolean canStopDodging(List<Bullet> playerBullets) {
        return dodging && !isEnemyInLineOfBullet(playerBullets);
    }

    protected boolean canStopShielding(List<Bullet> playerBullets) {
        return shielding && !isBulletClose(playerBullets);
    }

    public boolean isBulletClose(List<Bullet> playerBullets) {
        if (playerBullets.isEmpty()) {
            return false;
        }

        Predicate<Bullet> inLineAndClose = (this::isBulletInLineAndClose);

        return playerBullets.stream().anyMatch(inLineAndClose);
    }

    public boolean isEnemyInLineOfBullet(List<Bullet> playerBullets) {
        if (playerBullets.isEmpty()) {
            return false;
        }

        Predicate<Bullet> inLine = (this::isBulletInLine);

        return playerBullets.stream().anyMatch(inLine);
    }

    private boolean isBulletInLineAndClose(Bullet bullet) {
        return isBulletClose(bullet) && isBulletInLine(bullet);
    }

    private boolean isBulletClose(Bullet bullet) {
        return Math.abs(getX()-bullet.getX())<100;
    }

    private boolean isBulletInLine(Bullet bullet) {
        float bulletTop = bullet.getY() + bullet.getHeight();
        float bulletBottom = bullet.getY();
        float top = getY() + getHeight();
        float bottom = getY();

        float bulletRight = bullet.getX() + bullet.getWidth();
        float left = getX() + getWidth();

        if 	(bulletTop > bottom && bulletBottom < top && bulletRight < left) {
            bulletY = bullet.getY();
            return true;
        }
        return false;
    }

    public boolean isPlayerInLineOfSight(Player player) {
        float playerTop = player.getY() + player.getHeight();
        float playerBottom = player.getY();
        float top = getY() + getHeight();
        float bottom = getY();

        float playerRight = player.getX() + player.getWidth();
        float left = getX() + getWidth();

        return playerTop > bottom && playerBottom < top && playerRight < left;
    }

    protected boolean checkShootDelay() {
        return System.currentTimeMillis() - shootTime > SHOOT_DELAY;
    }

    public void update(Player player, List<Bullet> playerBullets, List<Bullet> enemyBullets){
        decisionTree(player, playerBullets, enemyBullets);
        checkPlayerHasBeenKilled(player);
    }

    protected boolean isAbleToShield() {
        return canShield && shieldHealth > 0;
    }

    private boolean isBulletLowToEnemy() {
        return getMidY() < bulletY;
    }

    private boolean isEnemyRetreatingOffEndOfScreen() {
        return xVel != ENEMY_SPEED && getX() + getWidth() < getScreenXMax();
    }

    public abstract void changeAnimation(float newXVel);

    public abstract void shieldAnimation();

    private boolean isEnemyOffBottomOfScreen() {
        return getY() < 0;
    }

    private boolean isEnemyOffTopOfScreen() {
        return getY() + getHeight() > getScreenYMax();
    }

    @Override
    public void moveForward() {
        Gdx.app.log(getLogName(),"Move Forward");
        xVel = -ENEMY_SPEED;
        changeAnimation(xVel);

        if (isEnemyOffBottomOfScreen()) {
            yVel = ENEMY_SPEED/2;
        } else if (isEnemyOffTopOfScreen()) {
            yVel = -ENEMY_SPEED/2;
        }
        else {
            yVel = 0;
        }
    }

    @Override
    public void checkPlayerHasBeenKilled(Player player) {
        //check if enemy gets to edge of screen, game over
        if (getX() <= 0) {
            Gdx.app.log("Death", "LET THROUGH! Level: " + level);
            player.loseLifeRegardlessOfShield();
            kill();
        }
    }

    protected String getLogName() {
        return getClass().getSimpleName() + getLevel();
    }
}
