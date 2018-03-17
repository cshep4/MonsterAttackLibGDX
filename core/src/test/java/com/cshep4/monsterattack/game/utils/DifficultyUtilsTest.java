package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Mutant;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.MAX_LEVEL;
import static com.cshep4.monsterattack.game.core.EnemyType.BOMBER;
import static com.cshep4.monsterattack.game.core.EnemyType.STANDARD;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Utils.class})
public class DifficultyUtilsTest {
    private static final float ZERO = 0;
    private static final float SCREEN_DIMS = 450;
    private static final float X_POS = 10;
    private static final float Y_POS = 10;

    @Mock
    private Mutant mutant;

    @Mock
    private Standard standard;

    @Mock
    private Bomber bomber;

    @Mock
    private Animation animationWrapper;

    @Before
    public void init() {
        ScoreIndicator.resetKills();

        mockStatic(Utils.class);
        Mockito.when(Utils.getScreenXMax()).thenReturn(SCREEN_DIMS);
        Mockito.when(Utils.getScreenYMax()).thenReturn(SCREEN_DIMS);

        int maxY = (int) (getScreenYMax() - (getScreenXMax() / CHARACTER_HEIGHT_DIVIDER * 2));
        int minY = (int) getScreenYMax() / CHARACTER_HEIGHT_DIVIDER;
        when(Utils.getRandomNumber(minY, maxY)).thenReturn(new Random().nextInt((maxY + 1) - minY) + minY);

        mockStatic(AnimationFactory.class);
        Mockito.when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void isMutationPossible_returnsFalseWhenHigherThanMaxLevel() {
        setKills(0);

        when(mutant.getLevel()).thenReturn(MAX_LEVEL);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(false));
    }

    @Test
    public void isMutationPossible_difficulty1() {
        setKills(0);

        when(mutant.getLevel()).thenReturn(1);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(false));
    }

    @Test
    public void isMutationPossible_difficulty2() {
        setKills(6);

        when(mutant.getLevel()).thenReturn(1);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(false));
    }

    @Test
    public void isMutationPossible_difficulty3() {
        setKills(11);

        when(standard.getLevel()).thenReturn(1);

        boolean result = DifficultyUtils.isMutationPossible(standard);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty4() {
        setKills(16);

        when(bomber.getLevel()).thenReturn(1);

        boolean result = DifficultyUtils.isMutationPossible(bomber);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty5() {
        setKills(21);

        when(bomber.getLevel()).thenReturn(2);

        boolean result = DifficultyUtils.isMutationPossible(bomber);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty6() {
        setKills(26);

        when(mutant.getLevel()).thenReturn(2);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty7() {
        setKills(31);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(false));
    }

    @Test
    public void isMutationPossible_difficulty8() {
        setKills(36);

        when(bomber.getLevel()).thenReturn(3);

        boolean result = DifficultyUtils.isMutationPossible(bomber);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty9() {
        setKills(41);

        when(mutant.getLevel()).thenReturn(3);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty10() {
        setKills(46);

        when(mutant.getLevel()).thenReturn(3);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(true));
    }

    @Test
    public void isMutationPossible_difficulty11() {
        setKills(51);

        boolean result = DifficultyUtils.isMutationPossible(mutant);

        assertThat(result, is(false));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty1() {
        setKills(0);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, instanceOf(Standard.class));

        Mutant mutant = (Mutant) enemy;

        assertThat(mutant.getLevel(), is(1));

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty2() {
        setKills(6);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class), instanceOf(Bomber.class)));

        Mutant mutant = (Mutant) enemy;

        assertThat(mutant.getLevel(), is(1));

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty3() {
        setKills(11);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (mutant instanceof Standard) {
                assertThat(mutant.getLevel(), anyOf(is(1), is(2)));
            } else {
                assertThat(mutant.getLevel(), is(1));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty4() {
        setKills(16);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            assertThat(mutant.getLevel(), anyOf(is(1), is(2)));
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty5() {
        setKills(21);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            assertThat(mutant.getLevel(), is(2));
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty6() {
        setKills(26);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (enemy instanceof Bomber) {
                assertThat(mutant.getLevel(), anyOf(is(2), is(3)));
            } else {
                assertThat(mutant.getLevel(), is(2));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty7() {
        setKills(31);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class)));

        Mutant mutant = (Mutant) enemy;

        assertThat(mutant.getLevel(), is(3));

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty8() {
        setKills(36);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (enemy instanceof Bomber) {
                assertThat(mutant.getLevel(), is(3));
            } else {
                assertThat(mutant.getLevel(), anyOf(is(2), is(3)));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty9() {
        setKills(41);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (enemy instanceof Bomber) {
                assertThat(mutant.getLevel(), anyOf(is(3), is(4)));
            } else {
                assertThat(mutant.getLevel(), is(3));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty10() {
        setKills(46);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (enemy instanceof Bomber) {
                assertThat(mutant.getLevel(), is(4));
            } else {
                assertThat(mutant.getLevel(), is(3));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void spawnEnemyBasedOnDifficulty_difficulty11() {
        setKills(51);

        Enemy enemy = DifficultyUtils.spawnEnemyBasedOnDifficulty();

        assertThat(enemy, anyOf(instanceOf(Standard.class),
                instanceOf(Bomber.class),
                instanceOf(StandardProducer.class),
                instanceOf(BomberProducer.class)));

        if (enemy instanceof Standard || enemy instanceof Bomber) {
            Mutant mutant = (Mutant) enemy;

            if (enemy instanceof Bomber) {
                assertThat(mutant.getLevel(), is(4));
            } else {
                assertThat(mutant.getLevel(), is(4));
            }
        }

        Character character = (Character) enemy;

        assertThat(character.getX(), is(getScreenXMax()));
        assertThat(character.getY(), greaterThan(ZERO));
        assertThat(character.getY(), lessThan(getScreenYMax()));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty1To3() {
        setKills(0);
        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), is(1));
        assertThat(runningEnemy, instanceOf(Standard.class));

        ScoreIndicator.resetKills();
        setKills(6);
        runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, BOMBER);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), is(1));
        assertThat(runningEnemy, instanceOf(Bomber.class));

        ScoreIndicator.resetKills();
        setKills(11);
        runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), is(1));
        assertThat(runningEnemy, instanceOf(Standard.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty4To5() {
        setKills(16);

        int max = 2;
        int min = 1;
        when(Utils.getRandomNumber(min, max)).thenReturn(new Random().nextInt((max + 1) - min) + min);

        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(1),is(2)));
        assertThat(runningEnemy, instanceOf(Standard.class));

        ScoreIndicator.resetKills();
        setKills(21);
        runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, BOMBER);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(1),is(2)));
        assertThat(runningEnemy, instanceOf(Bomber.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty6() {
        setKills(26);

        int max = 3;
        int min = 1;
        when(Utils.getRandomNumber(min, max)).thenReturn(new Random().nextInt((max + 1) - min) + min);

        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(1),is(2),is(3)));
        assertThat(runningEnemy, instanceOf(Standard.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty7To8() {
        setKills(31);

        int max = 3;
        int min = 2;
        when(Utils.getRandomNumber(min, max)).thenReturn(new Random().nextInt((max + 1) - min) + min);

        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(2),is(3)));
        assertThat(runningEnemy, instanceOf(Standard.class));

        ScoreIndicator.resetKills();
        setKills(36);
        runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, BOMBER);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(2),is(3)));
        assertThat(runningEnemy, instanceOf(Bomber.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty9() {
        setKills(41);

        int max = 4;
        int min = 2;
        when(Utils.getRandomNumber(min, max)).thenReturn(new Random().nextInt((max + 1) - min) + min);

        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, STANDARD);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(2),is(3),is(4)));
        assertThat(runningEnemy, instanceOf(Standard.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty10() {
        setKills(46);

        int max = 4;
        int min = 3;
        when(Utils.getRandomNumber(min, max)).thenReturn(new Random().nextInt((max + 1) - min) + min);

        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, BOMBER);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), anyOf(is(3),is(4)));
        assertThat(runningEnemy, instanceOf(Bomber.class));
    }

    @Test
    public void produceEnemyBasedDifficulty_difficulty11() {
        setKills(51);
        RunningEnemy runningEnemy = DifficultyUtils.produceEnemyBasedDifficulty(X_POS, Y_POS, BOMBER);

        assertThat(runningEnemy.getX(), is(X_POS));
        assertThat(runningEnemy.getY(), is(Y_POS));
        assertThat(runningEnemy.getLevel(), is(4));
        assertThat(runningEnemy, instanceOf(Bomber.class));
    }

    private void setKills(int number) {
        for (int i=0; i < number; i++) {
            ScoreIndicator.incrementKills();
        }
    }
}