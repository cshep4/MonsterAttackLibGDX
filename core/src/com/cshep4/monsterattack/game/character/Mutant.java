package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.MUTATE_DELAY_MIN;
import static com.cshep4.monsterattack.game.utils.DifficultyUtils.isMutationPossible;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Mutant extends Character {
    private static long mutateTime = System.currentTimeMillis();
    protected int level;

    public Mutant(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
    }

    protected abstract void mutate();

    protected boolean isValidMutation() {
        return checkMutateDelay() && isMutationPossible(this);
    }

    private boolean checkMutateDelay() {
        Random rand = new Random();
        int delay = rand.nextInt(MUTATE_DELAY_MAX) + MUTATE_DELAY_MIN;

        return System.currentTimeMillis() - mutateTime > delay;
    }

    protected static void updateMutateTime() {
        mutateTime = System.currentTimeMillis();
    }
}
