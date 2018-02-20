package com.cshep4.monsterattack.game.ai;

import java.util.Random;

import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;
import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MIN;

public interface Mutation {
    void mutate();

    default boolean checkMutateDelay(long mutateTime) {
        Random rand = new Random();
        int delay = rand.nextInt(MUTATE_DELAY_MAX) + MUTATE_DELAY_MIN;

        return System.currentTimeMillis() - mutateTime > delay;
    }

    default boolean isValidMutation(int level, long mutateTime) {
        return checkMutateDelay(mutateTime) && level < MAX_LEVEL;

        // TODO - Add difficulty mutation
    }
}
