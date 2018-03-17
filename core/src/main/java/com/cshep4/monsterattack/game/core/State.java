package com.cshep4.monsterattack.game.core;

import lombok.Getter;

import static com.cshep4.monsterattack.game.constants.Constants.GAME_OVER_DELAY;
import static java.lang.System.currentTimeMillis;

@Getter
public enum State
{
    PAUSE,
    RUN,
    RESUME,
    GAME_OVER;

    private float stateTime = 0f;
    private long gameOverTime = 0;

    public void updateStateTime(float delta) {
        if (this.equals(RUN)) {
            stateTime += delta; // Accumulate elapsed animation time
        }
    }

    public void setGameOverTime() {
        if (gameOverTime == 0) {
            gameOverTime = currentTimeMillis();
        }
    }

    public boolean hasGameOverDelayPassed() {
        return currentTimeMillis() - gameOverTime > GAME_OVER_DELAY;
    }
}