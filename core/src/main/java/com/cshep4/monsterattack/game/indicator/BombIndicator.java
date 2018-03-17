package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.game.constants.Constants.BOMB;
import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static java.lang.String.valueOf;

@EqualsAndHashCode(callSuper=true)
@Getter
public class BombIndicator extends ScreenIndicator {
    private static final float BOMB_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
    private static final float INDICATOR_SEPARATOR = BOMB_SIZE / 2;

    private Texture bombTexture = TextureFactory.create(BOMB);
    private float x = 0;
    private float y = 0;
    private int bombNumber = 0;

    @Override
    public void update(Player player) {
        throw new UnsupportedOperationException();
    }

    public void update(Player player, float bulletLayoutWidth) {
        bombNumber = player.getNumberOfBombs();

        text = valueOf(bombNumber);

        x = INDICATOR_SEPARATOR + BOMB_SIZE + bulletLayoutWidth;
        y = 0;
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        if (bombNumber > 0) {
            batch.draw(bombTexture, x, y, BOMB_SIZE, BOMB_SIZE);
            font.draw(batch, text, BOMB_SIZE + x, BOMB_SIZE);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        throw new UnsupportedOperationException();
    }


}
