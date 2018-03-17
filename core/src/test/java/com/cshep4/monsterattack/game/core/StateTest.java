package com.cshep4.monsterattack.game.core;

import org.junit.Test;

import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class StateTest {
    private static final float DELTA = 1000;
    @Test
    public void updateStateTime_updatesTimeWhenStateIsRUN() {
        State state = RUN;
        state.updateStateTime(DELTA);

        assertThat(state.getStateTime(), is(DELTA));
    }

    @Test
    public void updateStateTime_doesNotUpdateTimeWhenStateIsNotRUN() {
        State state = PAUSE;
        state.updateStateTime(DELTA);

        assertThat(state.getStateTime(), is(0f));
    }

    @Test
    public void setGameOverTime_setsTimeIfZero() {
        State state = RUN;
        state.setGameOverTime();

        assertThat(state.getGameOverTime(), not(0));
    }
}