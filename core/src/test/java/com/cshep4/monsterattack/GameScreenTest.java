package com.cshep4.monsterattack;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.character.StandardProducer;
import com.cshep4.monsterattack.game.core.State;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.factory.CameraFactory;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.indicator.BombIndicator;
import com.cshep4.monsterattack.game.indicator.BulletIndicator;
import com.cshep4.monsterattack.game.indicator.LifeIndicator;
import com.cshep4.monsterattack.game.indicator.PausedIndicator;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;
import com.cshep4.monsterattack.game.indicator.ShieldIndicator;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.utils.SpawnUtils;
import com.cshep4.monsterattack.game.utils.Utils;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.cshep4.monsterattack.game.constants.Constants.GAME_OVER_DELAY;
import static com.cshep4.monsterattack.game.constants.Constants.SCREEN_X_MAX;
import static com.cshep4.monsterattack.game.core.CharacterType.ENEMY;
import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static com.cshep4.monsterattack.game.core.State.GAME_OVER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, TextureFactory.class, CameraFactory.class, Utils.class, Sound.class, SpawnUtils.class})
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

    @Mock
    private LifeIndicator lifeIndicator;

    @Mock
    private BulletIndicator bulletIndicator;

    @Mock
    private BombIndicator bombIndicator;

    @Mock
    private ShieldIndicator shieldIndicator;

    @Mock
    private ScoreIndicator scoreIndicator;

    @Mock
    private PausedIndicator pausedIndicator;

    @Mock
    private Player player;

    @Mock
    private RunningEnemy runningEnemy;

    @Mock
    private ProducerEnemy producerEnemy;

    @Mock
    private Bullet playerBullet;

    @Mock
    private Bullet enemyBullet;

    @Mock
    private Bomb playerBomb;

    @Mock
    private Bomb enemyBomb;

    @Mock
    private PickupItem pickupItem;

    @Mock
    private Sprite backgroundSprite;

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
        mockStatic(Sound.class);
        mockStatic(SpawnUtils.class);

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

        gameScreen.setLifeIndicator(lifeIndicator);
        gameScreen.setBulletIndicator(bulletIndicator);
        gameScreen.setBombIndicator(bombIndicator);
        gameScreen.setShieldIndicator(shieldIndicator);
        gameScreen.setScoreIndicator(scoreIndicator);
        gameScreen.setPausedIndicator(pausedIndicator);

        gameScreen.setBackgroundSprite(backgroundSprite);

        gameScreen.setPlayer(player);
        when(player.getAnimation()).thenReturn(animation);
        when(player.getHealth()).thenReturn(5);

        gameScreen.setRunningEnemies(new ArrayList<>(Collections.singletonList(runningEnemy)));
        when(runningEnemy.getAnimation()).thenReturn(animation);
        when(runningEnemy.getHealth()).thenReturn(1);

        gameScreen.setProducerEnemies(new ArrayList<>(Collections.singletonList(producerEnemy)));
        when(producerEnemy.getAnimation()).thenReturn(animation);
        when(producerEnemy.getHealth()).thenReturn(1);

        gameScreen.setPlayerBullets(new ArrayList<>(Arrays.asList(playerBomb, playerBullet)));
        when(playerBomb.getAnimation()).thenReturn(animation);
        when(playerBullet.getAnimation()).thenReturn(animation);

        gameScreen.setEnemyBullets(new ArrayList<>(Arrays.asList(enemyBomb, enemyBullet)));
        when(enemyBomb.getAnimation()).thenReturn(animation);
        when(enemyBullet.getAnimation()).thenReturn(animation);

        gameScreen.setPickups(new ArrayList<>(Collections.singletonList(pickupItem)));
        when(pickupItem.getAnimation()).thenReturn(animation);
    }

    @Test
    public void render_drawsEverything() {
        gameScreen.render(1f);

        verify(gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        verify(batch).begin();
        verify(backgroundSprite).draw(batch);
        verify(batch, atLeast(3)).draw(any(TextureRegion.class), any(Float.class), any(Float.class), any(Float.class), any(Float.class));
        verify(lifeIndicator).draw(batch);
        verify(bulletIndicator).draw(batch,font);
        verify(bombIndicator).draw(batch,font);
        verify(shieldIndicator).draw(batch,font);
        verify(scoreIndicator).draw(batch,font);
        verify(pausedIndicator).draw(batch,font);
        verify(batch).end();
    }

    @Test
    public void render_setsGameOverStateAndSetsGameOverTimeIfPlayerIsDead() {
        when(player.getHealth()).thenReturn(0);

        gameScreen.render(1f);

        assertThat(gameScreen.getState(), is(GAME_OVER));
        assertThat(gameScreen.getState().getGameOverTime(), not(0));
    }

    @Test
    public void render_runStateCallsUpdateMethods() {
        gameScreen.render(1f);

        verify(lifeIndicator).update(gameScreen.getPlayer());
        verify(bulletIndicator).update(gameScreen.getPlayer());
        verify(bombIndicator).update(any(Player.class), any(Float.class));
        verify(shieldIndicator).update(any(Player.class), any(Float.class), any(Float.class));
        verify(scoreIndicator).update();
        verify(player).update();
        verify(runningEnemy).update(player, gameScreen.getPlayerBullets(), gameScreen.getEnemyBullets());
        verify(producerEnemy).update(player, gameScreen.getRunningEnemies());
        verify(enemyBomb).update(player);
        verify(enemyBullet).update(player);
        verify(playerBomb).update(gameScreen.getRunningEnemies(), gameScreen.getProducerEnemies());
        verify(playerBullet).update(gameScreen.getRunningEnemies(), gameScreen.getProducerEnemies());
    }

    @Test
    public void render_runStateRemovesBulletsIfTheyCollideWithPlayerOrEnemy() {
        when(playerBullet.update(gameScreen.getRunningEnemies(), gameScreen.getProducerEnemies())).thenReturn(true);
        when(enemyBullet.update(player)).thenReturn(true);

        int expectedPlayerBullets = gameScreen.getPlayerBullets().size() - 1;
        int expectedEnemyBullets = gameScreen.getEnemyBullets().size() - 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getPlayerBullets().size(), is(expectedPlayerBullets));
        assertThat(gameScreen.getEnemyBullets().size(), is(expectedEnemyBullets));
    }

    @Test
    public void render_runStateRemovesBulletsIfTheyLeaveScreen() {
        when(playerBullet.getX()).thenReturn(SCREEN_X_MAX + 1);
        when(enemyBullet.getX()).thenReturn(-1000f);

        int expectedPlayerBullets = gameScreen.getPlayerBullets().size() - 1;
        int expectedEnemyBullets = gameScreen.getEnemyBullets().size() - 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getPlayerBullets().size(), is(expectedPlayerBullets));
        assertThat(gameScreen.getEnemyBullets().size(), is(expectedEnemyBullets));
    }

    @Test
    public void render_runStateRemovesBulletsAndBombsIfTheyCollideTogether() {
        when(Utils.hasCollided(any(Bullet.class), any(Bullet.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bullet.class), any(Bomb.class))).thenReturn(true);
        when(Utils.hasCollided(any(Bomb.class), any(Bomb.class))).thenReturn(false);

        int expectedPlayerBullets = gameScreen.getPlayerBullets().size() - 1;
        int expectedEnemyBullets = gameScreen.getEnemyBullets().size() - 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getPlayerBullets().size(), is(expectedPlayerBullets));
        assertThat(gameScreen.getEnemyBullets().size(), is(expectedEnemyBullets));
    }

    @Test
    public void render_runStateDoesNotRemoveTwoBulletsIfTheyCollideTogether() {
        when(Utils.hasCollided(any(Bullet.class), any(Bullet.class))).thenReturn(true);
        when(Utils.hasCollided(any(Bomb.class), any(Bullet.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bullet.class), any(Bomb.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bomb.class), any(Bomb.class))).thenReturn(false);

        int expectedPlayerBullets = gameScreen.getPlayerBullets().size();
        int expectedEnemyBullets = gameScreen.getEnemyBullets().size();

        gameScreen.render(1f);

        assertThat(gameScreen.getPlayerBullets().size(), is(expectedPlayerBullets));
        assertThat(gameScreen.getEnemyBullets().size(), is(expectedEnemyBullets));
    }

    @Test
    public void render_runStateRemovesBombsIfTheyCollideTogether() {
        when(Utils.hasCollided(any(Bullet.class), any(Bullet.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bomb.class), any(Bullet.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bullet.class), any(Bomb.class))).thenReturn(false);
        when(Utils.hasCollided(any(Bomb.class), any(Bomb.class))).thenReturn(true);

        int expectedPlayerBullets = gameScreen.getPlayerBullets().size() - 1;
        int expectedEnemyBullets = gameScreen.getEnemyBullets().size() - 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getPlayerBullets().size(), is(expectedPlayerBullets));
        assertThat(gameScreen.getEnemyBullets().size(), is(expectedEnemyBullets));
    }

    @Test
    public void render_runStateRemovesPickupsIfTheyArePickedUp() {
        when(pickupItem.isPickedUp(player)).thenReturn(true);
        when(pickupItem.hasExpired()).thenReturn(false);

        gameScreen.render(1f);

        assertThat(gameScreen.getPickups().isEmpty(), is(true));
    }

    @Test
    public void render_runStateRemovesPickupsIfTheyExpire() {
        when(pickupItem.isPickedUp(player)).thenReturn(false);
        when(pickupItem.hasExpired()).thenReturn(true);

        gameScreen.render(1f);

        assertThat(gameScreen.getPickups().isEmpty(), is(true));
    }

    @Test
    public void render_runStateRemovesEnemiesIfTheyAreKilled() {
        when(runningEnemy.getHealth()).thenReturn(0);
        when(producerEnemy.getHealth()).thenReturn(0);

        gameScreen.render(1f);

        assertThat(gameScreen.getRunningEnemies().isEmpty(), is(true));
        assertThat(gameScreen.getProducerEnemies().isEmpty(), is(true));
    }

    @Test
    public void render_runStateSpawnsRunningEnemies() {
        RunningEnemy runningEnemy = mock(RunningEnemy.class);
        when(SpawnUtils.spawnEnemy(any(Integer.class))).thenReturn(runningEnemy);

        int expectedNumberOfRunningEnemies = gameScreen.getRunningEnemies().size() + 1;
        int expectedNumberOfProducerEnemies = gameScreen.getProducerEnemies().size();

        gameScreen.render(1f);

        assertThat(gameScreen.getRunningEnemies().contains(runningEnemy), is(true));
        assertThat(gameScreen.getRunningEnemies().size(), is(expectedNumberOfRunningEnemies));
        assertThat(gameScreen.getProducerEnemies().size(), is(expectedNumberOfProducerEnemies));
    }

    @Test
    public void render_runStateSpawnsProducerEnemies() {
        ProducerEnemy producerEnemy = mock(ProducerEnemy.class);
        when(SpawnUtils.spawnEnemy(any(Integer.class))).thenReturn(producerEnemy);

        int expectedNumberOfRunningEnemies = gameScreen.getRunningEnemies().size();
        int expectedNumberOfProducerEnemies = gameScreen.getProducerEnemies().size() + 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getProducerEnemies().contains(producerEnemy), is(true));
        assertThat(gameScreen.getRunningEnemies().size(), is(expectedNumberOfRunningEnemies));
        assertThat(gameScreen.getProducerEnemies().size(), is(expectedNumberOfProducerEnemies));
    }

    @Test
    public void render_runStateDoesNotAddEnemiesIfNoneSpawned() {
        when(SpawnUtils.spawnEnemy(any(Integer.class))).thenReturn(null);

        int expectedNumberOfRunningEnemies = gameScreen.getRunningEnemies().size();
        int expectedNumberOfProducerEnemies = gameScreen.getProducerEnemies().size();

        gameScreen.render(1f);

        assertThat(gameScreen.getRunningEnemies().size(), is(expectedNumberOfRunningEnemies));
        assertThat(gameScreen.getProducerEnemies().size(), is(expectedNumberOfProducerEnemies));
    }

    @Test
    public void render_runStateSpawnsPickups() {
        PickupItem pickup = mock(PickupItem.class);
        when(SpawnUtils.spawnPickup()).thenReturn(pickup);

        int expectedNumberOfPickups = gameScreen.getPickups().size() + 1;

        gameScreen.render(1f);

        assertThat(gameScreen.getPickups().contains(pickup), is(true));
        assertThat(gameScreen.getPickups().size(), is(expectedNumberOfPickups));
    }

    @Test
    public void render_runStateDoesNotAddPickupIfNoneSpawned() {
        when(SpawnUtils.spawnPickup()).thenReturn(null);

        int expectedNumberOfPickups = gameScreen.getPickups().size();

        gameScreen.render(1f);

        assertThat(gameScreen.getPickups().size(), is(expectedNumberOfPickups));
    }

    @Test
    public void render_pauseStateWaits() {
        gameScreen.setState(PAUSE);

        gameScreen.render(1f);

        assertThat(gameScreen.getState(), is(PAUSE));
    }

    @Test
    public void render_resumeStateStartsGameAgain() {
        gameScreen.setState(RESUME);

        gameScreen.render(1f);

        assertThat(gameScreen.getState(), is(RUN));
    }

    @Test
    public void render_gameOverStateSetsScreenToGameOverIfDelayHasPassed() throws Exception {
        gameScreen.setState(GAME_OVER);

        //set game over time to current time - max delay to simulate delay passing
        long gameOverTime = currentTimeMillis() - GAME_OVER_DELAY - 1;
        Field f = State.class.getDeclaredField("gameOverTime");
        f.setAccessible(true);
        f.set(gameScreen.getState(), gameOverTime);
        f.setAccessible(false);

        gameScreen.render(1f);

        assertThat(gameScreen.getState(), is(GAME_OVER));
        assertThat(gameScreen.getGame().getScreen(), instanceOf(GameOverScreen.class));
    }

    @Test
    public void render_gameOverStateDoesNothingIfDelayHasNotPassed() throws Exception {
        gameScreen.setState(GAME_OVER);

        //set game over time to current time + max delay to simulate delay not passing
        long gameOverTime = currentTimeMillis() + GAME_OVER_DELAY;
        Field f = State.class.getDeclaredField("gameOverTime");
        f.setAccessible(true);
        f.set(gameScreen.getState(), gameOverTime);
        f.setAccessible(false);

        gameScreen.render(1f);

        assertThat(gameScreen.getState(), is(GAME_OVER));
        assertThat(gameScreen.getGame().getScreen(), not(instanceOf(GameScreen.class)));
    }

    @Test
    public void show_doesNothing() {
        gameScreen.show();
    }

    @Test
    public void resize_doesNothing() {
        gameScreen.resize(10, 10);
    }

    @Test
    public void pause_setsStateToPause() {
        gameScreen.pause();

        assertThat(gameScreen.getState(), is(PAUSE));
    }

    @Test
    public void resume_setsStateToResume() {
        gameScreen.resume();

        assertThat(gameScreen.getState(), is(RESUME));
    }

    @Test
    public void hide_setsStateToPause() {
        gameScreen.hide();

        assertThat(gameScreen.getState(), is(PAUSE));
    }

    @Test
    public void dispose_destroysAllTextures() {
        gameScreen.setPlayerBullets(Collections.singletonList(Bullet.create(PLAYER, X_POS, Y_POS, 10, 10)));
        gameScreen.setEnemyBullets(Collections.singletonList(Bullet.create(ENEMY, X_POS, Y_POS, 10, 10)));
        gameScreen.setRunningEnemies(Collections.singletonList(Standard.create(X_POS, Y_POS, 1)));
        gameScreen.setProducerEnemies(Collections.singletonList(StandardProducer.create(X_POS, Y_POS)));
        gameScreen.setPlayer(Player.create(X_POS, Y_POS));

        gameScreen.dispose();

        verify(animationWrapper, times(7)).dispose();
    }
}