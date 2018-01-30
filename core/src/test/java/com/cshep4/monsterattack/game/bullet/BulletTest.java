package com.cshep4.monsterattack.game.bullet;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    private Enemy enemy;

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
        TextureFactory.setTexture(texture);

        //Audio mocking
        Gdx.audio = audio;
        when(audio.newSound(any(FileHandle.class))).thenReturn(sound);
        when(sound.play(any(Float.class))).thenReturn(1L);

        //Graphics time mocking
        when(graphics.getDeltaTime()).thenReturn(1f);
    }

    @Test
    public void create_player() throws Exception {
        Bullet bullet = Bullet.create(player, X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bullet instanceof Bullet);
        assertEquals(X_POS, bullet.getRectangle().getX());
        assertEquals(Y_POS, bullet.getRectangle().getY());
        assertEquals(WIDTH, bullet.getRectangle().getWidth());
        assertEquals(HEIGHT, bullet.getRectangle().getHeight());
        assertEquals(BULLET_SPEED, bullet.getXVel());
    }

    @Test
    public void create_enemy() throws Exception {
        Bullet bullet = Bullet.create(enemy, X_POS, Y_POS, WIDTH, HEIGHT);

        assertTrue(bullet instanceof Bullet);
        assertEquals(X_POS, bullet.getRectangle().getX());
        assertEquals(Y_POS, bullet.getRectangle().getY());
        assertEquals(WIDTH, bullet.getRectangle().getWidth());
        assertEquals(HEIGHT, bullet.getRectangle().getHeight());
        assertEquals(-BULLET_SPEED, bullet.getXVel());
    }

    @Test
    public void update_enemyCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(player, X_POS, Y_POS, WIDTH, HEIGHT);
        List<Enemy> enemyList = Arrays.asList(Standard.create(xPos, Y_POS, 1));
        List<ProducerEnemy> producerEnemyList = Collections.emptyList();

        boolean result = bullet.update(enemyList, producerEnemyList);

        assertEquals(xPos, bullet.getRectangle().getX());
        assertTrue(result);
    }

    @Test
    public void update_enemyProducerCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(player, X_POS, Y_POS, WIDTH, HEIGHT);
        List<Enemy> enemyList = Collections.emptyList();
        List<ProducerEnemy> producerEnemyList = Arrays.asList(StandardProducer.create(xPos, Y_POS));

        boolean result = bullet.update(enemyList, producerEnemyList);

        assertEquals(xPos, bullet.getRectangle().getX());
        assertTrue(result);
    }

    @Test
    public void update_enemyNotCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS + BULLET_SPEED;

        Bullet bullet = Bullet.create(player, X_POS, Y_POS, WIDTH, HEIGHT);
        List<Enemy> enemyList = Arrays.asList(Standard.create(1000000, 100000, 1));
        List<ProducerEnemy> producerEnemyList = Arrays.asList(StandardProducer.create(1000000, 100000));

        boolean result = bullet.update(enemyList, producerEnemyList);

        assertEquals(xPos, bullet.getRectangle().getX());
        assertFalse(result);
    }

    @Test
    public void update_playerCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(enemy, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(xPos, Y_POS);

        boolean result = bullet.update(player);

        assertEquals(xPos, bullet.getRectangle().getX());
        assertTrue(result);
    }

    @Test
    public void update_playerNotCollided() throws Exception {
        //bullet will update and move to this x position
        float xPos = X_POS - BULLET_SPEED;

        Bullet bullet = Bullet.create(enemy, X_POS, Y_POS, WIDTH, HEIGHT);
        Player player = Player.create(1000000, 1000000);

        boolean result = bullet.update(player);

        assertEquals(xPos, bullet.getRectangle().getX());
        assertFalse(result);
    }

    @Test
    public void testCollisionSound() throws Exception {
        Bullet bullet = Bullet.create(player, X_POS, Y_POS, WIDTH, HEIGHT);

        bullet.collisionSound();

        verify(sound).play(1.0f);
    }

}