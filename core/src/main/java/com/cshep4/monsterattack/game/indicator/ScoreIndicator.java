package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;
import static com.cshep4.monsterattack.game.utils.Utils.getTextWidth;

@EqualsAndHashCode(callSuper=true)
public class ScoreIndicator extends ScreenIndicator {
    @Getter
    private static int numKills;

    @Override
    public void update(Player player) {
        throw new UnsupportedOperationException();
    }

    public void update() {
        text = "Kills: " + numKills;
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        final float textWidth = getTextWidth(font, text);
        final float x = (getScreenXMax() - textWidth) / 2;
        final float y = getScreenYMax();

        font.draw(batch, text, x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        throw new UnsupportedOperationException();
    }

    public static void incrementKills() {
        numKills++;
    }

    public static void resetKills() {
        numKills = 0;
    }
}
