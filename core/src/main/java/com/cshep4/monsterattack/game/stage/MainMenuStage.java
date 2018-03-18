package com.cshep4.monsterattack.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.cshep4.monsterattack.MonsterAttack;
import com.cshep4.monsterattack.game.input.OptionsButtonListener;
import com.cshep4.monsterattack.game.input.PlayButtonListener;

import static com.badlogic.gdx.utils.Align.center;

public class MainMenuStage extends Stage {
    private final Skin skin = new Skin(Gdx.files.internal("skin/comic/skin/comic-ui.json"));
    private static final float WIDTH = Gdx.graphics.getWidth();
    private static final float HEIGHT = Gdx.graphics.getHeight();
    private final MonsterAttack game;

    public MainMenuStage(final MonsterAttack game) {
        super(new ScreenViewport());
        this.game = game;
    }

    public void setup() {
        addTitle();
        addPlayButton();
        addOptionsButton();
    }

    private void addTitle() {
        Label title = new Label("Monster Attack", skin, "big");
        title.setFontScale(5);
        title.setAlignment(center);
        title.setWidth(WIDTH);
        title.setHeight(HEIGHT/4);
        title.setY(HEIGHT - title.getHeight());
        addActor(title);
    }

    private void addPlayButton() {
        TextButton playButton = new TextButton("Play!", skin);
        playButton.getLabel().setFontScale(5);

        float width = WIDTH/2;
        playButton.setWidth(width);

        float height = HEIGHT/4;
        playButton.setHeight(height);

        float x = WIDTH / 2 - playButton.getWidth() / 2;
        float y = (float) (HEIGHT / 2 - (playButton.getHeight() * 0.33));
        playButton.setPosition(x, y);

        PlayButtonListener playButtonListener = new PlayButtonListener(game);
        playButton.addListener(playButtonListener);

        addActor(playButton);
    }

    private void addOptionsButton() {
        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.getLabel().setFontScale(5);

        float width = WIDTH / 2;
        optionsButton.setWidth(width);

        float height = HEIGHT/4;
        optionsButton.setHeight(height);

        float x = WIDTH / 2 - optionsButton.getWidth() / 2;
        float y = (float) (HEIGHT / 4 - (optionsButton.getHeight() * 0.66));
        optionsButton.setPosition(x, y);

        OptionsButtonListener optionsButtonListener = new OptionsButtonListener(game);
        optionsButton.addListener(optionsButtonListener);

        addActor(optionsButton);
    }
}
