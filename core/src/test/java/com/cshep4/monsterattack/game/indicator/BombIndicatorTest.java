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
import org.mockito.junit.MockitoJUnitRunner;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BombIndicatorTest {
    private static final float INDICATOR_SPRITE_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
    private static final float INDICATOR_SEPARATOR = INDICATOR_SPRITE_SIZE / 2;
    private static final int NO_BOMBS = 0;
    private static final int NUM_BOMBS = 10;
    private static final int LAYOUT_WIDTH = 100;

    private BombIndicator bombIndicator;

    @Mock
    private TextureFactory textureFactory;

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Texture bombTexture;

    @Mock
    private Player player;

    @Before
    public void init() {
        when(textureFactory.create(any(String.class))).thenReturn(bombTexture);
        bombIndicator = new BombIndicator(textureFactory);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void update_throwsUnsupportedOperationException() {
        bombIndicator.update(player);
    }

    @Test
    public void update_textAndPositionIsCorrect() {
        when(player.getNumberOfBombs()).thenReturn(NUM_BOMBS);

        bombIndicator.update(player, LAYOUT_WIDTH);

        float expectedX = INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + LAYOUT_WIDTH;

        assertThat(bombIndicator.getX(), is(expectedX));
        assertThat(bombIndicator.getY(), is(0.0F));
        assertThat(bombIndicator.getText(), is(valueOf(NUM_BOMBS)));
    }

    @Test
    public void draw_doesntExecuteWhenNumberOfBombsIsZero() {
        when(player.getNumberOfBombs()).thenReturn(NO_BOMBS);
        bombIndicator.update(player, LAYOUT_WIDTH);
        bombIndicator.draw(batch, font);

        verify(batch, times(0)).draw(any(Texture.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
        verify(font, times(0)).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test
    public void draw_ExecutedWhenNumberOfBombsIsNotZero() {
        when(player.getNumberOfBombs()).thenReturn(NUM_BOMBS);
        bombIndicator.update(player, LAYOUT_WIDTH);
        bombIndicator.draw(batch, font);

        verify(batch).draw(any(Texture.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
        verify(font).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_exceptionThrownWhenUsingWrongDrawMethod() {
        bombIndicator.draw(batch);
    }
}