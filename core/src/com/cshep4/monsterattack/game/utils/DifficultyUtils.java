package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.Mutant;
import com.cshep4.monsterattack.game.character.Standard;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;

@UtilityClass
public class DifficultyUtils {
    public boolean isMutationPossible(Mutant mutant) {
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

    private int getDifficulty() {
        return (int) Math.ceil(GameScreen.getNumKills() / 5);
    }
}
