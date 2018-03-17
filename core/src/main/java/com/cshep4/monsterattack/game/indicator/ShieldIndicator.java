package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.SHIELD;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static java.lang.String.valueOf;

@EqualsAndHashCode(callSuper=true)
@Getter
public class ShieldIndicator extends ScreenIndicator {
    private static final float INDICATOR_SPRITE_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;
    private static final float INDICATOR_SEPARATOR = INDICATOR_SPRITE_SIZE / 2;

    private Texture shieldTexture;
    private float x = 0;
    private float y = 0;
    private int shieldTime = 0;

    public ShieldIndicator() {
        shieldTexture = TextureFactory.create(SHIELD);
    }

    @Override
    public void update(Player player) {
        throw new UnsupportedOperationException();
    }

    public void update(Player player, float bulletLayoutWidth, float bombLayoutWidth) {
        shieldTime = player.getRemainingShieldTime();

        text = valueOf(shieldTime);

        x = INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + bulletLayoutWidth;
        if (player.getNumberOfBombs() > 0) {
            x += INDICATOR_SEPARATOR + INDICATOR_SPRITE_SIZE + bombLayoutWidth;
        }

        y = 0;
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        if (shieldTime > 0) {
            batch.draw(shieldTexture, x, y, INDICATOR_SPRITE_SIZE, INDICATOR_SPRITE_SIZE);

            font.draw(batch, text, INDICATOR_SPRITE_SIZE + x, INDICATOR_SPRITE_SIZE);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        throw new UnsupportedOperationException();
    }


}
