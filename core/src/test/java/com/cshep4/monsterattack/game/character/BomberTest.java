package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BomberTest {
    private static final int FRAME_COLS = 1;
    private static final int FRAME_ROWS = 1;
    private static final float X_POS = 100;
    private static final float Y_POS = 100;

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
    public void create() throws Exception {
        GameObject bomber = Bomber.create(X_POS, Y_POS, 1);

        assertTrue(bomber instanceof Bomber);
        assertEquals(X_POS, bomber.getRectangle().getX());
        assertEquals(Y_POS, bomber.getRectangle().getX());
    }

    @Test
    public void update() throws Exception {
        Player player = Player.create(400, 600);
        Bomber bomber = Bomber.create(X_POS, Y_POS, 1);

        final float startDifferenceX = player.getRectangle().getX() - bomber.getRectangle().getX();
        final float startDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        //set mutateTime to be low so that mutation is triggered
        Field f = Bomber.class.getDeclaredField("mutateTime");
        f.setAccessible(true);
        f.set(bomber, 100);

        bomber.update(player);

        final float endDifferenceX = player.getRectangle().getX() - bomber.getRectangle().getX();
        final float endDifferenceY = player.getRectangle().getY() - bomber.getRectangle().getY();

        assertTrue(startDifferenceX > endDifferenceX);
        assertTrue(startDifferenceY > endDifferenceY);

        //check mutation has been triggered and working correctly
        assertEquals(2, bomber.getLevel());
    }

    @Test
    public void mutate_test1() throws Exception {
        int level = 1;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertEquals(bomber.getLevel(), 2);
        assertEquals(bomber.isCanShoot(), true);
        assertEquals(bomber.isCanDodge(), false);
        assertEquals(bomber.isCanShield(), false);
        assertEquals(bomber.isCanShootBombs(), false);
        assertEquals(bomber.getHealth(), 100);
    }

    @Test
    public void mutate_test2() throws Exception {
        int level = 2;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertEquals(bomber.getLevel(), 3);
        assertEquals(bomber.isCanShoot(), true);
        assertEquals(bomber.isCanDodge(), false);
        assertEquals(bomber.isCanShield(), false);
        assertEquals(bomber.isCanShootBombs(), false);
        assertEquals(bomber.getHealth(), 200);
    }

    @Test
    public void mutate_test3() throws Exception {
        int level = 3;
        Bomber bomber = Bomber.create(0, 0, level);

        bomber.mutate();

        assertEquals(bomber.getLevel(), 4);
        assertEquals(bomber.isCanShoot(), true);
        assertEquals(bomber.isCanDodge(), false);
        assertEquals(bomber.isCanShield(), false);
        assertEquals(bomber.isCanShootBombs(), true);
        assertEquals(bomber.getHealth(), 200);
    }

    @Test
    public void changeAnimation() throws Exception {
        Bomber bomber = Bomber.create(0, 0, 1);

        Animation<TextureRegion> oldAnimation = bomber.getAnimation();

        bomber.changeAnimation(ENEMY_SPEED);

        Animation<TextureRegion> newAnimation = bomber.getAnimation();

        assertNotEquals(newAnimation, oldAnimation);
    }

}