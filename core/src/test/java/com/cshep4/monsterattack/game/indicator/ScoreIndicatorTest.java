package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class ScoreIndicatorTest {
    private static final float TEXT_WIDTH = 10;

    private ScoreIndicator scoreIndicator = new ScoreIndicator();

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Player player;

    @Before
    public void init() {
        mockStatic(Utils.class);
        when(Utils.getTextWidth(any(BitmapFont.class), any(String.class))).thenReturn(TEXT_WIDTH);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void update_withPlayerThrowsException() {
        scoreIndicator.update(player);
    }

    @Test
    public void update_textSetWithNumberOfKills() {
        ScoreIndicator.resetKills();
        ScoreIndicator.incrementKills();
        ScoreIndicator.incrementKills();
        ScoreIndicator.incrementKills();

        scoreIndicator.update();

        String expectedText = "Kills: " + 3;
        String actualText = scoreIndicator.getText();

        assertThat(actualText, is(expectedText));
    }

    @Test
    public void draw_drawsTextInCentreOfScreen() {
        final float x = (getScreenXMax() - TEXT_WIDTH) / 2;
        final float y = getScreenYMax();

        scoreIndicator.draw(batch, font);

        verify(font).draw(batch, scoreIndicator.getText(), x, y);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_drawForIconsWillThrowException() {
        scoreIndicator.draw(batch);
    }

    @Test
    public void incrementKills_addsOneToKills() {
        int originalKills = ScoreIndicator.getNumKills();

        ScoreIndicator.incrementKills();

        int newKills = ScoreIndicator.getNumKills();

        assertThat(newKills, is(originalKills + 1));
    }

    @Test
    public void resetKills_setsKillsToZero() {
        ScoreIndicator.resetKills();

        int kills = ScoreIndicator.getNumKills();

        assertThat(kills, is(0));
    }
}