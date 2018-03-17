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

import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextureFactory.class)
public class ShieldIndicatorTest {
    private static final float INDICATOR_SPRITE_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
    private static final float INDICATOR_SEPARATOR = INDICATOR_SPRITE_SIZE / 2;
    private static final int SHIELD_TIME = 10;
    private static final int NO_BOMBS = 0;
    private static final int HAS_BOMBS = 10;
    private static final int LAYOUT_WIDTH = 100;

    private ShieldIndicator shieldIndicator;

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Texture shieldTexture;

    @Mock
    private Player player;

    @Before
    public void init() {
        mockStatic(TextureFactory.class);
        when(TextureFactory.create(any(String.class))).thenReturn(shieldTexture);

        shieldIndicator = new ShieldIndicator();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void update_throwsUnsupportedOperationException() {
        shieldIndicator.update(player);
    }

    @Test
    public void update_whenPlayerHasNoBombsPositionedCorrectlyWithText() {
        when(player.getRemainingShieldTime()).thenReturn(SHIELD_TIME);
        when(player.getNumberOfBombs()).thenReturn(NO_BOMBS);

        shieldIndicator.update(player, LAYOUT_WIDTH, LAYOUT_WIDTH);

        float expectedX = INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + LAYOUT_WIDTH;

        assertThat(shieldIndicator.getX(), is(expectedX));
        assertThat(shieldIndicator.getY(), is(0.0F));
        assertThat(shieldIndicator.getText(), is(valueOf(SHIELD_TIME)));
    }

    @Test
    public void update_whenPlayerHasBombsPositionedCorrectlyWithText() {
        when(player.getRemainingShieldTime()).thenReturn(SHIELD_TIME);
        when(player.getNumberOfBombs()).thenReturn(HAS_BOMBS);

        shieldIndicator.update(player, LAYOUT_WIDTH, LAYOUT_WIDTH);

        float expectedX = INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + LAYOUT_WIDTH + INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + LAYOUT_WIDTH;

        assertThat(shieldIndicator.getX(), is(expectedX));
        assertThat(shieldIndicator.getY(), is(0.0F));
        assertThat(shieldIndicator.getText(), is(valueOf(SHIELD_TIME)));
    }

    @Test
    public void draw_doesntExecuteWhenShieldTimeIsZero() {
        when(player.getRemainingShieldTime()).thenReturn(0);
        shieldIndicator.update(player, LAYOUT_WIDTH, LAYOUT_WIDTH);
        shieldIndicator.draw(batch, font);

        verify(batch, times(0)).draw(any(Texture.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
        verify(font, times(0)).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test
    public void draw_ExecutedWhenShieldTimeIsNotZero() {
        when(player.getRemainingShieldTime()).thenReturn(SHIELD_TIME);
        shieldIndicator.update(player, LAYOUT_WIDTH, LAYOUT_WIDTH);
        shieldIndicator.draw(batch, font);

        verify(batch).draw(any(Texture.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
        verify(font).draw(any(SpriteBatch.class), any(String.class), any(Float.class), any(Float.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_exceptionThrownWhenUsingWrongDrawMethod() {
        shieldIndicator.draw(batch);
    }
}