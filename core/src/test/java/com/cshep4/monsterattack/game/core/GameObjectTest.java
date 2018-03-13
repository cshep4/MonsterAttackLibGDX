package com.cshep4.monsterattack.game.core;

import com.cshep4.monsterattack.game.character.Player;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnimationFactory.class)
public class GameObjectTest {
    @Mock
    private Animation animationWrapper;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void changeAnimation_createsNewAnimation() {
        GameObject object = Player.create(0, 0);

        final String sprite = "SPRITE";
        final int cols = 1;
        final int rows = 1;

        object.changeAnimation(sprite, cols, rows);

        verifyStatic(AnimationFactory.class);
        AnimationFactory.createAnimation(cols, rows, sprite);
    }

    @Test
    public void getMidX_returnsObjectCenterX() {
        GameObject object = Player.create(0, 0);

        float expectedResult = object.getWidth()/2;

        assertThat(object.getMidX(), is(expectedResult));
    }

    @Test
    public void getMidY_returnsObjectCenterY() {
        GameObject object = Player.create(0, 0);

        float expectedResult = object.getHeight()/2;

        assertThat(object.getMidY(), is(expectedResult));
    }
}