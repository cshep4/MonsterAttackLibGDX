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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextureFactory.class)
public class BulletIndicatorTest {
    private static final float BULLET_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
    private static final int NUM_BULLETS = 10;

    private BulletIndicator bulletIndicator;

    @Mock
    private SpriteBatch batch;

    @Mock
    private BitmapFont font;

    @Mock
    private Texture bulletCaseTexture;

    @Mock
    private Player player;

    @Before
    public void init() {
        mockStatic(TextureFactory.class);
        when(TextureFactory.create(any(String.class))).thenReturn(bulletCaseTexture);

        bulletIndicator = new BulletIndicator();
    }

    @Test
    public void update_textDisplaysNumberOfBulletsRemaining() {
        when(player.getNumberOfBullets()).thenReturn(NUM_BULLETS);

        bulletIndicator.update(player);

        assertThat(bulletIndicator.getText(), is(valueOf(NUM_BULLETS)));
    }

    @Test
    public void draw_drawsBulletCaseIconAndText() {
        bulletIndicator.draw(batch, font);

        verify(batch).draw(bulletCaseTexture, 0, 0, BULLET_SIZE, BULLET_SIZE);
        verify(font).draw(batch, bulletIndicator.getText(), BULLET_SIZE, BULLET_SIZE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void draw_exceptionThrownWhenUsingWrongDrawMethod() {
        bulletIndicator.draw(batch);
    }
}