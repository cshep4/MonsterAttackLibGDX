package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextureFactory.class)
public class AnimationFactoryTest {
    private static final int COLS = 5;
    private static final int ROWS = 5;
    private static final int TEXTURE_SIZE = 10;

    @Mock
    private Texture texture;

    @Test
    public void createAnimation_returnsNullWhenTextureNotLoaded() {
        mockStatic(TextureFactory.class);
        Mockito.when(TextureFactory.create(any(String.class))).thenReturn(null);

        Animation animation = AnimationFactory.createAnimation(0,0, "FAKE SPRITE");

        assertThat(animation, is(nullValue()));
    }

    @Test
    public void createAnimation_returnsAnimation() {
        mockStatic(TextureFactory.class);
        Mockito.when(TextureFactory.create(any(String.class))).thenReturn(texture);

        when(texture.getWidth()).thenReturn(TEXTURE_SIZE);
        when(texture.getHeight()).thenReturn(TEXTURE_SIZE);

        Animation animation = AnimationFactory.createAnimation(COLS,ROWS, "REAL SPRITE");

        int expectedFrameNumber = COLS * ROWS;
        int actualFrameNumber = animation.getAnimation().getKeyFrames().length;

        assertThat(animation, is(notNullValue()));
        assertThat(animation.getTexture(), is(texture));
        assertThat(actualFrameNumber, is(expectedFrameNumber));
    }
}