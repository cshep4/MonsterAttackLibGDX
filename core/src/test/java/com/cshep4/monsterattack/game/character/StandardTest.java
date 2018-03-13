package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_SHIELD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class})
public class StandardTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final int LEVEL = 1;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    @Before
    public void init() {
        mockStatic(Sound.class);
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
    }

    @Test
    public void create() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);

        assertThat(standard, instanceOf(Standard.class));
        assertThat(standard.getX(), is(X_POS));
        assertThat(standard.getY(), is(Y_POS));
        assertThat(standard.getLevel(), is(LEVEL));
    }

    @Test
    public void update_enemyRunsForwardAndMutatesIfValidMutation() throws Exception {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);

        // setup mutation conditions - 50 kills = difficulty 10, highest difficulty with mutation
        ScoreIndicator.resetKills();
        for (int i=0; i<50; i++) {
            ScoreIndicator.incrementKills();
        }
        Field f = Mutant.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(standard, 100);
        f.setAccessible(false);

        standard.update();

        final float expectedX = X_POS - ENEMY_SPEED;
        final int expectedLevel = LEVEL + 1;

        assertThat(standard.getX(), is(expectedX));
        assertThat(standard.getY(), is(Y_POS));

        //check mutation has been triggered and working correctly
        assertThat(standard.getLevel(), is(expectedLevel));
    }

    @Test
    public void shieldAnimation_changesEnemySpriteToShield() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);

        standard.shieldAnimation();

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(2, 1, S4_SPRITE_SHIELD);
    }

    @Test
    public void mutate_levelOneToTwo() throws Exception {
        int level = 1;
        Standard standard = Standard.create(0, 0, level);

        standard.mutate();

        assertThat(standard.getLevel(), is(2));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(false));
        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.isCanShootBombs(), is(false));
        assertThat(standard.getHealth(), is(1));
    }

    @Test
    public void mutate_levelTwoToThree() throws Exception {
        int level = 2;
        Standard standard = Standard.create(0, 0, level);

        standard.mutate();

        assertThat(standard.getLevel(), is(3));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.isCanShootBombs(), is(false));
        assertThat(standard.getHealth(), is(2));
    }

    @Test
    public void mutate_levelThreeToFour() throws Exception {
        int level = 3;
        Standard standard = Standard.create(0, 0, level);

        standard.mutate();

        assertThat(standard.getLevel(), is(4));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.isCanShield(), is(true));
        assertThat(standard.isCanShootBombs(), is(false));
        assertThat(standard.getShieldHealth(), is(2));
        assertThat(standard.getHealth(), is(1));
    }

    @Test
    public void checkPlayerHasBeenKilled_killSelfAndHurtPlayerIfCollided() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);
        Player player = Player.create(X_POS, Y_POS);

        int expectedPlayerHealth = player.getHealth() - 1;

        standard.checkPlayerHasBeenKilled(player);

        assertThat(standard.getHealth(), is(0));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void checkPlayerHasBeenKilled_doNothingIfNotCollided() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);
        Player player = Player.create(100000, 100000);

        int expectedEnemyHealth = standard.getHealth();
        int expectedPlayerHealth = player.getHealth();

        standard.checkPlayerHasBeenKilled(player);

        assertThat(standard.getHealth(), is(expectedEnemyHealth));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void changeAnimation_updatesAnimationWithLeftSpriteWhenFacingLeft() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);
        standard.setLevel(2);

        standard.changeAnimation(-ENEMY_SPEED);

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(2, 1, S2_SPRITE_MOVE_LEFT);
    }

    @Test
    public void changeAnimation_updatesAnimationWithRightSpriteWhenFacingRight() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL);
        standard.setLevel(2);

        standard.changeAnimation(ENEMY_SPEED);

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(2, 1, S2_SPRITE_MOVE_RIGHT);
    }
}