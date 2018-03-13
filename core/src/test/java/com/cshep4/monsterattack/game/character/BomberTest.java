package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.bullet.Bullet;
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
import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.EXPLOSION_DELAY;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getBomberSprite;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class})
public class BomberTest {
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
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        assertThat(bomber, instanceOf(Bomber.class));
        assertThat(bomber.getX(), is(X_POS));
        assertThat(bomber.getY(), is(Y_POS));
        assertThat(bomber.getXVel(), is(ENEMY_SPEED));
        assertThat(bomber.getYVel(), is(ENEMY_SPEED));
        assertThat(bomber.getLevel(), is(LEVEL));
    }

    @Test
    public void update_bomberMovesTowardsPlayerAndMutationOccursWhenConditionsAreCorrect() throws Exception {
        Player player = Player.create(400, 600);
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        final float startDifferenceX = player.getX() - bomber.getRectangle().getX();
        final float startDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        // setup mutation conditions - 50 kills = difficulty 10, highest difficulty with mutation
        ScoreIndicator.resetKills();
        for (int i=0; i<50; i++) {
            ScoreIndicator.incrementKills();
        }
        Field f = Mutant.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(bomber, 100);
        f.setAccessible(false);

        bomber.update(player);

        final float endDifferenceX = player.getRectangle().getX() - bomber.getRectangle().getX();
        final float endDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        assertThat(startDifferenceX, greaterThan(endDifferenceX));
        assertThat(startDifferenceY, greaterThan(endDifferenceY));

        //check mutation has been triggered and working correctly
        assertThat(bomber.getLevel(), is(LEVEL + 1));
    }

    @Test
    public void update_bomberMovesTowardsPlayerAndMutationDoesNotOccurWhenConditionsAreNotCorrect() throws Exception {
        Player player = Player.create(400, 600);
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        final float startDifferenceX = player.getX() - bomber.getRectangle().getX();
        final float startDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        // remove mutation conditions, kills at 0 and mutateTime not set
        ScoreIndicator.resetKills();

        bomber.update(player);

        final float endDifferenceX = player.getRectangle().getX() - bomber.getRectangle().getX();
        final float endDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        assertThat(startDifferenceX, greaterThan(endDifferenceX));
        assertThat(startDifferenceY, greaterThan(endDifferenceY));

        //check mutation has been triggered and working correctly
        assertThat(bomber.getLevel(), is(LEVEL));
    }

    @Test
    public void mutate_levelOneToTwo() throws Exception {
        int level = 1;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertThat(bomber.getLevel(), is(2));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(false));
        assertThat(bomber.getHealth(), is(1));
    }

    @Test
    public void mutate_levelTwoToThree() throws Exception {
        int level = 2;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertThat(bomber.getLevel(), is(3));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(false));
        assertThat(bomber.getHealth(), is(2));
    }

    @Test
    public void mutate_levelThreeToFour() throws Exception {
        int level = 3;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertThat(bomber.getLevel(), is(4));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(true));
        assertThat(bomber.getHealth(), is(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shieldAnimation_causesException() {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);
        bomber.shieldAnimation();
    }

    @Test
    public void decisionTree_explosionOccurredButNotFinishedSoWait() throws Exception {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        Player player = Player.create(X_POS, Y_POS);
        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = Collections.emptyList();

        bomber.setExplosionTime(System.currentTimeMillis());

        bomber.decisionTree(player, playerBullets, enemyBullets);

        assertThat(bomber.getHealth(), not(0));
    }

    @Test
    public void decisionTree_explosionFinishedKillsSelf() throws Exception {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        Player player = Player.create(X_POS, Y_POS);
        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = Collections.emptyList();

        bomber.setExplosionTime(EXPLOSION_DELAY);

        bomber.decisionTree(player, playerBullets, enemyBullets);

        assertThat(bomber.getHealth(), is(0));
    }

    @Test
    public void decisionTree_explosionOccursWhenPlayerInRange() throws Exception {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        Player player = Player.create(X_POS, Y_POS);
        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = Collections.emptyList();

        bomber.decisionTree(player, playerBullets, enemyBullets);

        assertThat(player.getHealth(), is(0));
        assertThat(bomber.getExplosionTime(), not(0));
        assertThat(bomber.getXVel(), is(0f));
        assertThat(bomber.getYVel(), is(0f));
    }

    @Test
    public void decisionTree_explosionDoesNotOccurWhenPlayerIsNotInRange() throws Exception {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        Player player = Player.create(100000, 100000);
        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = Collections.emptyList();

        bomber.decisionTree(player, playerBullets, enemyBullets);

        assertThat(player.getHealth(), not(0));
        assertThat(bomber.getExplosionTime(), is(0L));
        assertThat(bomber.getXVel(), is(ENEMY_SPEED));
        assertThat(bomber.getYVel(), is(ENEMY_SPEED));
    }

    @Test
    public void changeAnimation_updatesAnimation() {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL);

        int newLevel = LEVEL + 1;
        bomber.setLevel(newLevel);

        bomber.changeAnimation(ENEMY_SPEED);

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(7, 1, getBomberSprite(newLevel));
    }
}