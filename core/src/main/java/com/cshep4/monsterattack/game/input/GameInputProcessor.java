package com.cshep4.monsterattack.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.MainMenuScreen;
import com.cshep4.monsterattack.game.bullet.Bullet;

import lombok.Getter;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.cshep4.monsterattack.game.core.State.PAUSE;
import static com.cshep4.monsterattack.game.core.State.RESUME;
import static com.cshep4.monsterattack.game.core.State.RUN;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;

@Getter
public class GameInputProcessor extends InputAdapter {
    private GameScreen gameScreen;
    private int movementPointer = -1;
    private boolean justPaused = false;

    public GameInputProcessor(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        float xMultiplier = getScreenXMax() / Gdx.graphics.getWidth();
        float yMultiplier = getScreenYMax() / Gdx.graphics.getHeight();

        float xPos = xMultiplier * x;
        float yPos = yMultiplier * (gameScreen.getHeight() - y);

        if (!gameScreen.getState().equals(RUN)) {
            return false;
        }

        if (isPauseButtonPressed(xPos, yPos)) {
            gameScreen.setState(PAUSE);
            justPaused = true;
        } else if (isShootButtonPressed(xPos, yPos)) {
            playerShoot();
        } else {
            if (movementPointer == -1) {
                movementPointer = pointer;
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

    private void playerShoot() {
        Bullet bullet = gameScreen.getPlayer().shoot();

        if (bullet != null) {
            gameScreen.getPlayerBullets().add(bullet);
        }
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!justPaused && gameScreen.getState().equals(PAUSE)) {
            gameScreen.setState(RESUME);
        }
        justPaused = false;

        if (pointer == movementPointer) {
            gameScreen.getPlayer().setDestinationX(gameScreen.getPlayer().getMidX());
            gameScreen.getPlayer().setDestinationY(gameScreen.getPlayer().getMidY());
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
            gameScreen.getPlayer().setDestinationX(xPos);
            gameScreen.getPlayer().setDestinationY(yPos);
        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == BACK){
            gameScreen.getGame().setScreen(new MainMenuScreen(gameScreen.getGame()));
        }
        return false;
    }
}