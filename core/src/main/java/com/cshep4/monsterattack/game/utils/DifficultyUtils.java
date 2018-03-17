package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Mutant;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.core.EnemyType;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;
import static com.cshep4.monsterattack.game.core.EnemyType.STANDARD;
import static com.cshep4.monsterattack.game.utils.Utils.getRandomNumber;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;

@UtilityClass
public class DifficultyUtils {
    public static boolean isMutationPossible(Mutant mutant) {
        if (mutant.getLevel() >= MAX_LEVEL) {
            return false;
        }

        int difficultyLevel = getDifficulty();

        boolean result;

        switch (difficultyLevel) {
            case 1 :
                result = false;
                break;
            case 2 :
                result = false;
                break;
            case 3 :
                result = mutant instanceof Standard && mutant.getLevel() <= 1;
                break;
            case 4 :
                result = mutant.getLevel() <= 1;
                break;
            case 5 :
                result = mutant instanceof Bomber && mutant.getLevel() <= 2;
                break;
            case 6 :
                result = mutant.getLevel() <= 2;
                break;
            case 7 :
                result = false;
                break;
            case 8 :
                result = mutant instanceof Bomber && mutant.getLevel() <= 3;
                break;
            case 9 :
            case 10 :
                result = mutant.getLevel() <= 3;
                break;
            case 11 :
                result = false;
                break;
            default :
                result = false;
                break;

        }

        return result;
    }

    static Enemy spawnEnemyBasedOnDifficulty() {
        int maxY = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2));
        int minY = (int) getScreenYMax() / CHARACTER_HEIGHT_DIVIDER;

        float x = getScreenXMax();
        float y = getRandomNumber(minY, maxY);

        int difficultyLevel = getDifficulty();

        Enemy enemy;

        switch (difficultyLevel) {
            case 1 :
                enemy = spawnDifficulty1(x, y);
                break;
            case 2 :
                enemy = spawnDifficulty2(x, y);
                break;
            case 3 :
                enemy = spawnDifficulty3(x, y);
                break;
            case 4 :
                enemy = spawnDifficulty4(x, y);
                break;
            case 5 :
                enemy = spawnDifficulty5(x, y);
                break;
            case 6 :
                enemy = spawnDifficulty6(x, y);
                break;
            case 7 :
                enemy = spawnDifficulty7(x, y);
                break;
            case 8 :
                enemy = spawnDifficulty8(x, y);
                break;
            case 9 :
                enemy = spawnDifficulty9(x, y);
                break;
            case 10 :
                enemy = spawnDifficulty10(x, y);
                break;
            case 11 :
                enemy = spawnDifficulty11(x, y);
                break;
            default :
                enemy = spawnDifficulty1(x, y);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty1(float x, float y) {
        return Standard.create(x, y, 1);
    }

    private Enemy spawnDifficulty2(float x, float y) {
        int option = getRandomNumber(1, 2);

        Enemy enemy;

        switch (option) {
            case 1 :
                enemy = Standard.create(x, y, 1);
                break;
            case 2 :
                enemy = Bomber.create(x, y, 1);
                break;
            default:
                enemy = Standard.create(x, y, 1);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty3(float x, float y) {
        int option = getRandomNumber(1, 7);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 1);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 1);
                break;
            case 5 :
            case 6 :
                enemy = Standard.create(x, y, 2);
                break;
            case 7 :
                enemy = StandardProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 1);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty4(float x, float y) {
        int option = getRandomNumber(1, 10);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 1);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 1);
                break;
            case 5 :
            case 6 :
                enemy = Standard.create(x, y, 2);
                break;
            case 7 :
            case 8 :
                enemy = Bomber.create(x, y, 2);
                break;
            case 9 :
                enemy = StandardProducer.create(x, y);
                break;
            case 10 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 1);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty5(float x, float y) {
        int option = getRandomNumber(1, 6);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Bomber.create(x, y, 2);
                break;
            case 3 :
            case 4 :
                enemy = Standard.create(x, y, 2);
                break;
            case 5 :
                enemy = StandardProducer.create(x, y);
                break;
            case 6 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 2);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty6(float x, float y) {
        int option = getRandomNumber(1, 8);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 2);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 2);
                break;
            case 5 :
            case 6 :
                enemy = Bomber.create(x, y, 3);
                break;
            case 7 :
                enemy = StandardProducer.create(x, y);
                break;
            case 8 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 2);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty7(float x, float y) {
        int option = getRandomNumber(1, 2);

        Enemy enemy;

        switch (option) {
            case 1 :
                enemy = Standard.create(x, y, 3);
                break;
            case 2 :
                enemy = Bomber.create(x, y, 3);
                break;
            default:
                enemy = Standard.create(x, y, 3);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty8(float x, float y) {
        int option = getRandomNumber(1, 8);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 3);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 3);
                break;
            case 5 :
            case 6 :
                enemy = Standard.create(x, y, 2);
                break;
            case 7 :
                enemy = StandardProducer.create(x, y);
                break;
            case 8 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 3);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty9(float x, float y) {
        int option = getRandomNumber(1, 8);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 3);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 3);
                break;
            case 5 :
            case 6 :
                enemy = Bomber.create(x, y, 4);
                break;
            case 7 :
                enemy = StandardProducer.create(x, y);
                break;
            case 8 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 3);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty10(float x, float y) {
        int option = getRandomNumber(1, 6);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 3);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 4);
                break;
            case 5 :
                enemy = StandardProducer.create(x, y);
                break;
            case 6 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 3);
                break;
        }

        return enemy;
    }

    private Enemy spawnDifficulty11(float x, float y) {
        int option = getRandomNumber(1, 6);

        Enemy enemy;

        switch (option) {
            case 1 :
            case 2 :
                enemy = Standard.create(x, y, 4);
                break;
            case 3 :
            case 4 :
                enemy = Bomber.create(x, y, 4);
                break;
            case 5 :
                enemy = StandardProducer.create(x, y);
                break;
            case 6 :
                enemy = BomberProducer.create(x, y);
                break;
            default:
                enemy = Standard.create(x, y, 4);
                break;
        }

        return enemy;
    }

    public static RunningEnemy produceEnemyBasedDifficulty(float x, float y, EnemyType enemyType) {
        int difficultyLevel = getDifficulty();
        int level;
        switch (difficultyLevel) {
            case 1 :
            case 2 :
            case 3 :
                level = 1;
                break;
            case 4 :
            case 5 :
                level = getRandomNumber(1, 2);
                break;
            case 6 :
                level = getRandomNumber(1, 3);
                break;
            case 7 :
            case 8 :
                level = getRandomNumber(2, 3);
                break;
            case 9 :
                level = getRandomNumber(2, 4);
                break;
            case 10 :
                level = getRandomNumber(3, 4);
                break;
            case 11 :
                level = 4;
                break;
            default :
                level = getRandomNumber(1, 4);
                break;

        }

        return produceEnemy(x, y, level, enemyType);
    }

    private RunningEnemy produceEnemy(float x, float y, int level, EnemyType enemyType) {
        RunningEnemy enemy;

        if (enemyType == STANDARD) {
            enemy = Standard.create(x, y, level);
        } else {
            enemy = Bomber.create(x, y, level);
        }

        return enemy;
    }

    private int getDifficulty() {
        double difficulty = (double) ScoreIndicator.getNumKills() / 5;

        if (difficulty == 0) {
            difficulty = 1;
        }

        return difficulty < 11 ? (int) Math.ceil(difficulty) : 11;
    }
}
