package com.cshep4.monsterattack.game.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.MonsterAttack;

public class PlayButtonListener extends InputListener {
    private final MonsterAttack game;

    public PlayButtonListener(final MonsterAttack game) {
        this.game = game;
    }

    @Override
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }
}
