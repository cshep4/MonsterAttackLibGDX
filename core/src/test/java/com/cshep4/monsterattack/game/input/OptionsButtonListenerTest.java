package com.cshep4.monsterattack.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.cshep4.monsterattack.GameOverScreen;
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
@PrepareForTest(OptionsButtonListener.class)
public class OptionsButtonListenerTest {
    private MonsterAttack game = new MonsterAttack();
    private OptionsButtonListener optionsButtonListener = new OptionsButtonListener(game);

    @Mock
    private GameOverScreen screen;

    @Mock
    private Graphics graphics;

    @Test
    public void touchUp_setsScreenToOptions() throws Exception {
        Gdx.graphics = graphics;
        when(graphics.getWidth()).thenReturn(10);
        when(graphics.getHeight()).thenReturn(10);

        whenNew(GameOverScreen.class).withAnyArguments().thenReturn(screen);

        optionsButtonListener.touchUp(new InputEvent(), 0, 0, 0, 0);

        assertThat(game.getScreen(), instanceOf(GameOverScreen.class));
    }

    @Test
    public void touchDown_returnsTrue() {
        boolean result = optionsButtonListener.touchDown(new InputEvent(), 0, 0, 0, 0);

        assertThat(result, is(true));
    }
}