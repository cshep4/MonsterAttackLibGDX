package com.cshep4.monsterattack.game;

import java.util.Random;

import static com.cshep4.monsterattack.game.Constants.MUTATE_DELAY_MAX;
import static com.cshep4.monsterattack.game.Constants.MUTATE_DELAY_MIN;

public interface Mutatable {
    public void mutate();

    public default boolean checkMutateDelay(long mutateTime) {
        Random rand = new Random();
        int delay = rand.nextInt(MUTATE_DELAY_MAX) + MUTATE_DELAY_MIN;

        return System.currentTimeMillis() - mutateTime > delay;
    }

    public default boolean isValidMutation(int type, long mutateTime) {
        if (!this.checkMutateDelay(mutateTime)) {
            return false;
        }
        if (type >= 4) {
            return false;
        }
        // TODO - Add difficulty mutation
        return true;
    }
}
