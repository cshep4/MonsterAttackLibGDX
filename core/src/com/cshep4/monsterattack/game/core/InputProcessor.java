package com.cshep4.monsterattack.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.cshep4.monsterattack.GameScreen;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;

public class InputProcessor extends InputAdapter {
    private GameScreen gameScreen;
    private int movementPointer = -1;
    private boolean justPaused = false;

    public InputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        float xMultiplier = getScreenXMax() / Gdx.graphics.getWidth();
        float yMultiplier = getScreenYMax() / Gdx.graphics.getHeight();

        float xPos = xMultiplier * x;
        float yPos = yMultiplier * (gameScreen.getHeight() - y);

        if (gameScreen.getState() != RUN) {
            return false;
        }

        if (isPauseButtonPressed(xPos, yPos)) {
            gameScreen.setState(PAUSE);
            justPaused = true;
        } else if (isShootButtonPressed(xPos, yPos)) {
            gameScreen.shoot();
        } else {
            if (movementPointer == -1) {
                movementPointer = pointer;
                gameScreen.setPlayerMoving(true);
            }
        }

        return true;
    }

    private boolean isPauseButtonPressed(float xPos, float yPos) {
        return gameScreen.getPauseButton().getRectangle().contains(xPos, yPos);
    }

    private boolean isShootButtonPressed(float xPos, float yPos) {
        return gameScreen.getShootButton().getRectangle().contains(xPos, yPos);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!justPaused && gameScreen.getState() == PAUSE) {
            gameScreen.setState(RESUME);
        }
        justPaused = false;

        if (pointer == movementPointer) {
            gameScreen.setPlayerMoving(false);
            gameScreen.getPlayer().stand();
            movementPointer = -1;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float xMultiplier = getScreenXMax() / Gdx.graphics.getWidth();
        float yMultiplier = getScreenYMax() / Gdx.graphics.getHeight();

        float xPos = xMultiplier * screenX;
        float yPos = yMultiplier * (gameScreen.getHeight() - screenY);

        if (pointer == movementPointer) {
            gameScreen.setPlayerDestinationX(xPos);
            gameScreen.setPlayerDestinationY(yPos);
        }
        return true;
    }
}