package com.cshep4.monsterattack.game.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.MonsterAttack;
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

import java.util.ArrayList;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, TextureFactory.class, CameraFactory.class, Utils.class})
public class InputProcessorTest {
    private static final float TEXT_WIDTH = 10f;
    private static final int NUM_BULLETS = 10;
    private static final int POINTER = 999;
    private static final int DESTINATION_X = 123;
    private static final int DESTINATION_Y = 123;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Texture texture;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    @Mock
    private MonsterAttack game;

    @Mock
    private OrthographicCamera camera;

    @Mock
    private Input input;

    private InputProcessor inputProcessor;
    private GameScreen gameScreen;
    private float xMultiplier;
    private float yMultiplier;

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
        when(graphics.getWidth()).thenReturn(100);
        when(graphics.getHeight()).thenReturn(100);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));

        Gdx.input = input;

        gameScreen = new GameScreen(game);
        inputProcessor = new InputProcessor(gameScreen);

        xMultiplier = getScreenXMax() / Gdx.graphics.getWidth();
        yMultiplier = getScreenYMax() / Gdx.graphics.getHeight();
    }

    @Test
    public void touchDown_returnsFalseWhenGameIsNotInRunState() {
        gameScreen.setState(PAUSE);

        boolean result = inputProcessor.touchDown(0,0,POINTER,0);

        assertThat(result, is(false));
    }

    @Test
    public void touchDown_gameIsPausedWhenPauseButtonPressed() {
        gameScreen.setState(RUN);

        float buttonSize = getScreenXMax() / BUTTON_SIZE_DIVIDER;
        float pauseButtonX = getScreenXMax() - buttonSize;
        float pauseButtonY = getScreenYMax() - buttonSize;

        int x = (int) (pauseButtonX / xMultiplier);
        int y = (int) (gameScreen.getHeight() - (pauseButtonY / yMultiplier));

        boolean result = inputProcessor.touchDown(x,y,POINTER,0);

        assertThat(gameScreen.getState(), is(PAUSE));
        assertThat(result, is(true));
    }

    @Test
    public void touchDown_playerShootsWhenShootButtonPressed() {
        float buttonSize = getScreenXMax() / BUTTON_SIZE_DIVIDER;
        float pauseButtonX = getScreenXMax() - buttonSize;
        float pauseButtonY = 0;

        gameScreen.getPlayer().setNumberOfBullets(NUM_BULLETS);
        gameScreen.setPlayerBullets(new ArrayList<>());

        int x = (int) (pauseButtonX / xMultiplier);
        int y = (int) (gameScreen.getHeight() - (pauseButtonY / yMultiplier));

        boolean result = inputProcessor.touchDown(x,y,POINTER,0);

        int expectedBullets = NUM_BULLETS - 1;

        assertThat(gameScreen.getPlayer().getNumberOfBullets(), is(expectedBullets));
        assertThat(gameScreen.getPlayerBullets().isEmpty(), is(false));
        assertThat(result, is(true));
    }

    @Test
    public void touchDown_playerStartsMoving() {
        boolean result = inputProcessor.touchDown(0,0,POINTER,0);

        assertThat(inputProcessor.getMovementPointer(), is(POINTER));
        assertThat(gameScreen.isPlayerMoving(), is(true));
        assertThat(result, is(true));
    }

    @Test
    public void touchUp_resumesGameIfPaused() {
        gameScreen.setState(PAUSE);

        boolean result = inputProcessor.touchUp(0,0,POINTER,0);

        assertThat(gameScreen.getState(), is(RESUME));
        assertThat(result, is(true));
    }

    @Test
    public void touchUp_doesNotResumeGameIfTouchUpIsFromPressingPauseButton() {
        float buttonSize = getScreenXMax() / BUTTON_SIZE_DIVIDER;
        float pauseButtonX = getScreenXMax() - buttonSize;
        float pauseButtonY = getScreenYMax() - buttonSize;

        int x = (int) (pauseButtonX / xMultiplier);
        int y = (int) (gameScreen.getHeight() - (pauseButtonY / yMultiplier));

        // press pause button
        inputProcessor.touchDown(x,y,POINTER,0);
        // take finger off pause button
        boolean result = inputProcessor.touchUp(x, y, POINTER,0);

        assertThat(gameScreen.getState(), is(PAUSE));
        assertThat(result, is(true));
    }

    @Test
    public void touchUp_stopPlayerMovingIfSameTouch() {
        inputProcessor.touchDown(0,0,POINTER,0);

        assertThat(inputProcessor.getMovementPointer(), is(POINTER));
        assertThat(gameScreen.isPlayerMoving(), is(true));

        boolean result = inputProcessor.touchUp(0,0, POINTER,0);

        assertThat(inputProcessor.getMovementPointer(), is(-1));
        assertThat(gameScreen.isPlayerMoving(), is(false));
        assertThat(result, is(true));
    }

    @Test
    public void touchUp_doNothingIfNotSameTouch() {
        inputProcessor.touchDown(0,0, POINTER,0);
        boolean result = inputProcessor.touchUp(0,0, POINTER + 1,0);

        assertThat(inputProcessor.getMovementPointer(), is(POINTER));
        assertThat(gameScreen.isPlayerMoving(), is(true));
        assertThat(result, is(true));
    }

    @Test
    public void touchDragged_setPlayerDestinationIfSameTouch() {
        gameScreen.setPlayerDestinationX(0);
        gameScreen.setPlayerDestinationY(0);

        int x = (int) (DESTINATION_X / xMultiplier);
        int y = (int) (gameScreen.getHeight() - (DESTINATION_Y / yMultiplier));

        inputProcessor.touchDown(x, y, POINTER,0);
        boolean result = inputProcessor.touchDragged(x, y, POINTER);

        float expectedDestinationX = x * xMultiplier;
        float expectedDestinationY = yMultiplier * (gameScreen.getHeight() - y);

        assertThat(gameScreen.getPlayerDestinationX(), is(expectedDestinationX));
        assertThat(gameScreen.getPlayerDestinationY(), is(expectedDestinationY));
        assertThat(result, is(true));
    }

    @Test
    public void touchDragged_doNothingIfNotSameTouch() {
        gameScreen.setPlayerDestinationX(0);
        gameScreen.setPlayerDestinationY(0);

        inputProcessor.touchDown(DESTINATION_X, DESTINATION_Y, POINTER,0);
        boolean result = inputProcessor.touchDragged(DESTINATION_X, DESTINATION_Y, POINTER + 1);

        assertThat(gameScreen.getPlayerDestinationX(), is(0f));
        assertThat(gameScreen.getPlayerDestinationY(), is(0f));
        assertThat(result, is(true));
    }
}