package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class PausedIndicatorTest {
    private static final int NUM_LIVES = 10;
    private static final float TEXT_WIDTH = 10f;

    private PausedIndicator pausedIndicator;

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Player player;

    @Mock
    private GameScreen gameScreen;


    @Before
    public void init() {
        mockStatic(Utils.class);
        when(Utils.getTextWidth(any(BitmapFont.class), any(String.class))).thenReturn(TEXT_WIDTH);
        when(Utils.getTextHeight(any(BitmapFont.class), any(String.class))).thenReturn(TEXT_WIDTH);

        pausedIndicator = new PausedIndicator(font, gameScreen);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void update_updateWillThrowException() {
        pausedIndicator.update(player);
    }

    @Test
    public void draw_drawsTextWhenPaused() {
        when(gameScreen.getState()).thenReturn(PAUSE);

        pausedIndicator.draw(batch, font);

        verify(font).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test
    public void draw_doesntDrawTextWhenNotPaused() {
        when(gameScreen.getState()).thenReturn(RUN);

        pausedIndicator.draw(batch, font);

        verify(font, times(0)).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_drawForIconsWillThrowException() {
        pausedIndicator.draw(batch);
    }
}