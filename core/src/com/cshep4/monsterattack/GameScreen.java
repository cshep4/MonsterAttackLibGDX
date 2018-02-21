package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.button.PauseButton;
import com.cshep4.monsterattack.game.button.ShootButton;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.core.InputProcessing;
import com.cshep4.monsterattack.game.core.State;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.pickup.BulletCase;
import com.cshep4.monsterattack.game.pickup.PickupItem;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

import static com.cshep4.monsterattack.game.constants.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.constants.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.LIFE;
import static com.cshep4.monsterattack.game.core.State.GAME_OVER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnEnemies;
import static com.cshep4.monsterattack.game.utils.SpawnUtils.spawnPickups;
import static java.lang.String.valueOf;

@Data
public class GameScreen implements Screen {
    private static final int PLAYER_START_X = 50;
    private static final int PLAYER_START_Y = 50;
    private static final Texture LIFE_TEXTURE = TextureFactory.create(LIFE);

    private final MonsterAttack game;
    private static float screenXMax = 450;
    private static float screenYMax = 0;
    private OrthographicCamera camera;
    private float width;
    private float height;

    @Getter
    private static int numKills;

    // A variable for tracking elapsed time for the animation
    private float stateTime;
    private State state = RUN;
    private boolean justPaused = false;

    private int pressDownPointer = -1;
    private long gameOverTime = 0;

    private static final int GAME_OVER_DELAY = 1000;
    private Player player = Player.create(PLAYER_START_X, PLAYER_START_Y);

    private List<RunningEnemy> runningEnemies = new ArrayList<>();
    private List<ProducerEnemy> producerEnemies = new ArrayList<>();

    private List<Bullet> playerBullets = new ArrayList<>();
	private List<Bullet> enemyBullets = new ArrayList<>();

    private ShootButton shootButton;
    private PauseButton pauseButton;
    private BulletCase bulletIndicator;

    private List<PickupItem> pickups = new ArrayList<>();

    private Sprite backgroundSprite;

    private float destinationX;
    private float destinationY;
    private boolean playerMoving;

    public GameScreen(final MonsterAttack game) {
        this.game = game;
        game.font.setColor(Color.BLACK);

        // create the camera and make sure it looks the same across all devices
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        setScreenYMax(width, height);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);

        Texture backgroundTexture = TextureFactory.create(BACKGROUND);
        backgroundSprite = new Sprite(backgroundTexture);

        float buttonSize = screenXMax / BUTTON_SIZE_DIVIDER;
        float shootButtonX = screenXMax - buttonSize;
        float shootButtonY = 0;
        shootButton = ShootButton.create(shootButtonX, shootButtonY, buttonSize, buttonSize);
        float pauseButtonX = screenXMax - buttonSize;
        float pauseButtonY = screenYMax - buttonSize;
        pauseButton = PauseButton.create(pauseButtonX, pauseButtonY, buttonSize, buttonSize);

        float bulletSize = screenXMax / INDICATOR_SIZE_DIVIDER;
        bulletIndicator = BulletCase.create(0, 0, bulletSize, bulletSize);

        stateTime = 0f;

        setupNewGame();
    }

    private static void setupNewGame() {
        numKills = 0;
    }

    @Override
    public void render(float delta) {
        draw();

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
        player.getTexture().dispose();

        runningEnemies.forEach(enemy -> enemy.getTexture().dispose());
        producerEnemies.forEach(enemy -> enemy.getTexture().dispose());

        playerBullets.forEach(bullet -> bullet.getTexture().dispose());
        enemyBullets.forEach(bullet -> bullet.getTexture().dispose());

        shootButton.getTexture().dispose();
        pauseButton.getTexture().dispose();
    }

    private void draw() {
        processUserInput();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        updateStateTime(); // update time in animation state

        game.batch.begin();

        backgroundSprite.draw(game.batch);

        drawEverything();

        game.batch.end();

        if (isGameOver()) {
            state = GAME_OVER;
            if (gameOverTime == 0) {
                gameOverTime = System.currentTimeMillis();
            }
        }
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
        drawLives();
        drawObject(bulletIndicator);
        drawBulletNumber();
        drawKillNumber();

        if (state == PAUSE) {
            drawPausedMessage();
        }
    }

    private void drawObject(GameObject obj) {
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = obj.getAnimation().getKeyFrame(stateTime, true);

        game.batch.draw(currentFrame, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
    }

    private void drawBulletNumber() {
        String bulletNumber = valueOf(player.getNumberOfBullets());
        float x = bulletIndicator.getWidth() + bulletIndicator.getX();
        float y = bulletIndicator.getHeight();
        game.font.draw(game.batch, bulletNumber, x, y);
    }

    private void drawKillNumber() {
        String bulletNumber = "Kills: " + numKills;

        final GlyphLayout layout = new GlyphLayout(game.font, bulletNumber);
        final float x = (screenXMax - layout.width) / 2;
        final float y = screenYMax;

        game.font.draw(game.batch, layout, x, y);
    }

    private void drawPausedMessage() {
        String pausedText = "PAUSED";

        GlyphLayout pauseLayout = new GlyphLayout(game.font, pausedText);
        float x = (screenXMax - pauseLayout.width) / 2;
        float y = (screenYMax - pauseLayout.height) / 2;

        game.font.draw(game.batch, pausedText, x, y);
    }

    private void drawLives() {
        for (int i=0; i<player.getHealth(); i++) {
            float size = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
            float x = size * i;
            float y = getScreenYMax() - size;

            game.batch.draw(LIFE_TEXTURE, x, y, size, size);
        }
    }

    private void updateStateTime() {
        if (state == RUN) {
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }
    }

    private void updateEverything(){
        if (playerMoving) {
            player.move(destinationX, destinationY);
        }

        player.update();

        runningEnemies.forEach(e -> e.update(player, playerBullets, enemyBullets));
        producerEnemies.forEach(e -> e.update(player, runningEnemies));

        updateBullets();

        pickups.removeIf(p -> p.isPickedUp(player) || p.hasExpired());

        runningEnemies.removeIf(GameScreen::enemyDead);
        producerEnemies.removeIf(GameScreen::enemyDead);

        spawnEnemies(runningEnemies, producerEnemies);

        spawnPickups(pickups);
    }

    private static boolean enemyDead(Character enemy) {
        if (enemy.getHealth() <= 0) {
            numKills++;
            return true;
        }
        return false;
    }

    private void updateBullets() {
        playerBullets.removeIf(b -> (b.update(runningEnemies, producerEnemies) || b.getX() > screenXMax));
        enemyBullets.removeIf(b -> (b.update(player) || b.getX() + b.getWidth() < 0));
    }

    public void shoot() {
        if (player.getNumberOfBullets() > 0) {
            playerBullets.add(player.shoot());
        }
    }

    private boolean isGameOver() {
        return player.getHealth() <= 0;
    }

    private void processUserInput(){
        Gdx.input.setInputProcessor(new InputProcessing(this));
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
