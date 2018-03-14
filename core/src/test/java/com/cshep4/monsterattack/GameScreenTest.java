package com.cshep4.monsterattack;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.factory.CameraFactory;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.utils.Utils;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, TextureFactory.class, CameraFactory.class, Utils.class})
public class GameScreenTest {
    private static final float X_POS = 100;
    private static final float Y_POS = 100;
    private static final float TEXT_WIDTH = 10;

    @Mock
    private Animation animationWrapper;

    @Mock
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;

    @Mock
    private Texture texture;

    @Mock
    private OrthographicCamera camera;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    @Mock
    private BitmapFont font;

    @Mock
    private SpriteBatch batch;

    @Mock
    private GL20 gl;

    @Mock
    private Input input;

    @Mock
    private TextureRegion textureRegion;

    private GameScreen gameScreen;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
        mockStatic(TextureFactory.class);
        when(TextureFactory.create(any(String.class))).thenReturn(texture);
        mockStatic(CameraFactory.class);
        when(CameraFactory.create(any(Boolean.class), any(Float.class), any(Float.class))).thenReturn(camera);
        mockStatic(Utils.class);
        when(Utils.getTextWidth(any(BitmapFont.class), any(String.class))).thenReturn(TEXT_WIDTH);

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
        Gdx.gl = gl;
        doNothing().when(gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input = input;

        when(animationWrapper.getAnimation()).thenReturn(animation);
        when(animation.getKeyFrame(any(Float.class),any(Boolean.class))).thenReturn(textureRegion);

        MonsterAttack game = new MonsterAttack();
        game.batch = batch;
        game.font = font;

        gameScreen = new GameScreen(game);
    }

    @Test
    public void render_drawsEverything() {
        gameScreen.render(1f);

        verify(gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        verify(batch).begin();



        verify(batch).end();
    }

    @Test
    public void render_setsGameOverStateAndSetsGameOverTimeIfPlayerIsDead() {
    }

    @Test
    public void render_runStateUpdatesEverything() {
    }

    @Test
    public void render_pauseStateDoesNothing() {
    }

    @Test
    public void render_resumeStateStartsGameAgain() {
    }

    @Test
    public void render_gameOverStateSetsScreenToGameOverIfDelayHasPassed() {
    }

    @Test
    public void render_gameOverStateDoesNothingIfDelayHasNotPassed() {
    }

    @Test
    public void show_doesNothing() {
    }

    @Test
    public void resize_doesNothing() {
    }

    @Test
    public void pause_setsStateToPause() {
    }

    @Test
    public void resume_setsStateToResume() {
    }

    @Test
    public void hide_setsStateToPause() {
    }

    @Test
    public void dispose_destroysAllTextures() {
    }

    @Test
    public void shoot_playerShootsBombIfTheyAreAvailable() {
    }

    @Test
    public void shoot_playerShootsBulletIfTheyAreAvailable() {
    }

    @Test
    public void shoot_playerDoesNotShootIfNothingIsAvailable() {
    }
}