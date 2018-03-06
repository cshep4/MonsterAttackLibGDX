package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.pickup.BombCase;
import com.cshep4.monsterattack.game.pickup.BulletCase;
import com.cshep4.monsterattack.game.pickup.Life;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.pickup.Shield;

import java.util.List;

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
import static com.cshep4.monsterattack.game.utils.Utils.getRandomNumber;
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
        int delay = getRandomNumber(SPAWN_DELAY_MIN, SPAWN_DELAY_MAX);
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

        int x = getRandomNumber(minX, maxX);
        int y = getRandomNumber(minY, maxY);
        float size = getScreenXMax() / PICKUP_SIZE_DIVIDER;

        int randomNumber = getRandomNumber(1, 6);

        PickupItem pickup;

        switch (randomNumber) {
            case 1 :
            case 2 :
                pickup = Life.create(x, y, size, size);
                break;
            case 3 :
            case 4 :
                pickup = BulletCase.create(x, y, size, size);
                break;
            case 5 :
                pickup = BombCase.create(x, y, size, size);
                break;
            case 6 :
                pickup = Shield.create(x, y, size, size);
                break;
            default:
                pickup = Life.create(x, y, size, size);
                break;
        }

        return pickup;
    }

    private boolean checkPickupSpawnDelay() {
        int delay = getRandomNumber(PICKUP_SPAWN_DELAY_MIN, PICKUP_SPAWN_DELAY_MAX);

        return currentTimeMillis() - pickupSpawnTime > delay;
    }
}
