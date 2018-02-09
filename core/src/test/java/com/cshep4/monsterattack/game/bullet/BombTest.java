package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.button.PauseButton;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BombTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

    @Mock
    private Texture texture;

    @Mock
    private Application app;

    @Mock
    private Sound sound;

    @Mock
    private Audio audio;

    @Mock
    private Files files;

    @Before
    public void init() {
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);
        Gdx.app = app;
        Gdx.files = files;

        doNothing().when(app).log(any(String.class), any(String.class));
        when(files.internal(any(String.class))).thenReturn(new FileHandle(""));
        TextureFactory.setTexture(texture);
    }

    @Test
    public void create() throws Exception {
        GameObject bomb = Bomb.create(X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bomb instanceof Bomb);
        assertEquals(X_POS, bomb.getX());
        assertEquals(Y_POS, bomb.getY());
        assertEquals(WIDTH, bomb.getWidth());
        assertEquals(HEIGHT, bomb.getHeight());
    }

    @Test
    public void collisionSound() throws Exception {
        Bomb bomb = Bomb.create(X_POS, Y_POS, WIDTH, HEIGHT);

        Gdx.audio = audio;
        when(audio.newSound(any(FileHandle.class))).thenReturn(sound);
        when(sound.play(any(Float.class))).thenReturn(1L);

        bomb.collisionSound();

        verify(sound).play(1.0f);
    }
}