package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.cshep4.monsterattack.game.core.GameObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.cshep4.monsterattack.game.constants.Constants.PLAYER;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BombTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float WIDTH = 100;
    private static final float HEIGHT = 100;

//    @InjectMocks
//    private Bomb bomb;
//
//    @Mock
//    private AnimationFactory animationFactory;
//
//    @Mock
//    private Animation animationWrapper;
//
//    @Mock
//    private Texture texture;
//
//    @Mock
//    private Animation<TextureRegion> animation;

    @Mock
    private Sound sound;

    @Mock
    private Audio audio;

    @Before
    public void init() {
//        when(animationFactory.createAnimation(any(Integer.class),any(Integer.class),any(String.class))).thenReturn(animationWrapper);
//        when(animationWrapper.getAnimation()).thenReturn(animation);
//        when(animationWrapper.getTexture()).thenReturn(texture);
    }

    @Test
    public void create() throws Exception {
        GameObject bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bomb instanceof Bomb);
        assertEquals(X_POS, bomb.getX());
        assertEquals(Y_POS, bomb.getY());
        assertEquals(WIDTH, bomb.getWidth());
        assertEquals(HEIGHT, bomb.getHeight());
    }

    @Test
    public void collisionSound() throws Exception {
        Bomb bomb = Bomb.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        Gdx.audio = audio;
        when(audio.newSound(any(FileHandle.class))).thenReturn(sound);
        when(sound.play(any(Float.class))).thenReturn(1L);

        bomb.collisionSound();

        verify(sound).play(1.0f);
    }
}