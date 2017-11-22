package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenuScreen implements Screen {

    private final MonsterAttack game;
    private float screenXMax = 450;
    private float screenYMax = 0;
    private OrthographicCamera camera;


    private Stage stage;

    public MainMenuScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices---------------------
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        float ratio = width / height;
        screenYMax = screenXMax / ratio;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);
        //------------------------------------------------------------------------------------------

//        stage = new Stage(new ScreenViewport(camera));
//
//        Label title = new Label("Title Screen", MonsterAttack.gameSkin,"big-white");
//        title.setAlignment(Align.center);
//        title.setY(screenYMax*2/3);
//        title.setWidth(screenXMax);
//        stage.addActor(title);
//
//        TextButton playButton = new TextButton("Play!",MonsterAttack.gameSkin);
//        playButton.setWidth(screenXMax/2);
//        playButton.setPosition(screenXMax/2-playButton.getWidth()/2,screenYMax/2-playButton.getHeight()/2);
//        playButton.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                game.getScreen().dispose();
//                game.setScreen(new GameScreen(game));
//            }
//            @Override
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//        });
//        stage.addActor(playButton);
//
//        TextButton optionsButton = new TextButton("Options",MonsterAttack.gameSkin);
//        optionsButton.setWidth(screenXMax/2);
//        optionsButton.setPosition(screenXMax/2-optionsButton.getWidth()/2,screenYMax/4-optionsButton.getHeight()/2);
//        optionsButton.addListener(new InputListener(){
//            @Override
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
////                game.getScreen().dispose();
////                game.setScreen(new OptionScreen(game));
//            }
//            @Override
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//        });
//        stage.addActor(optionsButton);

    }
    @Override
    public void render(float delta) {

        // background color-------------------------------------------------------------------------
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //------------------------------------------------------------------------------------------

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // get text layout height and width and place it in the middle of the screen----------------
        String mainMenuString = "Main Menu";
        GlyphLayout mainMenuLayout = new GlyphLayout(game.font, mainMenuString);
        float mainMenuTextWidth = mainMenuLayout.width;
        float mainMenuTextHeight = mainMenuLayout.height;
        game.font.draw(game.batch, mainMenuString, screenXMax / 2 - (mainMenuTextWidth / 2),
                screenYMax / 2 - (mainMenuTextHeight / 2));
        //------------------------------------------------------------------------------------------

        game.batch.end();

//        Gdx.gl.glClearColor(0, 0, 0, 0);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        camera.update();
//        stage.act();
//        stage.draw();

    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resume() {
    }
}
