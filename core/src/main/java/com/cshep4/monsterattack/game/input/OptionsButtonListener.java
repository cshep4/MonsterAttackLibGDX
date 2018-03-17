package com.cshep4.monsterattack.game.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cshep4.monsterattack.GameOverScreen;
import com.cshep4.monsterattack.MonsterAttack;

public class OptionsButtonListener extends InputListener {
    private final MonsterAttack game;

    public OptionsButtonListener(final MonsterAttack game) {
        this.game = game;
    }

    @Override
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }
}
