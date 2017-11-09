package com.cshep4.monsterattack.game;

import java.util.List;
import java.util.Random;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.MAX_ENEMIES;
import static com.cshep4.monsterattack.game.Constants.SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.Constants.SPAWN_DELAY_MIN;
import static java.lang.System.currentTimeMillis;

public final class Spawn {
    private static long enemySpawnTime = currentTimeMillis();

    public static void spawnEnemies(List<Enemy> enemies, List<ProducerEnemy> producerEnemies) {
        if (lessThanMaxNumberOfEnemies(enemies, producerEnemies)) {
            Random rand = new Random();
            int maxHeight = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2));

            float x = getScreenXMax();
            float y = rand.nextInt(maxHeight);

            rand = new Random();
            int type = rand.nextInt(4) + 1;

            if (checkSpawnDelay()) {
                enemies.add(Create.standard(x, y, 3));
                enemySpawnTime = currentTimeMillis();
            }
//            if (checkSpawnDelay()) {
//                producerEnemies.add(Create.standardProducer(x, y));
//                enemySpawnTime = currentTimeMillis();
//            }
//            if (checkSpawnDelay()) {
//                enemies.add(Create.bomber(x, y, type));
//                enemySpawnTime = currentTimeMillis();
//            }
//            if (checkSpawnDelay()) {
//                producerEnemies.add(Create.bomberProducer(x, y));
//                enemySpawnTime = currentTimeMillis();
//            }
        }
	}

	private static boolean lessThanMaxNumberOfEnemies(List<Enemy> enemies, List<ProducerEnemy> producerEnemies) {
        return ((enemies.size() + producerEnemies.size()) < MAX_ENEMIES);
    }

	private static boolean checkSpawnDelay() {
        Random rand = new Random();
        int delay = rand.nextInt(SPAWN_DELAY_MAX) + SPAWN_DELAY_MIN;

        return currentTimeMillis() - enemySpawnTime > delay;
	}
}
