package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.character.Player;

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

        GlyphLayout layout = new GlyphLayout(font, text);
        x = (GameScreen.getScreenXMax() - layout.width) / 2;
        y = (GameScreen.getScreenYMax() - layout.height) / 2;
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
