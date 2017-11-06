package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cshep4.monsterattack.game.Bullet;
import com.cshep4.monsterattack.game.Create;
import com.cshep4.monsterattack.game.Enemy;
import com.cshep4.monsterattack.game.GameObject;
import com.cshep4.monsterattack.game.PauseButton;
import com.cshep4.monsterattack.game.Player;
import com.cshep4.monsterattack.game.ShootButton;

import java.util.ArrayList;

import static com.cshep4.monsterattack.game.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.Constants.BUTTON_SIZE_DIVIDER;


public class GameScreen implements Screen {

    private final MonsterAttack game;
    private static float screenXMax = 450;
    private static float screenYMax = 0;
    private OrthographicCamera camera;
    private float width, height;

    //---------------PLAYER
    private final int PLAYER_START_X = 50;
    private final int PLAYER_START_Y = 50;
    Player player = Create.player(PLAYER_START_X, PLAYER_START_Y);
    //----------------------

    //---------------ENEMIES
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Enemy> producerEnemies = new ArrayList<Enemy>();
    //----------------------

    //---------------BULLETS
    private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
    //----------------------

    //---------------BUTTONS
    private ShootButton shootButton;
    private PauseButton pauseButton;
    //----------------------

    //---------------BACKGROUND
    private Sprite backgroundSprite;
    //----------------------

    public GameScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices---------------------
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        float ratio = width / height;
        screenYMax = screenXMax / ratio;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);
        //------------------------------------------------------------------------------------------

        //----------------BACKGROUND SETUP
        Texture backgroundTexture = new Texture(Gdx.files.internal(BACKGROUND));
        backgroundSprite = new Sprite(backgroundTexture);
        //-------------------------------

        //----------------BUTTON SETUP
        float buttonWidth = screenXMax / BUTTON_SIZE_DIVIDER;
        float buttonHeight = buttonWidth;
        float shootButtonX = screenXMax - buttonWidth;
        float shootButtonY = 0;
        float pauseButtonX = screenXMax - buttonWidth;
        float pauseButtonY = screenYMax - buttonHeight;
        shootButton = Create.shootButton(shootButtonX, shootButtonY, buttonWidth, buttonHeight);
        pauseButton = Create.pauseButton(pauseButtonX, pauseButtonY, buttonWidth, buttonHeight);
        //----------------------------------

    }

    @Override
    public void render(float delta) {
        processUserInput();

        updateEverything();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        game.batch.begin();

        backgroundSprite.draw(game.batch);

        drawEverything();

        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void drawEverything(){
        //----------------BUTTONS
        drawObject(shootButton);
        drawObject(pauseButton);
        //-----------------------

        //----------------PLAYER
        drawObject(player);
        //-----------------------

        //----------------ENEMIES
        drawEnemies();
        //-----------------------

        //----------------BULLETS
        drawBullets();
        //-----------------------
    }

    private void drawEnemies() {
        //----------------ENEMIES
        for (Enemy enemy : enemies) {
            drawObject(enemy);
        }
        //-----------------------

        //----------------PRODUCER ENEMIES
        for (Enemy enemy : producerEnemies) {
            drawObject(enemy);
        }
        //-----------------------
    }

    private void drawBullets() {
        //----------------ENEMY BULLETS
        for (Bullet bullet : enemyBullets) {
            drawObject(bullet);
        }
        //-----------------------

        //----------------PLAYER BULLETS
        for (Bullet bullet : playerBullets) {
            drawObject(bullet);
        }
        //-----------------------
    }

    private void drawObject(GameObject obj) {
        game.batch.draw(obj.getTexture(), obj.getRectangle().getX(), obj.getRectangle().getY(), obj.getRectangle().getWidth(), obj.getRectangle().getHeight());
    }

    public void processUserInput(){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {

                return true;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {

                return true;
            }
        });
    }

    private void updateEverything(){

    }

    private void updateBullets() {
        //----------------ENEMY BULLETS
        playerBullets.removeIf(bullet -> bullet.update(enemies));
        playerBullets.removeIf(bullet -> bullet.update(producerEnemies));
        //-----------------------

        //----------------PLAYER BULLETS
        enemyBullets.removeIf(bullet -> bullet.update(player));
        //-----------------------
    }

    public static float getScreenXMax() {
        return screenXMax;
    }

    public static float getScreenYMax() {
        return screenYMax;
    }
}
