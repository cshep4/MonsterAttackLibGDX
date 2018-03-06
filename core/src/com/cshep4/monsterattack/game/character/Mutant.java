package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.math.Rectangle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MIN;
import static com.cshep4.monsterattack.game.utils.DifficultyUtils.isMutationPossible;
import static com.cshep4.monsterattack.game.utils.Utils.getRandomNumber;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class Mutant extends Character {
    private static long mutateTime = System.currentTimeMillis();
    protected int level;

    protected Mutant(Rectangle rectangle, String texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
    }

    protected abstract void mutate();

    protected boolean isValidMutation() {
        return checkMutateDelay() && isMutationPossible(this);
    }

    private boolean checkMutateDelay() {
        int delay = getRandomNumber(MUTATE_DELAY_MIN, MUTATE_DELAY_MAX);
        return System.currentTimeMillis() - mutateTime > delay;
    }

    protected static void updateMutateTime() {
        mutateTime = System.currentTimeMillis();
    }
}
