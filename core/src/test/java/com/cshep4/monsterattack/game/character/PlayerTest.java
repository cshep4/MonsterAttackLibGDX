package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.utils.Utils;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.constants.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_NUMBER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerIdleSprite;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class, Utils.class})
public class PlayerTest {
    private static final float X_START_POS = 10;
    private static final float Y_START_POS = 10;
    private static final float X_END_POS = 5;
    private static final float Y_END_POS = 5;
    private static final float SPEED = 10;
    private static final int HEALTH = 5;
    private static final int SHIELD_HEALTH = 10;
    private static final int START_SHEILD_HEALTH = 0;
    private static final int BOMB_NUMBER = 0;
    private static final int ZERO = 0;
    private static final float ZERO_VEL = 0;
    private static final int HIGHER_THAN_SCREEN_DIMS = 500;
    private static final int LOWER_THAN_SCREEN_DIMS = -100;
    private static final float SCREEN_DIMS = 450f;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    private Player player;

    @Before
    public void init() {
        mockStatic(Sound.class);
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        mockStatic(Utils.class);
        when(Utils.getScreenXMax()).thenReturn(SCREEN_DIMS);
        when(Utils.getScreenYMax()).thenReturn(SCREEN_DIMS);

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));

        player = Player.create(X_START_POS, Y_START_POS);
    }

    @Test
    public void create() {
        Player player = Player.create(X_START_POS, Y_START_POS);

        assertThat(player, IsInstanceOf.instanceOf(Player.class));
        assertThat(player.getX(), is(X_START_POS));
        assertThat(player.getY(), is(Y_START_POS));
        assertThat(player.getHealth(), is(HEALTH));
        assertThat(player.getRemainingShieldTime(), is(START_SHEILD_HEALTH));
        assertThat(player.getNumberOfBullets(), is(BULLET_NUMBER));
        assertThat(player.getNumberOfBombs(), is(BOMB_NUMBER));
    }

    @Test
    public void update_stopsPlayerLeavingTopOrRightOfScreen() {
        player.setDestinationX(HIGHER_THAN_SCREEN_DIMS+100)
                .setDestinationY(HIGHER_THAN_SCREEN_DIMS+100)
                .setXVel(SPEED)
                .setYVel(SPEED)
                .setX(HIGHER_THAN_SCREEN_DIMS)
                .setY(HIGHER_THAN_SCREEN_DIMS);

        player.update();

        assertThat(player.getXVel(), is(ZERO_VEL));
        assertThat(player.getYVel(), is(ZERO_VEL));
    }

    @Test
    public void update_stopsPlayerLeavingBottomOrLeftOfScreen() {
        player.setDestinationX(LOWER_THAN_SCREEN_DIMS-100)
                .setDestinationY(LOWER_THAN_SCREEN_DIMS-100)
                .setXVel(-SPEED)
                .setYVel(-SPEED)
                .setX(LOWER_THAN_SCREEN_DIMS)
                .setY(LOWER_THAN_SCREEN_DIMS);

        player.update();

        assertThat(player.getXVel(), is(ZERO_VEL));
        assertThat(player.getYVel(), is(ZERO_VEL));
    }

    @Test
    public void update_shieldTimeDecrementedIfSecondHasPassedAndShieldSpriteStaysOnWhenShieldContinues() {
        player.setRemainingShieldTime(SHIELD_HEALTH);
        player.setTimeSeconds(100);

        int expectedShieldHealth = SHIELD_HEALTH - 1;

        player.update();

        assertThat(player.getRemainingShieldTime(), is(expectedShieldHealth));

        // already invoked once when player first created
        verifyStatic(AnimationFactory.class, times(1));
        AnimationFactory.createAnimation(2, 1, CHARACTER_IDLE);
    }

    @Test
    public void update_shieldTimeDecrementedIfSecondHasPassedAndShieldSpriteStaysStopsWhenShieldExpires() {
        player.setRemainingShieldTime(1);
        player.setTimeSeconds(100);

        player.update();

        assertThat(player.getRemainingShieldTime(), is(0));

        // already invoked once when player first created
        verifyStatic(AnimationFactory.class, times(2));
        AnimationFactory.createAnimation(2, 1, CHARACTER_IDLE);
    }

    @Test
    public void update_shieldTimeNotDecrementedIfSecondHasNotPassed() {
        player.setRemainingShieldTime(SHIELD_HEALTH);

        player.update();

        assertThat(player.getRemainingShieldTime(), is(SHIELD_HEALTH));
    }

    @Test
    public void loseLife_lifeLostWhenNoShield() {
        player.setRemainingShieldTime(ZERO).setHealth(HEALTH);

        player.loseLife();

        int expectedHealth = HEALTH - 1;

        assertThat(player.getHealth(), is(expectedHealth));
    }

    @Test
    public void loseLife_lifeNotLostWhenShieldOn() {
        player.setRemainingShieldTime(SHIELD_HEALTH).setHealth(HEALTH);

        player.loseLife();

        assertThat(player.getHealth(), is(HEALTH));
    }

    @Test
    public void loseLifeRegardlessOfShield_lifeLostEvenThoughShieldIsOn() {
        player.setRemainingShieldTime(SHIELD_HEALTH).setHealth(HEALTH);

        player.loseLifeRegardlessOfShield();

        int expectedHealth = HEALTH - 1;

        assertThat(player.getHealth(), is(expectedHealth));
    }

    @Test
    public void update_movesTowardsPoint() {
        final float startDifferenceX = player.getX() - X_END_POS;
        final float startDifferenceY = player.getRectangle().getY() - Y_END_POS;

        player.setDestinationX(X_END_POS);
        player.setDestinationY(Y_END_POS);

        player.update();

        final float endDifferenceX = player.getRectangle().getX() - X_END_POS;
        final float endDifferenceY = player.getRectangle().getY() - Y_END_POS;

        assertThat(startDifferenceX, greaterThan(endDifferenceX));
        assertThat(startDifferenceY, greaterThan(endDifferenceY));
    }

    @Test
    public void stand_velocitySetToZeroAndSpriteChanged() {
        player.stand();

        verifyStatic(AnimationFactory.class, times(2));
        AnimationFactory.createAnimation(2, 1, getPlayerIdleSprite(ZERO));

        assertThat(player.getXVel(), is(0f));
        assertThat(player.getYVel(), is(0f));
    }

    @Test
    public void shoot_createsInstanceOfBulletAndDecrementsNumberOfBulletsWhenPlayerHasBullets() {
        player.setNumberOfBullets(5);
        player.setNumberOfBombs(0);

        int expectedBullets = player.getNumberOfBullets() - 1;
        int expectedBombs = player.getNumberOfBombs();

        Bullet bullet = player.shoot();

        float expectedX = player.getMidX();
        float expectedY = player.getMidY();
        float expectedWidth = player.getWidth() / BULLET_WIDTH_DIVIDER;
        float expectedHeight = player.getHeight() / BULLET_HEIGHT_DIVIDER;

        assertThat(bullet, instanceOf(Bullet.class));
        assertThat(bullet.getX(), is(expectedX));
        assertThat(bullet.getY(), is(expectedY));
        assertThat(bullet.getWidth(), is(expectedWidth));
        assertThat(bullet.getHeight(), is(expectedHeight));
        assertThat(player.getNumberOfBullets(), is(expectedBullets));
        assertThat(player.getNumberOfBombs(), is(expectedBombs));
    }

    @Test
    public void shoot_createsInstanceOfBombAndDecrementsNumberOfBombsWhenPlayerHasBombs() {
        player.setNumberOfBullets(5);
        player.setNumberOfBombs(5);

        int expectedBullets = player.getNumberOfBullets();
        int expectedBombs = player.getNumberOfBombs() - 1;

        Bullet bomb = player.shoot();

        float expectedX = player.getMidX();
        float expectedY = player.getMidY();
        float expectedWidth = (player.getWidth() / BOMB_SIZE_DIVIDER) * 4;
        float expectedHeight = (player.getHeight() / BOMB_SIZE_DIVIDER) * 4;

        assertThat(bomb, instanceOf(Bomb.class));
        assertThat(bomb.getX(), is(expectedX));
        assertThat(bomb.getY(), is(expectedY));
        assertThat(bomb.getWidth(), is(expectedWidth));
        assertThat(bomb.getHeight(), is(expectedHeight));
        assertThat(player.getNumberOfBullets(), is(expectedBullets));
        assertThat(player.getNumberOfBombs(), is(expectedBombs));
    }

    @Test
    public void shoot_returnsNullWhenPlayerHasNoBulletsOrBombs() {
        player.setNumberOfBullets(0);
        player.setNumberOfBombs(0);

        Bullet bomb = player.shoot();

        assertThat(bomb, is(nullValue()));
    }

    @Test
    public void kill_destroyShieldIfPlayerHasOne() {
        player.setRemainingShieldTime(SHIELD_HEALTH).setHealth(HEALTH);

        player.kill();

        assertThat(player.getHealth(), is(HEALTH));
        assertThat(player.getRemainingShieldTime(), is(ZERO));
    }

    @Test
    public void kill_killsPlayerWhenThereIsNoShield() {
        player.setRemainingShieldTime(ZERO).setHealth(HEALTH);

        player.kill();

        assertThat(player.getHealth(), is(ZERO));
    }
}