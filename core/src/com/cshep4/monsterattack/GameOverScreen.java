package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


public class GameOverScreen implements Screen {

    private final MonsterAttack game;
    private float screenXMax = 450;
    private float screenYMax = 0;
    private OrthographicCamera camera;

    public GameOverScreen(final MonsterAttack game) {
        this.game = game;

        // create the camera and make sure it looks the same across all devices---------------------
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        float ratio = width / height;
        screenYMax = screenXMax / ratio;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenXMax, screenYMax);
        //------------------------------------------------------------------------------------------

    }
    @Override
    public void render(float delta) {

        // background color-------------------------------------------------------------------------
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //------------------------------------------------------------------------------------------

        if (Gdx.input.isTouched()) {
            game.getScreen().dispose();
            game.setScreen(new MainMenuScreen(game));
        }



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // get text layout height and width and place it in the middle of the screen----------------
        String mainMenuString = "Game Over";
        GlyphLayout mainMenuLayout = new GlyphLayout(game.font, mainMenuString);
        float mainMenuTextWidth = mainMenuLayout.width;
        float mainMenuTextHeight = mainMenuLayout.height;
        game.font.draw(game.batch, mainMenuString, screenXMax / 2 - (mainMenuTextWidth / 2),
                screenYMax / 2 - (mainMenuTextHeight / 2));
        //------------------------------------------------------------------------------------------

        game.batch.end();

    }
    @Override
    public void resize(int width, int height) {
        // not implemented
    }

    @Override
    public void show() {
        // not implemented
    }

    @Override
    public void hide() {
        // not implemented
    }

    @Override
    public void pause() {
        // not implemented
    }

    @Override
    public void dispose() {
        // not implemented
    }

    @Override
    public void resume() {
        // not implemented
    }
}
