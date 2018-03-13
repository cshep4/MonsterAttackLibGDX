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

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class})
public class LifeTest {
    private static final float X_POS = 123;
    private static final float Y_POS = 123;
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
    public void create() {
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        assertThat(life, instanceOf(Life.class));
        assertThat(life.getX(), is(X_POS));
        assertThat(life.getY(), is(Y_POS));
        assertThat(life.getWidth(), is(SIZE));
        assertThat(life.getHeight(), is(SIZE));
    }

    @Test
    public void reward_givesPlayerExtraLife() {
        Player player = Player.create(X_POS, Y_POS);
        Life life = Life.create(X_POS, Y_POS, SIZE, SIZE);

        int expectedHealth = player.getHealth() + 1;

        life.reward(player);

        assertThat(player.getHealth(), is(expectedHealth));
    }
}