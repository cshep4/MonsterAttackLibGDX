package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnimationFactory.class)
public class CharacterTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;

    @Mock
    private Animation animationWrapper;
    @Mock
    private Application app;

    @Mock
    private Graphics graphics;

    @Before
    public void init() {
        Gdx.app = app;
        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);

        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void update_changesCharacterPositionBasedOnVelocity() {
        Character character = Standard.create(X_POS, Y_POS, 1);

        character.update();

        float expectedX = X_POS - ENEMY_SPEED;

        assertThat(character.getX(), is(expectedX));
        assertThat(character.getY(), is(Y_POS));
    }

    @Test
    public void kill_setsCharacterHealthToZero() {
        Character character = Bomber.create(X_POS, Y_POS, 1);

        character.kill();

        assertThat(character.getHealth(), is(0));
    }

    @Test
    public void loseLife_decrementsHealth() {
        Character character = Bomber.create(X_POS, Y_POS, 1);

        int expectedHealth = character.getHealth() - 1;

        character.loseLife();

        assertThat(character.getHealth(), is(expectedHealth));
    }

    @Test
    public void getXVelByDeltaTime_returnsXVelBasedOnDeltaTime() {
        Character character = Standard.create(X_POS, Y_POS, 1);

        float xVel = character.getXVelByDeltaTime();

        assertThat(character.getXVel(), is(xVel));
    }

    @Test
    public void getYVelByDeltaTime_returnsYVelBasedOnDeltaTime() {
        Character character = Standard.create(X_POS, Y_POS, 1);

        float yVel = character.getYVelByDeltaTime();

        assertThat(character.getYVel(), is(yVel));
    }
}