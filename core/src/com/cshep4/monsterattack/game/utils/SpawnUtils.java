package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.pickup.BulletCase;
import com.cshep4.monsterattack.game.pickup.Life;
import com.cshep4.monsterattack.game.pickup.PickupItem;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_ENEMIES;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;
import static com.cshep4.monsterattack.game.constants.Constants.MIN_LEVEL;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MIN;
import static java.lang.System.currentTimeMillis;

public final class SpawnUtils {
    private static long enemySpawnTime = currentTimeMillis();
    private static long pickupSpawnTime = currentTimeMillis();

    private SpawnUtils() {}

    public static void spawnEnemies(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        if (isReadyToSpawn(runningEnemies, producerEnemies)) {
            int maxY = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2)) + 1;
            int minY = (int) getScreenYMax() / CHARACTER_HEIGHT_DIVIDER;

            float x = getScreenXMax();
            float y = ThreadLocalRandom.current().nextInt(minY, maxY);

            //TODO - Add difficulty
            int level = ThreadLocalRandom.current().nextInt(MIN_LEVEL, MAX_LEVEL + 1);
            int randEnemy = ThreadLocalRandom.current().nextInt(MIN_LEVEL, MAX_LEVEL + 1);

            switch (randEnemy) {
                case 1:
                    runningEnemies.add(Standard.create(x, y, level));
                    break;
                case 2:
                    producerEnemies.add(StandardProducer.create(x, y));
                    break;
                case 3:
                    runningEnemies.add(Bomber.create(x, y, level));
                    break;
                case 4:
                    producerEnemies.add(BomberProducer.create(x, y));
                    break;
                default:
                    runningEnemies.add(Standard.create(x, y, level));
                    break;
            }

            enemySpawnTime = currentTimeMillis();
        }
	}

	private static boolean isReadyToSpawn(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        return isLessThanMaxNumberOfEnemies(runningEnemies, producerEnemies) && checkSpawnDelay();
    }

	private static boolean isLessThanMaxNumberOfEnemies(List<RunningEnemy> runningEnemies, List<ProducerEnemy> producerEnemies) {
        return ((runningEnemies.size() + producerEnemies.size()) < MAX_ENEMIES);
    }

	private static boolean checkSpawnDelay() {
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

    private static PickupItem addPickup() {
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

    private static boolean checkPickupSpawnDelay() {
        int delay = ThreadLocalRandom.current().nextInt(PICKUP_SPAWN_DELAY_MIN, PICKUP_SPAWN_DELAY_MAX + 1);

        return currentTimeMillis() - pickupSpawnTime > delay;
    }
}
