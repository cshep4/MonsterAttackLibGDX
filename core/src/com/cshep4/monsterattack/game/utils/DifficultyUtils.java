package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Mutant;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;

import java.util.concurrent.ThreadLocalRandom;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;

@UtilityClass
public class DifficultyUtils {
    public static boolean isMutationPossible(Mutant mutant) {
        if (mutant.getLevel() < MAX_LEVEL) {
            return false;
        }

        int difficultyLevel = getDifficulty();

        switch (difficultyLevel) {
            case 1 :
                return false;
            case 2 :
                return false;
            case 3 :
                return mutant instanceof Standard && mutant.getLevel() <= 1;
            case 4 :
                return mutant.getLevel() <= 1;
            case 5 :
                return mutant instanceof Bomber && mutant.getLevel() <= 2;
            case 6 :
                return mutant.getLevel() <= 2;
            case 7 :
                return false;
            case 8 :
                return mutant instanceof Bomber && mutant.getLevel() <= 3;
            case 9 :
                return mutant.getLevel() <= 3;
            case 10 :
                return mutant.getLevel() <= 3;
            case 11 :
                return false;
            default :
                return false;

        }
    }

    public static Enemy spawnEnemyBasedOnDifficulty() {
        int maxY = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2)) + 1;
        int minY = (int) getScreenYMax() / CHARACTER_HEIGHT_DIVIDER;

        float x = getScreenXMax();
        float y = ThreadLocalRandom.current().nextInt(minY, maxY);

        int difficultyLevel = getDifficulty();

        switch (difficultyLevel) {
            case 1 :
                return spawnDifficulty1(x, y);
            case 2 :
                return spawnDifficulty2(x, y);
            case 3 :
                return spawnDifficulty3(x, y);
            case 4 :
                return spawnDifficulty4(x, y);
            case 5 :
                return spawnDifficulty5(x, y);
            case 6 :
                return spawnDifficulty6(x, y);
            case 7 :
                return spawnDifficulty7(x, y);
            case 8 :
                return spawnDifficulty8(x, y);
            case 9 :
                return spawnDifficulty9(x, y);
            case 10 :
                return spawnDifficulty10(x, y);
            case 11 :
                return spawnDifficulty11(x, y);
            default :
                return spawnDifficulty1(x, y);

        }
    }

    private Enemy spawnDifficulty1(float x, float y) {
        return Standard.create(x, y, 1);
    }

    private Enemy spawnDifficulty2(float x, float y) {
        int option = getRandomOption(2);
        switch (option) {
            case 1:
                return Standard.create(x, y, 1);
            case 2 :
                return Bomber.create(x, y, 1);
            default:
                return Standard.create(x, y, 1);
        }
    }

    private Enemy spawnDifficulty3(float x, float y) {
        int option = getRandomOption(4);
        switch (option) {
            case 1:
                return Standard.create(x, y, 1);
            case 2 :
                return Bomber.create(x, y, 1);
            case 3 :
                return Standard.create(x, y, 2);
            case 4 :
                return StandardProducer.create(x, y);
            default:
                return Standard.create(x, y, 1);
        }
    }

    private Enemy spawnDifficulty4(float x, float y) {
        int option = getRandomOption(6);
        switch (option) {
            case 1:
                return Standard.create(x, y, 1);
            case 2 :
                return Bomber.create(x, y, 1);
            case 3 :
                return Standard.create(x, y, 2);
            case 4 :
                return Bomber.create(x, y, 2);
            case 5 :
                return StandardProducer.create(x, y);
            case 6 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 1);
        }
    }

    private Enemy spawnDifficulty5(float x, float y) {
        int option = getRandomOption(4);
        switch (option) {
            case 1 :
                return Bomber.create(x, y, 2);
            case 2 :
                return Standard.create(x, y, 2);
            case 3 :
                return StandardProducer.create(x, y);
            case 4 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 2);
        }
    }

    private Enemy spawnDifficulty6(float x, float y) {
        int option = getRandomOption(5);
        switch (option) {
            case 1:
                return Standard.create(x, y, 2);
            case 2 :
                return Bomber.create(x, y, 2);
            case 3 :
                return Bomber.create(x, y, 3);
            case 4 :
                return StandardProducer.create(x, y);
            case 5 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 2);
        }
    }

    private Enemy spawnDifficulty7(float x, float y) {
        int option = getRandomOption(2);
        switch (option) {
            case 1:
                return Standard.create(x, y, 3);
            case 2 :
                return Bomber.create(x, y, 3);
            default:
                return Standard.create(x, y, 3);
        }
    }

    private Enemy spawnDifficulty8(float x, float y) {
        int option = getRandomOption(5);
        switch (option) {
            case 1:
                return Standard.create(x, y, 3);
            case 2 :
                return Bomber.create(x, y, 3);
            case 3 :
                return Standard.create(x, y, 2);
            case 4 :
                return StandardProducer.create(x, y);
            case 5 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 3);
        }
    }

    private Enemy spawnDifficulty9(float x, float y) {
        int option = getRandomOption(5);
        switch (option) {
            case 1:
                return Standard.create(x, y, 3);
            case 2 :
                return Bomber.create(x, y, 3);
            case 3 :
                return Bomber.create(x, y, 4);
            case 4 :
                return StandardProducer.create(x, y);
            case 5 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 3);
        }
    }

    private Enemy spawnDifficulty10(float x, float y) {
        int option = getRandomOption(4);
        switch (option) {
            case 1:
                return Standard.create(x, y, 3);
            case 2 :
                return Bomber.create(x, y, 4);
            case 3 :
                return StandardProducer.create(x, y);
            case 4 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 3);
        }
    }

    private Enemy spawnDifficulty11(float x, float y) {
        int option = getRandomOption(4);
        switch (option) {
            case 1:
                return Standard.create(x, y, 4);
            case 2 :
                return Bomber.create(x, y, 4);
            case 3 :
                return StandardProducer.create(x, y);
            case 4 :
                return BomberProducer.create(x, y);
            default:
                return Standard.create(x, y, 4);
        }
    }

    private int getRandomOption(int numOptions) {
        return ThreadLocalRandom.current().nextInt(1, numOptions + 1);
    }

    private int getDifficulty() {
        double difficulty = (double) GameScreen.getNumKills() / 5;
        return difficulty < 11 ? (int) Math.ceil(difficulty) : 11;
    }
}
