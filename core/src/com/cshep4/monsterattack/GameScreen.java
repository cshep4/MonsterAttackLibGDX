package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.Bullet;
import com.cshep4.monsterattack.game.Create;
import com.cshep4.monsterattack.game.Enemy;
import com.cshep4.monsterattack.game.GameObject;
import com.cshep4.monsterattack.game.PauseButton;
import com.cshep4.monsterattack.game.Player;
import com.cshep4.monsterattack.game.ProducerEnemy;
import com.cshep4.monsterattack.game.ShootButton;
import com.cshep4.monsterattack.game.Spawn;
import com.cshep4.monsterattack.game.State;

import java.util.ArrayList;

import static com.cshep4.monsterattack.game.Constants.BACKGROUND;
import static com.cshep4.monsterattack.game.Constants.BUTTON_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.State.GAME_OVER;


public class GameScreen implements Screen {

    private final MonsterAttack game;
    private static float screenXMax = 450;
    private static float screenYMax = 0;
    private OrthographicCamera camera;
    private float width, height;

    // A variable for tracking elapsed time for the animation
    private float stateTime;

    //----------------STATE
    private State state = State.RUN;
    private boolean justPaused = false;
    private int pressDownPointer = -1;
    //---------------------

    //------------GAME OVER
    private long gameOverTime = 0;
    private static final int GAME_OVER_DELAY = 1000;
    //---------------------

    //---------------PLAYER
    private final int PLAYER_START_X = 50;
    private final int PLAYER_START_Y = 50;
    private Player player = Create.player(PLAYER_START_X, PLAYER_START_Y);
    //----------------------

    //---------------ENEMIES
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<ProducerEnemy> producerEnemies = new ArrayList<ProducerEnemy>();
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
                this.state = State.RUN;
                break;
            case GAME_OVER:
                if (System.currentTimeMillis() - gameOverTime > GAME_OVER_DELAY) {
                    game.setScreen(new GameOverScreen(game));
                    this.dispose();
                }
                break;
            default:
                this.state = State.RUN;
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

        //IF PAUSED-------------------------------------------------------------------------------------
        if (this.state == State.PAUSE) {
            // get text layout height and width and place it in the middle of the screen----------------
            String mainMenuString = "PAUSED";
            GlyphLayout mainMenuLayout = new GlyphLayout(game.font, mainMenuString);
            float mainMenuTextWidth = mainMenuLayout.width;
            float mainMenuTextHeight = mainMenuLayout.height;
            game.font.draw(game.batch, mainMenuString, screenXMax / 2 - (mainMenuTextWidth / 2),
                    screenYMax / 2 - (mainMenuTextHeight / 2));
            //------------------------------------------------------------------------------------------
        }
        //----------------------------------------------------------------------------------------------

        game.batch.end();

        if (gameOver()) {
            this.state = GAME_OVER;
            if (gameOverTime == 0) {
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void updateStateTime() {
        if (this.state == State.RUN) {
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //---------------PLAYER
        player.getTexture().dispose();
        //----------------------

        //---------------ENEMIES
        enemies.forEach(enemy -> enemy.getTexture().dispose());
        producerEnemies.forEach(enemy -> enemy.getTexture().dispose());
        //----------------------

        //---------------BULLETS
        playerBullets.forEach(bullet -> bullet.getTexture().dispose());
        enemyBullets.forEach(bullet -> bullet.getTexture().dispose());
        //----------------------

        //---------------BUTTONS
        shootButton.getTexture().dispose();
        pauseButton.getTexture().dispose();

        //---------------GAME
//        game.batch.dispose();
        //----------------------
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
        enemies.forEach(this::drawObject);
        producerEnemies.forEach(this::drawObject);
        //-----------------------

        //----------------BULLETS
        enemyBullets.forEach(this::drawObject);
        playerBullets.forEach(this::drawObject);
        //-----------------------

        //----------------TEXT
        //-----------------------
    }

    private void drawObject(GameObject obj) {
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = obj.getAnimation().getKeyFrame(stateTime, true);

        game.batch.draw(currentFrame, obj.getRectangle().getX(), obj.getRectangle().getY(), obj.getRectangle().getWidth(), obj.getRectangle().getHeight());
    }

    private void updateEverything(){
        //----------------PLAYER POSITION
        player.update();
        //-----------------------

        //----------------ENEMY POSITIONS
        enemies.forEach(enemy -> enemy.update(player, playerBullets, enemyBullets));
        producerEnemies.forEach(enemy -> enemy.update(player, enemies));
        //-----------------------

        //----------------BULLET POSITIONS
        updateBullets();
        //-----------------------

        //----------------CHECK ENEMY HEALTH
        enemies.removeIf(enemy -> enemy.getHealth() <= 0);
        producerEnemies.removeIf(enemy -> enemy.getHealth() <= 0);
        //-----------------------

        //----------------SPAWN NEW ENEMIES
        Spawn.spawnEnemies(enemies, producerEnemies);
        //-----------------------

        //----------------DEBUG ONLY - ENEMIES REACHING END OF SCREEN WILL CAUSE GAME OVER
        enemies.removeIf(enemy -> enemy.getRectangle().getX() + enemy.getRectangle().getWidth() < 0);
        //-----------------------
    }

    private void updateBullets() {
        //----------------ENEMY BULLETS
        playerBullets.removeIf(bullet -> (bullet.update(enemies, producerEnemies) || bullet.getRectangle().getX() > getScreenXMax()));
        //-----------------------

        //----------------PLAYER BULLETS
        enemyBullets.removeIf(bullet -> (bullet.update(player) || bullet.getRectangle().getX() + bullet.getRectangle().getWidth() < 0));
        //-----------------------
    }

    private void shoot() {
        playerBullets.add(player.shoot());
    }

    private boolean gameOver() {
        return player.getHealth() <= 0;
    }

    public void processUserInput(){
        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                //----------------------------------------GET CAMERA COORDS
                float xMultiplier = screenXMax / Gdx.graphics.getWidth();
                float yMultiplier = screenYMax / Gdx.graphics.getHeight();

                float xPos = xMultiplier * x;
                float yPos = yMultiplier * (height - y);
                //---------------------------------------------------------

                //--------------------------------------------IF NOT PAUSED
                if (getGameState() == State.RUN) {
                    //-----------------------------------------------PAUSE
                    if (pauseButton.getRectangle().contains(xPos, yPos)) {
                        setGameState(State.PAUSE);
                        setJustPaused(true);
                    }
                    //-----------------------------------------------------

                    //------------------------------------------------SHOOT
                    else if (shootButton.getRectangle().contains(xPos, yPos)) {
                        shoot();
                    }
                    //-----------------------------------------------------

                    //-------------------------------------------------MOVE
                    else {
                        if (getPressDownPointer() == -1) {
                            setPressDownPointer(pointer);
                        }
                    }
                }
                //---------------------------------------------------------

                return true;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                //---------------------------------------------------PAUSE/UNPAUSE
                if (!isJustPaused()&& getGameState() == State.PAUSE) {
                    setGameState(State.RESUME);
                }
                setJustPaused(false);
                //------------------------------------------------------------

                //-----------------------------------------END PLAYER MOVEMENT
                if (pointer == getPressDownPointer()) {
                    player.stand();
                    setPressDownPointer(-1);
                }
                //------------------------------------------------------------

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                //----------------------------------------GET CAMERA COORDS
                float xMultiplier = screenXMax / Gdx.graphics.getWidth();
                float yMultiplier = screenYMax / Gdx.graphics.getHeight();

                float xPos = xMultiplier * screenX;
                float yPos = yMultiplier * (height - screenY);
                //---------------------------------------------------------

                //----------------------------------------------PLAYER MOVEMENT
                if (pointer == getPressDownPointer()) {
                    player.move(xPos, yPos);
                }
                //------------------------------------------------------------
                return true;
            }
        });
    }

    public static float getScreenXMax() {
        return screenXMax;
    }

    public static float getScreenYMax() {
        return screenYMax;
    }

    public void setGameState(State s){
        this.state = s;
    }

    public State getGameState() {
        return this.state;
    }

    public boolean isJustPaused() {
        return justPaused;
    }

    public void setJustPaused(boolean justPaused) {
        this.justPaused = justPaused;
    }

    public int getPressDownPointer() {
        return pressDownPointer;
    }

    public void setPressDownPointer(int pressDownPointer) {
        this.pressDownPointer = pressDownPointer;
    }
}
