package com.cshep4.monsterattack.game.core;

import com.badlogic.gdx.Gdx;

import lombok.Getter;

public enum State
{
    PAUSE,
    RUN,
    RESUME,
    GAME_OVER;

    @Getter
    private float stateTime = 0f;

    public void updateStateTime() {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    }
}