package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.button.PauseButton;
import com.cshep4.monsterattack.game.button.ShootButton;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.core.State;
import com.cshep4.monsterattack.game.factory.CameraFactory;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.indicator.BombIndicator;
import com.cshep4.monsterattack.game.indicator.BulletIndicator;
import com.cshep4.monsterattack.game.indicator.LifeIndicator;
import com.cshep4.monsterattack.game.indicator.PausedIndicator;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;
import com.cshep4.monsterattack.game.indicator.ShieldIndicator;
import com.cshep4.monsterattack.game.input.GameInputProcessor;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.utils.EnemyUtils;
import com.cshep4.monsterattack.game.wrapper.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

import static com.cshep4.monsterattack.game.constants.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.constants.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_START_X;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_START_Y;
import static com.cshep4.monsterattack.game.core.State.GAME_OVER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnEnemy;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnPickup;
import static com.cshep4.monsterattack.game.utils.Utils.HAS_BULLET_SHOT_BOMB;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;
import static com.cshep4.monsterattack.game.utils.Utils.getTextWidth;
import static java.util.Objects.requireNonNull;

@Getter
@Setter
public class GameScreen implements Screen {
    private final MonsterAttack game;
    private final InputAdapter inputProcessor = new GameInputProcessor(this);
    private OrthographicCamera camera;
    private float width;
    private float height;

    // A variable for tracking elapsed time for the animation
    private State state = RUN;

    private Sprite backgroundSprite = new Sprite(requireNonNull(TextureFactory.create(BACKGROUND)));

    private Player player = Player.create(PLAYER_START_X, PLAYER_START_Y);
    private List<RunningEnemy> runningEnemies = new ArrayList<>();
    private List<ProducerEnemy> producerEnemies = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> enemyBullets = new ArrayList<>();
    private List<PickupItem> pickups = new ArrayList<>();

    private BulletIndicator bulletIndicator = new BulletIndicator();
    private BombIndicator bombIndicator = new BombIndicator();
    private LifeIndicator lifeIndicator = new LifeIndicator();
    private ShieldIndicator shieldIndicator = new ShieldIndicator();
    private ScoreIndicator scoreIndicator = new ScoreIndicator();
    private PausedIndicator pausedIndicator;
    private ShootButton shootButton;
    private PauseButton pauseButton;

    public GameScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        camera = CameraFactory.create(false, getScreenXMax(), getScreenYMax());

        // create buttons
        float buttonSize = getScreenXMax() / BUTTON_SIZE_DIVIDER;
        float buttonX = getScreenXMax() - buttonSize;
        float shootButtonY = 0;
        float pauseButtonY = getScreenYMax() - buttonSize;
        shootButton = ShootButton.create(buttonX, shootButtonY, buttonSize, buttonSize);
        pauseButton = PauseButton.create(buttonX, pauseButtonY, buttonSize, buttonSize);

        ScoreIndicator.resetKills();
        pausedIndicator = new PausedIndicator(game.font, this);

        // setup input processor
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        state.updateStateTime(delta); // update time in animation state

        draw();

        if (isGameOver()) {
            state = GAME_OVER;
            state.setGameOverTime();
        }

        switch (state)
        {
            case RUN:
                updateEverything();
                break;
            case PAUSE:
                // wait for input
                break;
            case RESUME:
                state = RUN;
                break;
            case GAME_OVER:
                handleGameOver();
                break;
            default:
                state = RUN;
                break;
        }
    }

    private void handleGameOver() {
        if (state.hasGameOverDelayPassed()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        drawEverything();

        game.batch.end();
    }

    private void drawEverything(){
        backgroundSprite.draw(game.batch);

        drawObject(player);

        runningEnemies.forEach(this::drawObject);
        producerEnemies.forEach(this::drawObject);

        enemyBullets.forEach(this::drawObject);
        playerBullets.forEach(this::drawObject);

        pickups.forEach(this::drawObject);

        drawObject(shootButton);
        drawObject(pauseButton);
        lifeIndicator.draw(game.batch);
        bulletIndicator.draw(game.batch, game.font);
        bombIndicator.draw(game.batch, game.font);
        shieldIndicator.draw(game.batch, game.font);
        scoreIndicator.draw(game.batch, game.font);
        pausedIndicator.draw(game.batch, game.font);
    }

    private void drawObject(GameObject obj) {
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = obj.getAnimation().getKeyFrame(state.getStateTime(), true);
        game.batch.draw(currentFrame, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
    }

    private void updateEverything(){
        player.update();

        runningEnemies.forEach(e -> e.update(player, playerBullets, enemyBullets));
        producerEnemies.forEach(e -> e.update(player, runningEnemies));

        updateBullets();

        pickups.removeIf(p -> p.isPickedUp(player) || p.hasExpired());

        runningEnemies.removeIf(EnemyUtils::enemyKilled);
        producerEnemies.removeIf(EnemyUtils::enemyKilled);

        spawnEnemies();
        spawnPickups();

        bulletIndicator.update(player);
        lifeIndicator.update(player);
        scoreIndicator.update();

        float bulletIndicatorWidth = getTextWidth(game.font, bulletIndicator.getText());
        bombIndicator.update(player, bulletIndicatorWidth);

        float bombIndicatorWidth = getTextWidth(game.font, bombIndicator.getText());
        shieldIndicator.update(player, bulletIndicatorWidth, bombIndicatorWidth);
    }

    private void updateBullets() {
        playerBullets.removeIf(b -> b.update(runningEnemies, producerEnemies) || hasBulletLeftScreen(b));
        enemyBullets.removeIf(b -> b.update(player) || hasBulletLeftScreen(b));

        // remove bomb if it collides with bullet
        if (playerBullets.removeIf(hasPlayerShotBomb)) {
            Sound.playExplode();
        }
    }

    private Predicate<Bullet> hasPlayerShotBomb = playerBullet -> enemyBullets.removeIf(enemyBullet -> HAS_BULLET_SHOT_BOMB.test(playerBullet, enemyBullet));

    private void spawnEnemies() {
        int enemyNumber = runningEnemies.size() + producerEnemies.size();

        Enemy enemy = spawnEnemy(enemyNumber);

        if (enemy == null) {
            return;
        }

        if (enemy instanceof ProducerEnemy) {
            producerEnemies.add((ProducerEnemy) enemy);
        } else {
            runningEnemies.add((RunningEnemy) enemy);
        }
    }

    private void spawnPickups() {
        PickupItem pickup = spawnPickup();

        if (pickup != null) {
            pickups.add(pickup);
        }
    }

    private boolean hasBulletLeftScreen(Bullet bullet) {
        return bullet.getX() > getScreenXMax() || bullet.getX() + bullet.getWidth() < 0;
    }

    private boolean isGameOver() {
        return player.getHealth() <= 0;
    }

    @Override
    public void show() {
        // not implemented
    }

    @Override
    public void resize(int width, int height) {
        // not implemented
    }

    @Override
    public void pause() {
        state = PAUSE;
    }

    @Override
    public void resume() {
        state = RESUME;
    }

    @Override
    public void hide() {
        pause();
    }

    @Override
    public void dispose() {
        player.dispose();

        runningEnemies.forEach(GameObject::dispose);
        producerEnemies.forEach(GameObject::dispose);

        playerBullets.forEach(GameObject::dispose);
        enemyBullets.forEach(GameObject::dispose);

        shootButton.dispose();
        pauseButton.dispose();
    }

}
