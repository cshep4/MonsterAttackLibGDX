package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_PRODUCE;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class})
public class ProducerEnemyTest {
    private static final float CHARACTER_WIDTH = getScreenXMax() / CHARACTER_WIDTH_DIVIDER;
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
    public void decisionTree_producerMovesForwardIfNotInPosition() {
        ProducerEnemy producerEnemy = StandardProducer.create(getScreenXMax(), Y_POS);

        producerEnemy.decisionTree(Collections.emptyList());

        float expectedX = getScreenXMax() - ENEMY_SPEED;

        assertThat(producerEnemy.getXVel(), is(-ENEMY_SPEED));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(expectedX));
        assertThat(producerEnemy.getY(), is(Y_POS));
    }

    @Test
    public void decisionTree_startToProduceEnemyIfDelayTimeHasPassedButWaitAndDisplaySprite() throws Exception {
        ProducerEnemy producerEnemy = StandardProducer.create(X_POS, Y_POS);
        List<RunningEnemy> runningEnemies = Collections.emptyList();

        Field f = ProducerEnemy.class.getDeclaredField("spawnTime");
        f.setAccessible(true);
        f.set(producerEnemy, 100);
        f.setAccessible(false);

        producerEnemy.decisionTree(runningEnemies);

        assertThat(producerEnemy.getXVel(), is(0f));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(X_POS));
        assertThat(producerEnemy.getY(), is(Y_POS));
        assertThat(producerEnemy.getProducingTime(), not(0));

        assertThat(runningEnemies.isEmpty(), is(true));

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(SP_PRODUCING_COLS, SP_PRODUCING_ROWS, SP_SPRITE_PRODUCE);
    }

    @Test
    public void decisionTree_finishProducingEnemyIfDelayTimesHaveBothPassedForStandard() throws Exception {
        ProducerEnemy producerEnemy = StandardProducer.create(X_POS, Y_POS);
        List<RunningEnemy> runningEnemies = new ArrayList<>();

        Field f = ProducerEnemy.class.getDeclaredField("spawnTime");
        f.setAccessible(true);
        f.set(producerEnemy, 100);
        f.setAccessible(false);

        f = ProducerEnemy.class.getDeclaredField("producingTime");
        f.setAccessible(true);
        f.set(producerEnemy, 100);
        f.setAccessible(false);

        producerEnemy.decisionTree(runningEnemies);

        assertThat(producerEnemy.getXVel(), is(0f));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(X_POS));
        assertThat(producerEnemy.getY(), is(Y_POS));
        assertThat(producerEnemy.getProducingTime(), is(0L));

        assertThat(runningEnemies.isEmpty(), is(false));
        RunningEnemy runningEnemy = runningEnemies.get(0);

        assertThat(runningEnemy, instanceOf(Standard.class));
        assertThat(runningEnemy.getX(), is(producerEnemy.getX() - CHARACTER_WIDTH));
        assertThat(runningEnemy.getY(), is(producerEnemy.getY()));

        //called once when first created so verify that its been called twice
        verifyStatic(AnimationFactory.class, times(2));
        AnimationFactory.createAnimation(SP_IDLE_COLS, SP_IDLE_ROWS, SP_SPRITE_IDLE);
    }

    @Test
    public void decisionTree_finishProducingEnemyIfDelayTimesHaveBothPassedForBomber() throws Exception {
        ProducerEnemy producerEnemy = BomberProducer.create(X_POS, Y_POS);
        List<RunningEnemy> runningEnemies = new ArrayList<>();

        Field f = ProducerEnemy.class.getDeclaredField("spawnTime");
        f.setAccessible(true);
        f.set(producerEnemy, 100);
        f.setAccessible(false);

        f = ProducerEnemy.class.getDeclaredField("producingTime");
        f.setAccessible(true);
        f.set(producerEnemy, 100);
        f.setAccessible(false);

        producerEnemy.decisionTree(runningEnemies);

        assertThat(producerEnemy.getXVel(), is(0f));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(X_POS));
        assertThat(producerEnemy.getY(), is(Y_POS));
        assertThat(producerEnemy.getProducingTime(), is(0L));

        assertThat(runningEnemies.isEmpty(), is(false));
        RunningEnemy runningEnemy = runningEnemies.get(0);

        assertThat(runningEnemy, instanceOf(Bomber.class));
        assertThat(runningEnemy.getX(), is(producerEnemy.getX() - CHARACTER_WIDTH));
        assertThat(runningEnemy.getY(), is(producerEnemy.getY()));

        //called once when first created so verify that its been called twice
        verifyStatic(AnimationFactory.class, times(2));
        AnimationFactory.createAnimation(BP_IDLE_COLS, BP_IDLE_ROWS, BP_SPRITE_IDLE);
    }

    @Test
    public void decisionTree_doNotProduceEnemyIfThereHasNotBeenADelay() {
        ProducerEnemy producerEnemy = StandardProducer.create(X_POS, Y_POS);
        List<RunningEnemy> runningEnemies = Collections.emptyList();

        producerEnemy.decisionTree(runningEnemies);

        assertThat(producerEnemy.getXVel(), is(0f));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(X_POS));
        assertThat(producerEnemy.getY(), is(Y_POS));
    }

    @Test
    public void moveForward_setsXVelocity() {
        ProducerEnemy producerEnemy = BomberProducer.create(X_POS, Y_POS);
        producerEnemy.setXVel(0).setYVel(0);

        producerEnemy.moveForward();

        assertThat(producerEnemy.getXVel(), is(-ENEMY_SPEED));
        assertThat(producerEnemy.getYVel(), is(0f));
    }

    @Test
    public void checkPlayerHasBeenKilled_killSelfAndTakePlayerHealthIfCollided() {
        ProducerEnemy producerEnemy = BomberProducer.create(X_POS, Y_POS);
        Player player = Player.create(X_POS, Y_POS);

        int expectedPlayerHealth = player.getHealth() - 1;

        producerEnemy.checkPlayerHasBeenKilled(player);

        assertThat(producerEnemy.getHealth(), is(0));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void checkPlayerHasBeenKilled_doNothingIfNotCollided() {
        ProducerEnemy producerEnemy = BomberProducer.create(X_POS, Y_POS);
        Player player = Player.create(100000, 100000);

        int expectedProducerHealth = producerEnemy.getHealth();
        int expectedPlayerHealth = player.getHealth();

        producerEnemy.checkPlayerHasBeenKilled(player);

        assertThat(producerEnemy.getHealth(), is(expectedProducerHealth));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void update_runsDecisionTreeThenChecksPlayerCollisions() {
        // check moving forward again to verify decisionTree invoked
        ProducerEnemy producerEnemy = StandardProducer.create(getScreenXMax(), Y_POS);
        float expectedX = getScreenXMax() - ENEMY_SPEED;

        // check player collision again to verify checkPlayerHasBeenKilled invoked
        Player player = Player.create(expectedX, Y_POS);
        int expectedPlayerHealth = player.getHealth() - 1;

        producerEnemy.update(player, Collections.emptyList());

        assertThat(producerEnemy.getXVel(), is(-ENEMY_SPEED));
        assertThat(producerEnemy.getYVel(), is(0f));
        assertThat(producerEnemy.getX(), is(expectedX));
        assertThat(producerEnemy.getY(), is(Y_POS));

        assertThat(producerEnemy.getHealth(), is(0));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }
}