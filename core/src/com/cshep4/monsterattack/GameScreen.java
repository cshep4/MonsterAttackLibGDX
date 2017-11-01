package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;
import java.util.Random;


public class GameScreen implements Screen {

    private final MonsterAttack game;
    private float screenXMax = 450;
    private float screenYMax = 0;
    private OrthographicCamera camera;

    private final int LEFT = 1;
    private final int RIGHT = -1;

    Circle tempBall;

    //--------------TEXTURES
    Texture ball;
    //----------

    //---------------PLAYER
//    Player player;
    Circle playerCircle;
    float player_radius = 30;

    //---------------BALLS
//    ArrayList<Ball> balls = new ArrayList<Ball>();
    Circle ballCircle;
    float ball_radius;

    ShapeRenderer debugRenderer = new ShapeRenderer();

    //----------------------

    public GameScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices---------------------
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        float ratio = width / height;
        screenYMax = screenXMax / ratio;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);
        //------------------------------------------------------------------------------------------

        //----------------TEXTURE SETUP
//        ball = new Texture(Gdx.files.internal("ball2.png"));
        //-------------------------------

        //----------------OBJECT SETUP
        //----------------------------------

    }

    @Override
    public void render(float delta) {

        // background color-------------------------------------------------------------------------
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //------------------------------------------------------------------------------------------
        camera.update();
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.setColor(Color.CYAN);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
//        debugRenderer.circle(player.getCircle().x, player.getCircle().y, player.getCircle().radius);
//        for (Ball ball : balls) {
//            //game.batch.draw(ball.getTexture(), ball.getCircle().x,ball.getCircle().y,ball.getCircle().radius*2,ball.getCircle().radius*2);
//            debugRenderer.circle(ball.getCircle().x, ball.getCircle().y, ball.getCircle().radius);
//
//        }
        debugRenderer.end();
        game.batch.setProjectionMatrix(camera.combined);


        processUserInput();

        updateEverything();

        drawEverything();
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

        game.batch.begin();

        // get text layout height and width and place it in the middle of the screen----------------
        String gameString = "Game";
        GlyphLayout gameTextLayout = new GlyphLayout(game.font, gameString);
        float gameTextWidth = gameTextLayout.width;
        float gameTextHeight = gameTextLayout.height;
        //game.font.draw(game.batch, gameString, screenXMax / 2 - (gameTextWidth / 2),
        //        screenYMax / 2 - 100 - (gameTextHeight / 2));
        //------------------------------------------------------------------------------------------

        //---------------------------------DRAW OBJECT
//			player.drawObject(paint, canvas);
//			shootButton.drawButton(canvas);
//			pauseButton.drawButton(canvas);
        //--------------------------------------------

        game.batch.end();
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

    public void updateEverything(){
//        player.update();
//		  updateEnemies(canvas);
//        updateBullets(canvas);
    }
}
