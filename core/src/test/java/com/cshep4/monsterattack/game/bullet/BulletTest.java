package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.core.CharacterType.ENEMY;
import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static junit.framework.TestCase.assertEquals;
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
public class BulletTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

    @Mock
    private Application app;

    @Mock
    private Graphics graphics;

    @Mock
    private Animation animationWrapper;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        mockStatic(Sound.class);

        Gdx.app = app;
        doNothing().when(app).log(any(String.class), any(String.class));

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);

        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void create_bulletCreatedForPlayer() throws Exception {
        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        assertThat(bullet, instanceOf(Bullet.class));
        assertThat(bullet.getX(), is(X_POS));
        assertThat(bullet.getY(), is(Y_POS));
        assertThat(bullet.getWidth(), is(WIDTH));
        assertThat(bullet.getHeight(), is(HEIGHT));
        assertThat(bullet.getXVel(), is(BULLET_SPEED));
    }

    @Test
    public void create_bulletCreatedForEnemy() throws Exception {
        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);

        assertThat(bullet, instanceOf(Bullet.class));
        assertThat(bullet.getX(), is(X_POS));
        assertThat(bullet.getY(), is(Y_POS));
        assertThat(bullet.getWidth(), is(WIDTH));
        assertThat(bullet.getHeight(), is(HEIGHT));
        assertEquals(-BULLET_SPEED, bullet.getXVel());
    }

    @Test
    public void update_runningEnemyLosesLifeWhenCollidedAndNotShielding() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        Standard standard = Standard.create(expectedBulletX, Y_POS, 4);

        List<RunningEnemy> runningEnemies = Collections.singletonList(standard);
        List<ProducerEnemy> producerEnemyList = Collections.emptyList();

        int expectedHealth = standard.getHealth() - 1;
        int expectedShieldHealth = standard.getShieldHealth();

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(standard.getHealth(), is(expectedHealth));
        assertThat(standard.getShieldHealth(), is(expectedShieldHealth));
        assertThat(result, is(true));
    }

    @Test
    public void update_runningEnemyDoesntLoseLifeButLosesShieldHealthWhenCollidedAndShielding() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        Standard standard = Standard.create(expectedBulletX, Y_POS, 4);
        standard.setShielding(true);

        List<RunningEnemy> runningEnemies = Collections.singletonList(standard);
        List<ProducerEnemy> producerEnemyList = Collections.emptyList();

        int expectedHealth = standard.getHealth();
        int expectedShieldHealth = standard.getShieldHealth() - 1;

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(standard.getHealth(), is(expectedHealth));
        assertThat(standard.getShieldHealth(), is(expectedShieldHealth));
        assertThat(result, is(true));
    }

    @Test
    public void update_enemyProducerLosesLifeWhenCollided() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        StandardProducer standardProducer = StandardProducer.create(expectedBulletX, Y_POS);

        List<RunningEnemy> runningEnemies = Collections.emptyList();
        List<ProducerEnemy> producerEnemyList = Collections.singletonList(standardProducer);

        int expectedHealth = standardProducer.getHealth() - 1;

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(standardProducer.getHealth(), is(expectedHealth));
        assertThat(result, is(true));
    }

    @Test
    public void update_enemiesDontLoseLifeWhenNotColliding() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        Standard standard = Standard.create(1000000, 100000, 1);
        StandardProducer standardProducer = StandardProducer.create(1000000, 100000);

        List<RunningEnemy> runningEnemies = Collections.singletonList(standard);
        List<ProducerEnemy> producerEnemyList = Collections.singletonList(standardProducer);

        int expectedRunningHealth = standard.getHealth();
        int expectedProducerHealth = standardProducer.getHealth();

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(standard.getHealth(), is(expectedRunningHealth));
        assertThat(standardProducer.getHealth(), is(expectedProducerHealth));
        assertThat(result, is(false));
    }

    @Test
    public void update_playerLosesLifeWhenCollided() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(expectedBulletX, Y_POS);

        int expectedHealth = player.getHealth() - 1;

        boolean result = bullet.update(player);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(player.getHealth(), is(expectedHealth));
        assertThat(result, is(true));
    }

    @Test
    public void update_playerDoenstLoseLifeWhenNotCollided() throws Exception {
        //bullet will update and move to this x position
        float expectedBulletX = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(10000000, 10000000);

        int expectedHealth = player.getHealth();

        boolean result = bullet.update(player);

        assertThat(bullet.getX(), is(expectedBulletX));
        assertThat(player.getHealth(), is(expectedHealth));
        assertThat(result, is(false));
    }

    @Test
    public void collisionSound_playsCorrectSound() throws Exception {
        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        bullet.collisionSound();

        verifyStatic(Sound.class);
        Sound.playEnemyHit();
    }

}