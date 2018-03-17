package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.cshep4.monsterattack.game.factory.CameraFactory;

import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;


public class GameOverScreen implements Screen {

    private final MonsterAttack game;
    private OrthographicCamera camera;

    public GameOverScreen(final MonsterAttack game) {
        this.game = game;

        camera = CameraFactory.create(false, getScreenXMax(), getScreenYMax());

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(256, 256, 256, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            game.getScreen().dispose();
            game.setScreen(new MainMenuScreen(game));
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        String mainMenuString = "Game Over";
        GlyphLayout mainMenuLayout = new GlyphLayout(game.font, mainMenuString);
        float mainMenuTextWidth = mainMenuLayout.width;
        float mainMenuTextHeight = mainMenuLayout.height;
        game.font.draw(game.batch, mainMenuString, getScreenXMax() / 2 - (mainMenuTextWidth / 2),
                getScreenYMax() / 2 - (mainMenuTextHeight / 2));

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
