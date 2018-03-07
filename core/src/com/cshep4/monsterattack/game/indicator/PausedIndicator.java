package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.utils.Utils;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.core.State.PAUSE;

@EqualsAndHashCode(callSuper=true)
public class PausedIndicator extends ScreenIndicator {
    private GameScreen gameScreen;
    private float x;
    private float y;

    public PausedIndicator(BitmapFont font, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        text = "PAUSED";

        float textWidth = Utils.getTextWidth(font, text);
        x = (GameScreen.getScreenXMax() - textWidth) / 2;
        y = (GameScreen.getScreenYMax() - textWidth) / 2;
    }

    @Override
    public void update(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        if (gameScreen.getState() == PAUSE) {
            font.draw(batch, text, x, y);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        throw new UnsupportedOperationException();
    }
}
