package com.cshep4.monsterattack.game.character;

import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.utils.DifficultyUtils;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, DifficultyUtils.class})
public class MutantTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final int LEVEL = 1;

    @Mock
    private Animation animationWrapper;

    private Mutant mutant;

    @Before
    public void init() {
        mockStatic(DifficultyUtils.class);
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        mutant = Bomber.create(X_POS, Y_POS, LEVEL);
    }

    @Test
    public void isValidMutation_returnsTrueWhenMutationConditionsExists() throws Exception {
        when(DifficultyUtils.isMutationPossible(mutant)).thenReturn(true);
        Field f = Mutant.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(mutant, 100);
        f.setAccessible(false);

        boolean result = mutant.isValidMutation();

        assertThat(result, is(true));
    }

    @Test
    public void isValidMutation_returnsFalseWhenCurrentDifficultyIsNotCorrect() throws Exception {
        when(DifficultyUtils.isMutationPossible(mutant)).thenReturn(false);
        Field f = Mutant.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(mutant, 100);
        f.setAccessible(false);

        boolean result = mutant.isValidMutation();

        assertThat(result, is(false));
    }

    @Test
    public void isValidMutation_returnsFalseWhenDueToDelay() throws Exception {
        when(DifficultyUtils.isMutationPossible(mutant)).thenReturn(true);
        Field f = Mutant.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(mutant, System.currentTimeMillis());
        f.setAccessible(false);

        boolean result = mutant.isValidMutation();

        assertThat(result, is(false));
    }

    @Test
    public void updateMutateTime_updatesMutateTimeToCurrentTime() {
        long beforeTime = Mutant.getMutateTime();

        Mutant.updateMutateTime();

        assertThat(Mutant.getMutateTime(), greaterThan(beforeTime));
    }
}