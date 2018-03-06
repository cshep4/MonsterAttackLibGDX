package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;

    @Mock
    private Texture texture;

    @Mock
    private Application app;

    @Mock
    private Files files;

    @Mock
    private Graphics graphics;

    @Before
    public void init() {
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);
        Gdx.app = app;
        Gdx.files = files;
        Gdx.graphics = graphics;

        when(graphics.getDeltaTime()).thenReturn(1f);
        when(files.internal(any(String.class))).thenReturn(new FileHandle(""));
//        TextureFactory.setTexture(texture);
    }

    @Test
    public void update_changesCharacterPositionBasedOnVelocity() {
        Character character = Bomber.create(X_POS, Y_POS, 1);

        character.update();

        assertThat(character.getX(), is(not(X_POS)));
        assertThat(character.getY(), is(not(Y_POS)));
    }

    @Test
    public void kill_setsCharacterHealthToZero() {
        Character character = Bomber.create(X_POS, Y_POS, 1);

        character.kill();

        assertThat(character.getHealth(), is(0));
    }

    @Test
    public void loseLife_decrementsHealth() {
        Character character = Bomber.create(X_POS, Y_POS, 1);

        int beforeHealth = character.getHealth();

        character.loseLife();

        assertThat(character.getHealth(), is(beforeHealth-1));
    }
}