package com.cshep4.monsterattack.game.button;

import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnimationFactory.class)
public class ShootButtonTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

    @Mock
    private Animation animationWrapper;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void create() throws Exception {
        GameObject shootButton = ShootButton.create(X_POS, Y_POS, WIDTH, HEIGHT);

        assertThat(shootButton, instanceOf(ShootButton.class));
        assertThat(shootButton.getX(), is(X_POS));
        assertThat(shootButton.getY(), is(Y_POS));
        assertThat(shootButton.getWidth(), is(WIDTH));
        assertThat(shootButton.getHeight(), is(HEIGHT));
    }
}