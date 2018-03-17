package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_DURATION;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class})
public class PickupItemTest {
    private static final int X_POS = 123;
    private static final int Y_POS = 123;
    private static final float SIZE = getScreenXMax() / PICKUP_SIZE_DIVIDER;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Application app;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
    }

    @Test
    public void isPickedUp_playerRewardedIfPlayerCollidesWithItem() {
        Player player = Player.create(X_POS, Y_POS);
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        int expectedNumberOfLives = player.getHealth() + 1;

        boolean result = life.isPickedUp(player);

        assertThat(player.getHealth(), is(expectedNumberOfLives));
        assertThat(result, is(true));
    }

    @Test
    public void isPickedUp_playerNotRewardedIfPlayerDoesNotCollideWithItem() {
        Player player = Player.create(100000, 100000);
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        int expectedNumberOfLives = player.getHealth();

        boolean result = life.isPickedUp(player);

        assertThat(player.getHealth(), is(expectedNumberOfLives));
        assertThat(result, is(false));
    }

    @Test
    public void hasExpired_returnsTrueWhenFiveSecondsHavePassed() throws Exception {
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        //set spawn time to 5 seconds ago
        long spawnTime = currentTimeMillis() - PICKUP_DURATION - 1;
        Field f = PickupItem.class.getDeclaredField("spawnTime");
        f.setAccessible(true);
        f.set(life, spawnTime);
        f.setAccessible(false);

        boolean result = life.hasExpired();

        assertThat(result, is(true));
    }

    @Test
    public void hasExpired_returnsFalseWhenFiveSecondsHaveNotPassed() {
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        boolean result = life.hasExpired();

        assertThat(result, is(false));
    }
}