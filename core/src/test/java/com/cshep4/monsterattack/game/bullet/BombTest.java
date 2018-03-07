package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.utils.Utils;
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
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class, Utils.class})
public class BombTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        mockStatic(Sound.class);
        mockStatic(Utils.class);

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));

        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void create() throws Exception {
        GameObject bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        assertThat(bomb, instanceOf(Bomb.class));
        assertThat(bomb.getX(), is(X_POS));
        assertThat(bomb.getY(), is(Y_POS));
        assertThat(bomb.getWidth(), is(WIDTH));
        assertThat(bomb.getHeight(), is(HEIGHT));
    }

    @Test
    public void collisionSound() throws Exception {
        Bomb bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        bomb.collisionSound();

        verifyStatic(Sound.class);
        Sound.playExplode();
    }

    @Test
    public void update_enemyBombUpdatesPositionAndPlayerKilledWhenHitByBomb() throws Exception {
        Bomb bomb = Bomb.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(X_POS, Y_POS);

        when(Utils.hasCollided(bomb, player)).thenReturn(true);

        bomb.update(player);

        float expectedX = X_POS - BULLET_SPEED;

        assertThat(player.getHealth(), is(0));
        assertThat(bomb.getX(), is(expectedX));
        assertThat(bomb.getY(), is(Y_POS));
    }

    @Test
    public void update_enemyBombUpdatesPositionAndPlayerIsntKilledWhenNotHitByBomb() throws Exception {
        Bomb bomb = Bomb.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(X_POS*2, Y_POS*2);

        when(Utils.hasCollided(bomb, player)).thenReturn(false);

        bomb.update(player);

        float expectedX = X_POS - BULLET_SPEED;

        assertThat(player.getHealth(), not(0));
        assertThat(bomb.getX(), is(expectedX));
        assertThat(bomb.getY(), is(Y_POS));
    }

    @Test
    public void update_playerBombUpdatesPositionAndEnemiesKilledWhenHitByBomb() throws Exception {
        Bomb bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        Standard standard = Standard.create(X_POS, Y_POS, 1);
        BomberProducer bomberProducer = BomberProducer.create(X_POS, Y_POS);

        List<RunningEnemy> runningEnemies = Collections.singletonList(standard);
        List<ProducerEnemy> producerEnemies = Collections.singletonList(bomberProducer);

        when(Utils.hasCollided(bomb, standard)).thenReturn(true);
        when(Utils.hasCollided(bomb, bomberProducer)).thenReturn(true);

        boolean result = bomb.update(runningEnemies, producerEnemies);

        float expectedX = X_POS + BULLET_SPEED;

        assertThat(result, is(true));
        assertThat(runningEnemies.get(0).getHealth(), is(0));
        assertThat(producerEnemies.get(0).getHealth(), is(0));
        assertThat(bomb.getX(), is(expectedX));
        assertThat(bomb.getY(), is(Y_POS));
    }

    @Test
    public void update_playerBombUpdatesPositionAndEnemiesArentKilledWhenNotHitByBomb() throws Exception {
        Bomb bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        Bomber bomber = Bomber.create(X_POS*2, Y_POS*2, 1);
        StandardProducer standardProducer = StandardProducer.create(X_POS*2, Y_POS*2);

        List<RunningEnemy> runningEnemies = Collections.singletonList(bomber);
        List<ProducerEnemy> producerEnemies = Collections.singletonList(standardProducer);

        when(Utils.hasCollided(bomb, bomber)).thenReturn(false);
        when(Utils.hasCollided(bomb, standardProducer)).thenReturn(false);

        boolean result = bomb.update(runningEnemies, producerEnemies);

        float expectedX = X_POS + BULLET_SPEED;

        assertThat(result, is(false));
        assertThat(runningEnemies.get(0).getHealth(), not(0));
        assertThat(producerEnemies.get(0).getHealth(), not(0));
        assertThat(bomb.getX(), is(expectedX));
        assertThat(bomb.getY(), is(Y_POS));
    }
}