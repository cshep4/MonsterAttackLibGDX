package com.cshep4.monsterattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.factory.TextureFactory;
import com.cshep4.monsterattack.game.stage.MainMenuStage;

import static com.cshep4.monsterattack.game.constants.Constants.BACKGROUND;


public class MainMenuScreen implements Screen {
    private static final float WIDTH = Gdx.graphics.getWidth();
    private static final float HEIGHT = Gdx.graphics.getHeight();

    private Texture background = TextureFactory.create(BACKGROUND);
    private MainMenuStage stage;

    public MainMenuScreen(final MonsterAttack game) {
        stage = new MainMenuStage(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, WIDTH, HEIGHT);
        stage.getBatch().end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // not implemented
    }

    @Override
    public void show() {
        stage.setup();
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }

    @Override
    public void resume() {
        // not implemented
    }
}
