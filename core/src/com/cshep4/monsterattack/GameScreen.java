package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.button.PauseButton;
import com.cshep4.monsterattack.game.button.ShootButton;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.core.InputProcessor;
import com.cshep4.monsterattack.game.core.State;
import com.cshep4.monsterattack.game.factory.CameraFactory;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.indicator.BombIndicator;
import com.cshep4.monsterattack.game.indicator.BulletIndicator;
import com.cshep4.monsterattack.game.indicator.LifeIndicator;
import com.cshep4.monsterattack.game.indicator.PausedIndicator;
import com.cshep4.monsterattack.game.indicator.ScoreIndicator;
import com.cshep4.monsterattack.game.indicator.ShieldIndicator;
import com.cshep4.monsterattack.game.pickup.PickupItem;
import com.cshep4.monsterattack.game.wrapper.Sound;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import static com.cshep4.monsterattack.game.constants.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.constants.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.core.State.GAME_OVER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnEnemies;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnPickups;
import static com.cshep4.monsterattack.game.utils.Utils.getTextWidth;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;

@Data
public class GameScreen implements Screen {
    private static final int PLAYER_START_X = 50;
    private static final int PLAYER_START_Y = 50;
    private static final int GAME_OVER_DELAY = 1000;

    private final MonsterAttack game;
    private final InputAdapter inputProcessor = new InputProcessor(this);
    private static float screenXMax = 450;
    private static float screenYMax;
    private OrthographicCamera camera;
    private float width;
    private float height;

    // A variable for tracking elapsed time for the animation
    private float stateTime = 0f;
    private State state = RUN;
    private long gameOverTime = 0;

    private Texture backgroundTexture = TextureFactory.create(BACKGROUND);
    private Sprite backgroundSprite = new Sprite(backgroundTexture);

    private Player player = Player.create(PLAYER_START_X, PLAYER_START_Y);
    private List<RunningEnemy> runningEnemies = new ArrayList<>();
    private List<ProducerEnemy> producerEnemies = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> enemyBullets = new ArrayList<>();
    private List<PickupItem> pickups = new ArrayList<>();

    private ShootButton shootButton;
    private PauseButton pauseButton;

    private float playerDestinationX;
    private float playerDestinationY;
    private boolean playerMoving;

    private BulletIndicator bulletIndicator = new BulletIndicator();
    private BombIndicator bombIndicator = new BombIndicator();
    private LifeIndicator lifeIndicator = new LifeIndicator();
    private ShieldIndicator shieldIndicator = new ShieldIndicator();
    private ScoreIndicator scoreIndicator = new ScoreIndicator();
    private PausedIndicator pausedIndicator;

    public GameScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        setScreenYMax(width, height);
        camera = CameraFactory.create(false, screenXMax, screenYMax);

        // create buttons
        float buttonSize = screenXMax / BUTTON_SIZE_DIVIDER;
        float buttonX = screenXMax - buttonSize;
        float shootButtonY = 0;
        float pauseButtonY = screenYMax - buttonSize;
        shootButton = ShootButton.create(buttonX, shootButtonY, buttonSize, buttonSize);
        pauseButton = PauseButton.create(buttonX, pauseButtonY, buttonSize, buttonSize);

        // reset number of kills
        setupNewGame();
        pausedIndicator = new PausedIndicator(game.font, this);

        // setup input processor
        Gdx.input.setInputProcessor(inputProcessor);
    }

    private static void setupNewGame() {
        ScoreIndicator.resetKills();
    }

    @Override
    public void render(float delta) {
        draw();

        if (isGameOver()) {
            state = GAME_OVER;
            if (gameOverTime == 0) {
                gameOverTime = System.currentTimeMillis();
            }
        }

        switch (state)
        {
            case RUN:
                updateEverything();
                break;
            case PAUSE:
                // do nothing
                break;
            case RESUME:
                state = RUN;
                break;
            case GAME_OVER:
                if (System.currentTimeMillis() - gameOverTime > GAME_OVER_DELAY) {
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }
                break;
            default:
                state = RUN;
                break;
        }
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

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        updateStateTime(); // update time in animation state

        game.batch.begin();

        backgroundSprite.draw(game.batch);

        drawEverything();

        game.batch.end();
    }

    private void drawEverything(){
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
        TextureRegion currentFrame = obj.getAnimation().getKeyFrame(stateTime, true);
        game.batch.draw(currentFrame, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
    }

    private void updateStateTime() {
        if (state == RUN) {
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }
    }

    private void updateEverything(){
        if (playerMoving) {
            player.move(playerDestinationX, playerDestinationY);
        }

        player.update();

        runningEnemies.forEach(e -> e.update(player, playerBullets, enemyBullets));
        producerEnemies.forEach(e -> e.update(player, runningEnemies));

        updateBullets();

        pickups.removeIf(p -> p.isPickedUp(player) || p.hasExpired());

        runningEnemies.removeIf(GameScreen::enemyKilled);
        producerEnemies.removeIf(GameScreen::enemyKilled);
        spawnEnemies(runningEnemies, producerEnemies);

        spawnPickups(pickups);

        bulletIndicator.update(player);

        float bulletIndicatorWidth = getTextWidth(game.font, bulletIndicator.getText());
        bombIndicator.update(player, bulletIndicatorWidth);

        float bombIndicatorWidth = getTextWidth(game.font, bombIndicator.getText());
        shieldIndicator.update(player, bulletIndicatorWidth, bombIndicatorWidth);
        lifeIndicator.update(player);
        scoreIndicator.update();
    }

    private static boolean enemyKilled(Character enemy) {
        if (enemy.getHealth() <= 0) {
            ScoreIndicator.incrementKills();
            return true;
        }
        return false;
    }

    private void updateBullets() {
        playerBullets.removeIf(b -> b.update(runningEnemies, producerEnemies) || hasBulletLeftScreen(b));
        enemyBullets.removeIf(b -> b.update(player) || hasBulletLeftScreen(b));

        // remove bomb if it collides with bullet
        if (playerBullets.removeIf(pb -> enemyBullets.removeIf(eb -> eb instanceof Bomb && hasCollided(eb, pb)))) {
            Sound.playExplode();
        }
    }

    private boolean hasBulletLeftScreen(Bullet bullet) {
        return bullet.getX() > screenXMax || bullet.getX() + bullet.getWidth() < 0;
    }

    public void shoot() {
        if (player.getNumberOfBombs() > 0) {
            playerBullets.add(player.shootBomb());
            return;
        }

        if (player.getNumberOfBullets() > 0) {
            playerBullets.add(player.shoot());
        }
    }

    private boolean isGameOver() {
        return player.getHealth() <= 0;
    }

    public static float getScreenXMax() {
        return screenXMax;
    }

    public static float getScreenYMax() {
        return screenYMax;
    }

    private static void setScreenYMax(float width, float height) {
        float ratio = width / height;
        screenYMax = screenXMax / ratio;
    }
}
