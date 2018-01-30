package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.button.PauseButton;
import com.cshep4.monsterattack.game.button.ShootButton;
import com.cshep4.monsterattack.game.character.Enemy;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.core.InputProcessing;
import com.cshep4.monsterattack.game.core.Spawn;
import com.cshep4.monsterattack.game.core.State;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import static com.cshep4.monsterattack.game.constants.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.constants.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.core.State.GAME_OVER;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;

@Data
public class GameScreen implements Screen {
    private static final int PLAYER_START_X = 50;
    private static final int PLAYER_START_Y = 50;

    private final MonsterAttack game;
    private static float screenXMax = 450;
    private static float screenYMax = 0;
    private OrthographicCamera camera;
    private float width;
    private float height;

    // A variable for tracking elapsed time for the animation
    private float stateTime;
    private State state = RUN;
    private boolean justPaused = false;

    private int pressDownPointer = -1;
    private long gameOverTime = 0;

    private static final int GAME_OVER_DELAY = 1000;
    private Player player = Player.create(PLAYER_START_X, PLAYER_START_Y);

    private List<Enemy> enemies = new ArrayList<>();
    private List<ProducerEnemy> producerEnemies = new ArrayList<>();

    private List<Bullet> playerBullets = new ArrayList<>();
	private List<Bullet> enemyBullets = new ArrayList<>();

    private ShootButton shootButton;
    private PauseButton pauseButton;

    private Sprite backgroundSprite;

    public GameScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        setScreenYMax(width, height);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);

        Texture backgroundTexture = TextureFactory.create(BACKGROUND);
        backgroundSprite = new Sprite(backgroundTexture);

        float buttonWidth = screenXMax / BUTTON_SIZE_DIVIDER;
        float buttonHeight = buttonWidth;
        float shootButtonX = screenXMax - buttonWidth;
        float shootButtonY = 0;
        float pauseButtonX = screenXMax - buttonWidth;
        float pauseButtonY = screenYMax - buttonHeight;
        shootButton = ShootButton.create(shootButtonX, shootButtonY, buttonWidth, buttonHeight);
        pauseButton = PauseButton.create(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);

        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
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
        draw();
    }

    private void draw() {
        processUserInput();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        updateStateTime(); // update time in animation state

        game.batch.begin();

        backgroundSprite.draw(game.batch);

        drawEverything();

        if (state == PAUSE) {
            // get text layout height and width and place it in the middle of the screen----------------
            String mainMenuString = "PAUSED";
            GlyphLayout mainMenuLayout = new GlyphLayout(game.font, mainMenuString);
            float mainMenuTextWidth = mainMenuLayout.width;
            float mainMenuTextHeight = mainMenuLayout.height;
            game.font.draw(game.batch, mainMenuString, screenXMax / 2 - (mainMenuTextWidth / 2),
                    screenYMax / 2 - (mainMenuTextHeight / 2));
        }

        game.batch.end();

        if (gameOver()) {
            state = GAME_OVER;
            if (gameOverTime == 0) {
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void updateStateTime() {
        if (state == RUN) {
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }
    }

    @Override
    public void show() {
        // do nothing
    }

    @Override
    public void resize(int width, int height) {
        // do nothing
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
        // do nothing
    }

    @Override
    public void dispose() {
        player.getTexture().dispose();

        enemies.forEach(enemy -> enemy.getTexture().dispose());
        producerEnemies.forEach(enemy -> enemy.getTexture().dispose());

        playerBullets.forEach(bullet -> bullet.getTexture().dispose());
        enemyBullets.forEach(bullet -> bullet.getTexture().dispose());

        shootButton.getTexture().dispose();
        pauseButton.getTexture().dispose();

//        game.batch.dispose();
    }

    private void drawEverything(){
        drawObject(shootButton);
        drawObject(pauseButton);

        drawObject(player);

        enemies.forEach(this::drawObject);
        producerEnemies.forEach(this::drawObject);

        enemyBullets.forEach(this::drawObject);
        playerBullets.forEach(this::drawObject);

        //----------------TEXT
    }

    private void drawObject(GameObject obj) {
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = obj.getAnimation().getKeyFrame(stateTime, true);

        game.batch.draw(currentFrame, obj.getRectangle().getX(), obj.getRectangle().getY(), obj.getRectangle().getWidth(), obj.getRectangle().getHeight());
    }

    private void updateEverything(){
        player.update();

        enemies.forEach(enemy -> enemy.update(player, playerBullets, enemyBullets));
        producerEnemies.forEach(enemy -> enemy.update(player, enemies));

        updateBullets();

        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
        producerEnemies.removeIf(enemy -> enemy.getHealth() <= 0);

        Spawn.spawnEnemies(enemies, producerEnemies);

        //----------------DEBUG ONLY - ENEMIES REACHING END OF SCREEN WILL CAUSE GAME OVER
        enemies.removeIf(enemy -> enemy.getRectangle().getX() + enemy.getRectangle().getWidth() < 0);
        //-----------------------
    }

    private void updateBullets() {
        playerBullets.removeIf(bullet -> (bullet.update(enemies, producerEnemies) || bullet.getRectangle().getX() > screenXMax));
        enemyBullets.removeIf(bullet -> (bullet.update(player) || bullet.getRectangle().getX() + bullet.getRectangle().getWidth() < 0));
    }

    public void shoot() {
        playerBullets.add(player.shoot());
    }

    private boolean gameOver() {
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
