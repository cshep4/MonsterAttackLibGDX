package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BomberProducerTest {
    private static final int FRAME_COLS = 1;
    private static final int FRAME_ROWS = 1;
    private static final float X = 100;
    private static final float Y = 100;

    @Mock
    private Texture texture;

    @Mock
    private Rectangle rectangle;

    @Mock
    private Application app;

    @Mock
    private Audio audio;

    @Mock
    private Graphics graphics;

    @Mock
    private Sound sound;

    @Mock
    private Files files;

    @Before
    public void init() {
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);
        Gdx.app = app;
        Gdx.audio = audio;
        Gdx.files = files;
        Gdx.graphics = graphics;

        when(graphics.getDeltaTime()).thenReturn(1f);
        doNothing().when(app).log(any(String.class), any(String.class));
        when(audio.newSound(any(FileHandle.class))).thenReturn(sound);
        when(sound.play(any(Float.class))).thenReturn(1L);
        when(files.internal(any(String.class))).thenReturn(new FileHandle(""));
        TextureFactory.setTexture(texture);
    }

    @Test
    public void create() {
        GameObject bomberProducer = BomberProducer.create(X,Y);

        assertThat(bomberProducer instanceof BomberProducer, is(true));
        assertThat(bomberProducer.getX(), is(X));
        assertThat(bomberProducer.getY(), is(Y));
    }
}