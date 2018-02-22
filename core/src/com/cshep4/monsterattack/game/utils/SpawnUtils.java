package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.pickup.BulletCase;
import com.cshep4.monsterattack.game.pickup.Life;
import com.cshep4.monsterattack.game.pickup.PickupItem;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_ENEMIES;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.utils.DifficultyUtils.spawnEnemyBasedOnDifficulty;
import static java.lang.System.currentTimeMillis;

@UtilityClass
public final class SpawnUtils {
    private static long enemySpawnTime = currentTimeMillis();
    private static long pickupSpawnTime = currentTimeMillis();

    public static void spawnEnemies(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        if (isReadyToSpawn(runningEnemies, producerEnemies)) {
            Enemy enemy = spawnEnemyBasedOnDifficulty();

            if (enemy instanceof ProducerEnemy) {
                producerEnemies.add((ProducerEnemy) enemy);
            } else {
                runningEnemies.add((RunningEnemy) enemy);
            }

            enemySpawnTime = currentTimeMillis();
        }
	}

	private boolean isReadyToSpawn(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        return isLessThanMaxNumberOfEnemies(runningEnemies, producerEnemies) && checkSpawnDelay();
    }

	private boolean isLessThanMaxNumberOfEnemies(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        return ((runningEnemies.size() + producerEnemies.size()) < MAX_ENEMIES);
    }

	private boolean checkSpawnDelay() {
        Random rand = new Random();
        int delay = rand.nextInt(SPAWN_DELAY_MAX) + SPAWN_DELAY_MIN;

        return currentTimeMillis() - enemySpawnTime > delay;
	}

    public static void spawnPickups(List<PickupItem> pickups) {
        if (checkPickupSpawnDelay()) {
            pickups.add(addPickup());
            pickupSpawnTime = currentTimeMillis();
        }
    }

    private PickupItem addPickup() {
        int maxY = (int) (getScreenYMax() - (getScreenXMax() / PICKUP_SIZE_DIVIDER * 2)) + 1;
        int minY = (int) getScreenYMax() / PICKUP_SIZE_DIVIDER;

        int maxX = (int) (getScreenXMax() * 0.75);
        int minX = (int) getScreenXMax() / PICKUP_SIZE_DIVIDER;

        Random rand = new Random();
        int x = rand.nextInt(maxX) + minX;
        int y = rand.nextInt(maxY) + minY;
        float size = getScreenXMax() / PICKUP_SIZE_DIVIDER;

        if (rand.nextBoolean()) {
            return Life.create(x, y, size, size);
        } else {
            return BulletCase.create(x, y, size, size);
        }
    }

    private boolean checkPickupSpawnDelay() {
        int delay = ThreadLocalRandom.current().nextInt(PICKUP_SPAWN_DELAY_MIN, PICKUP_SPAWN_DELAY_MAX + 1);

        return currentTimeMillis() - pickupSpawnTime > delay;
    }
}
