package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;

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
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = (getScreenXMax() - layout.width) / 2;
        final float y = getScreenYMax();

        font.draw(batch, layout, x, y);
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
