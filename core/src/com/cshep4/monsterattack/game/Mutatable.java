package com.cshep4.monsterattack.game;

import java.util.Random;

public interface Mutatable {
    public void mutate();

    public default boolean checkMutateDelay(long mutateTime) {
        Random rand = new Random();
        int delay = rand.nextInt(Constants.MUTATE_DELAY_MAX) + Constants.MUTATE_DELAY_MIN;

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
