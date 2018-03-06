package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BulletTest {
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

    @Mock
    private Graphics graphics;

    @Mock
    private Player player;

    @Mock
    private RunningEnemy runningEnemy;

    @Before
    public void init() {
        Gdx.app = app;
        Gdx.files = files;
        Gdx.graphics = graphics;

        //Texture mocking
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);
        doNothing().when(app).log(any(String.class), any(String.class));
        when(files.internal(any(String.class))).thenReturn(new FileHandle(""));
//        TextureFactory.setTexture(texture);

        //Audio mocking
        Gdx.audio = audio;
        when(audio.newSound(any(FileHandle.class))).thenReturn(sound);
        when(sound.play(any(Float.class))).thenReturn(1L);

        //Graphics time mocking
        when(graphics.getDeltaTime()).thenReturn(1f);
    }

    @Test
    public void create_player() throws Exception {
        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bullet instanceof Bullet);
        assertEquals(X_POS, bullet.getX());
        assertEquals(Y_POS, bullet.getY());
        assertEquals(WIDTH, bullet.getWidth());
        assertEquals(HEIGHT, bullet.getHeight());
        assertEquals(BULLET_SPEED, bullet.getXVel());
    }

    @Test
    public void create_enemy() throws Exception {
        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bullet instanceof Bullet);
        assertEquals(X_POS, bullet.getX());
        assertEquals(Y_POS, bullet.getY());
        assertEquals(WIDTH, bullet.getWidth());
        assertEquals(HEIGHT, bullet.getHeight());
        assertEquals(-BULLET_SPEED, bullet.getXVel());
    }

    @Test
    public void update_enemyCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        List<RunningEnemy> runningEnemies = Arrays.asList(Standard.create(xPos, Y_POS, 1));
        List<ProducerEnemy> producerEnemyList = Collections.emptyList();

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertEquals(xPos, bullet.getX());
        assertTrue(result);
    }

    @Test
    public void update_enemyProducerCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        List<RunningEnemy> runningEnemies = Collections.emptyList();
        List<ProducerEnemy> producerEnemyList = Arrays.asList(StandardProducer.create(xPos, Y_POS));

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertEquals(xPos, bullet.getX());
        assertTrue(result);
    }

    @Test
    public void update_enemyNotCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);
        List<RunningEnemy> runningEnemies = Arrays.asList(Standard.create(1000000, 100000, 1));
        List<ProducerEnemy> producerEnemyList = Arrays.asList(StandardProducer.create(1000000, 100000));

        boolean result = bullet.update(runningEnemies, producerEnemyList);

        assertEquals(xPos, bullet.getX());
        assertFalse(result);
    }

    @Test
    public void update_playerCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(xPos, Y_POS);

        boolean result = bullet.update(player);

        assertEquals(xPos, bullet.getX());
        assertTrue(result);
    }

    @Test
    public void update_playerNotCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(ENEMY, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(1000000, 1000000);

        boolean result = bullet.update(player);

        assertEquals(xPos, bullet.getX());
        assertFalse(result);
    }

    @Test
    public void testCollisionSound() throws Exception {
        Bullet bullet = Bullet.create(PLAYER, X_POS, Y_POS, WIDTH, HEIGHT);

        bullet.collisionSound();

        verify(sound).play(1.0f);
    }

}