package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class TextureFactoryTest {
    @Mock
    private Application app;

    @Mock
    private Files files;

    @Before
    public void init() {
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
    }

    @Test
    public void create_returnsNullWhenTextureCannotBeLoaded() {
        Gdx.files = files;
        when(files.internal(any(String.class))).thenThrow(GdxRuntimeException.class);

        Texture texture = TextureFactory.create("TEST");

        assertThat(texture, is(nullValue()));
    }
}