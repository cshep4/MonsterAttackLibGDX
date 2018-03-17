package com.cshep4.monsterattack.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.MonsterAttack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PlayButtonListener.class)
public class PlayButtonListenerTest {
    private MonsterAttack game = new MonsterAttack();
    private PlayButtonListener playButtonListener = new PlayButtonListener(game);

    @Mock
    private GameScreen screen;

    @Mock
    private Graphics graphics;

    @Test
    public void touchUp_setsScreenToGame() throws Exception {
        Gdx.graphics = graphics;
        when(graphics.getWidth()).thenReturn(10);
        when(graphics.getHeight()).thenReturn(10);

        whenNew(GameScreen.class).withAnyArguments().thenReturn(screen);

        playButtonListener.touchUp(new InputEvent(), 0, 0, 0, 0);

        assertThat(game.getScreen(), instanceOf(GameScreen.class));
    }

    @Test
    public void touchDown_returnsTrue() {
        boolean result = playButtonListener.touchDown(new InputEvent(), 0, 0, 0, 0);

        assertThat(result, is(true));
    }
}