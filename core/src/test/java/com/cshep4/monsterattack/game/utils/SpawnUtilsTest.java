package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static com.cshep4.monsterattack.game.constants.Constants.MAX_ENEMIES;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnEnemy;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnPickup;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, GameScreen.class})
public class SpawnUtilsTest {
    private static final float SCREEN_DIMS = 450;

    @Mock
    private Animation animationWrapper;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        Mockito.when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        mockStatic(GameScreen.class);
        Mockito.when(GameScreen.getScreenXMax()).thenReturn(SCREEN_DIMS);
        Mockito.when(GameScreen.getScreenYMax()).thenReturn(SCREEN_DIMS);
    }

    @Test
    public void spawnEnemy_enemySpawnsWhenDelayHasPassedAndLessThanMaxOnScreen() throws Exception {
        //set spawn time to current time - max delay to simulate delay passing
        long spawnTime = currentTimeMillis() - SPAWN_DELAY_MAX - 1;
        Field f = SpawnUtils.class.getDeclaredField("enemySpawnTime");
        f.setAccessible(true);
        f.set(SpawnUtils.class, spawnTime);
        f.setAccessible(false);

        Enemy enemy = spawnEnemy(0);

        assertThat(enemy, is(notNullValue()));
        assertThat(enemy, instanceOf(Enemy.class));
        assertThat(SpawnUtils.getEnemySpawnTime(), greaterThan(spawnTime));
    }

    @Test
    public void spawnEnemy_enemyDoesNotSpawnWhenDelayHasNotPassedAndLessThanMaxOnScreen() throws Exception {
        //set spawn time to current time + max delay to simulate delay not passed yet
        long spawnTime = currentTimeMillis() + SPAWN_DELAY_MAX;
        Field f = SpawnUtils.class.getDeclaredField("enemySpawnTime");
        f.setAccessible(true);
        f.set(SpawnUtils.class, spawnTime);
        f.setAccessible(false);

        Enemy enemy = spawnEnemy(0);

        assertThat(enemy, is(nullValue()));
        assertThat(SpawnUtils.getEnemySpawnTime(), is(spawnTime));
    }

    @Test
    public void spawnEnemy_enemyDoesNotSpawnWhenDelayHasPassedAndMaxEnemiesOnScreen() throws Exception {
        //set spawn time to current time - max delay to simulate delay passing
        long spawnTime = currentTimeMillis() - SPAWN_DELAY_MAX - 1;
        Field f = SpawnUtils.class.getDeclaredField("enemySpawnTime");
        f.setAccessible(true);
        f.set(SpawnUtils.class, spawnTime);
        f.setAccessible(false);

        Enemy enemy = spawnEnemy(MAX_ENEMIES);

        assertThat(enemy, is(nullValue()));
        assertThat(SpawnUtils.getEnemySpawnTime(), is(spawnTime));
    }

    @Test
    public void spawnPickups_spawnsPickupWhenDelayHasPassed() throws Exception {
        //set spawn time to current time - max delay to simulate delay passing
        long spawnTime = currentTimeMillis() - PICKUP_SPAWN_DELAY_MAX - 1;
        Field f = SpawnUtils.class.getDeclaredField("pickupSpawnTime");
        f.setAccessible(true);
        f.set(SpawnUtils.class, spawnTime);
        f.setAccessible(false);

        PickupItem pickup = spawnPickup();

        assertThat(pickup, is(notNullValue()));
        assertThat(pickup, instanceOf(PickupItem.class));
        assertThat(SpawnUtils.getPickupSpawnTime(), greaterThan(spawnTime));
    }

    @Test
    public void spawnPickups_doesNotSpawnPickupWhenDelayHasNotPassed() throws Exception {
        //set spawn time to current time + max delay to simulate delay not passed yet
        long spawnTime = currentTimeMillis() + PICKUP_SPAWN_DELAY_MAX;
        Field f = SpawnUtils.class.getDeclaredField("pickupSpawnTime");
        f.setAccessible(true);
        f.set(SpawnUtils.class, spawnTime);
        f.setAccessible(false);

        PickupItem pickup = spawnPickup();
        assertThat(pickup, is(nullValue()));
        assertThat(SpawnUtils.getPickupSpawnTime(), is(spawnTime));
    }
}