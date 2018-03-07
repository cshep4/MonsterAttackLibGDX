package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextureFactory.class)
public class LifeIndicatorTest {
    private static final int NUM_LIVES = 10;

    private LifeIndicator lifeIndicator;

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Texture lifeTexture;

    @Mock
    private Player player;

    @Before
    public void init() {
        mockStatic(TextureFactory.class);
        when(TextureFactory.create(any(String.class))).thenReturn(lifeTexture);

        lifeIndicator = new LifeIndicator();
    }

    @Test
    public void update_instancesSetToPlayersNumberOfLives() {
        when(player.getHealth()).thenReturn(NUM_LIVES);

        lifeIndicator.update(player);

        assertThat(lifeIndicator.getInstances(), is(NUM_LIVES));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_drawWithTextThrowsException() {
        lifeIndicator.draw(batch, font);
    }

    @Test
    public void draw_lifeIconsAreDrawnForEachInstance() {
        when(player.getHealth()).thenReturn(NUM_LIVES);
        lifeIndicator.update(player);

        lifeIndicator.draw(batch);

        verify(batch, times(NUM_LIVES)).draw(any(Texture.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
    }
}