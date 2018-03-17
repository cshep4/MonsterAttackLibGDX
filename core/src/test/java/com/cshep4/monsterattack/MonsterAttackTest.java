package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MonsterAttack.class, Sound.class})
public class MonsterAttackTest {
    @Mock
    private BitmapFont font;

    @Mock
    private SpriteBatch batch;

    @Mock
    private MainMenuScreen mainMenuScreen;

    @Mock
    private Graphics graphics;

    private MonsterAttack monsterAttack;

    @Before
    public void init() throws Exception {
        Gdx.graphics = graphics;
        when(graphics.getWidth()).thenReturn(10);
        when(graphics.getHeight()).thenReturn(10);

        mockStatic(Sound.class);

        whenNew(BitmapFont.class).withAnyArguments().thenReturn(font);
        whenNew(SpriteBatch.class).withAnyArguments().thenReturn(batch);
        whenNew(MainMenuScreen.class).withAnyArguments().thenReturn(mainMenuScreen);

        monsterAttack = new MonsterAttack();
    }

    @Test
    public void create_setsScreenToMainMenu() {
        monsterAttack.create();

        assertThat(monsterAttack.font, is(font));
        assertThat(monsterAttack.batch, is(batch));
        assertThat(monsterAttack.getScreen(), is(mainMenuScreen));
    }

    @Test
    public void dispose() {
        monsterAttack.create();
        monsterAttack.dispose();

        verify(font).dispose();
        verify(batch).dispose();
        verify(mainMenuScreen).dispose();
        verifyStatic(Sound.class);
        Sound.dispose();
    }
}