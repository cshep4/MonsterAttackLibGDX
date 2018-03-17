package com.cshep4.monsterattack.game.wrapper;

import com.badlogic.gdx.graphics.Texture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AnimationTest {
    @Mock
    private Texture texture;

    @Mock
    private com.badlogic.gdx.graphics.g2d.Animation animation;

    @InjectMocks
    private Animation animationWrapper;

    @Test
    public void dispose_disposesTexture() {
        animationWrapper.dispose();

        verify(texture).dispose();
    }
}