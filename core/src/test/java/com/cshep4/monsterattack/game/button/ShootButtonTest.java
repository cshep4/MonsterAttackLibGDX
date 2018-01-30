package com.cshep4.monsterattack.game.button;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShootButtonTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

    @Mock
    private Texture texture;

    @Mock
    private Application app;


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
        GameObject shootButton = ShootButton.create(X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(shootButton instanceof ShootButton);
        assertEquals(X_POS, shootButton.getRectangle().getX());
        assertEquals(Y_POS, shootButton.getRectangle().getY());
        assertEquals(WIDTH, shootButton.getRectangle().getWidth());
        assertEquals(HEIGHT, shootButton.getRectangle().getHeight());
    }

}