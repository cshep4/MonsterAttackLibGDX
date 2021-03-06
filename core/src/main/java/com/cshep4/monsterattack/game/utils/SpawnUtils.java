package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.pickup.BombCase;
import com.cshep4.monsterattack.game.pickup.BulletCase;
import com.cshep4.monsterattack.game.pickup.Life;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.pickup.Shield;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.game.constants.Constants.MAX_ENEMIES;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.utils.DifficultyUtils.spawnEnemyBasedOnDifficulty;
import static com.cshep4.monsterattack.game.utils.Utils.getRandomNumber;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;
import static java.lang.System.currentTimeMillis;

@UtilityClass
public final class SpawnUtils {
    @Getter
    private static long enemySpawnTime = currentTimeMillis();
    @Getter
    private static long pickupSpawnTime = currentTimeMillis();

    public static Enemy spawnEnemy(int enemyNumber) {
        Enemy enemy;

        if (isReadyToSpawn(enemyNumber)) {
            enemy = spawnEnemyBasedOnDifficulty();
            enemySpawnTime = currentTimeMillis();
        } else {
            enemy = null;
        }

        return enemy;
	}

	private boolean isReadyToSpawn(int enemyNumber) {
        return isLessThanMaxNumberOfEnemies(enemyNumber) && checkSpawnDelay();
    }

	private boolean isLessThanMaxNumberOfEnemies(int enemyNumber) {
        return enemyNumber < MAX_ENEMIES;
    }

	private boolean checkSpawnDelay() {
        int delay = getRandomNumber(SPAWN_DELAY_MIN, SPAWN_DELAY_MAX);
        return currentTimeMillis() - enemySpawnTime > delay;
	}

    public static PickupItem spawnPickup() {
        PickupItem pickup;

        if (checkPickupSpawnDelay()) {
            pickup = addPickup();
            pickupSpawnTime = currentTimeMillis();
        } else {
            pickup = null;
        }

        return pickup;
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
