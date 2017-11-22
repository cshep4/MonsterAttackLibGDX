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
//            if (checkSpawnDelay()) {
//                enemies.add(Create.standard(x, y, type));
//                enemySpawnTime = currentTimeMillis();
//            }
//            if (checkSpawnDelay()) {
//                producerEnemies.add(Create.standardProducer(x, y));
//                enemySpawnTime = currentTimeMillis();
//            }
//            if (checkSpawnDelay()) {
//                enemies.add(Create.bomber(x, y, 1));
//                enemySpawnTime = currentTimeMillis();
//            }
//            if (checkSpawnDelay()) {
//                producerEnemies.add(Create.bomberProducer(x, y));
//                enemySpawnTime = currentTimeMillis();
//            }

            if (checkSpawnDelay()) {
                int maxHeight = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2));

                float x = getScreenXMax();
                float y =  (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER) + (int)(Math.random() * maxHeight);

                int type = 1 + (int)(Math.random() * 4);
                int randEnemy = 1 + (int)(Math.random() * 4);

                switch (randEnemy) {
                    case 1:
                        enemies.add(Create.standard(x, y, type));
                        break;
                    case 2:
                        producerEnemies.add(Create.standardProducer(x, y));
                        break;
                    case 3:
                        enemies.add(Create.bomber(x, y, 1));
                        break;
                    case 4:
                        producerEnemies.add(Create.bomberProducer(x, y));
                        break;
                    default:
                        enemies.add(Create.standard(x, y, type));
                        break;
                }

                enemySpawnTime = currentTimeMillis();
            }
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
