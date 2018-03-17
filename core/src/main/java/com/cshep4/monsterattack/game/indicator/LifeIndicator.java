package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.LIFE;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenYMax;

@EqualsAndHashCode(callSuper=true)
public class LifeIndicator extends ScreenIndicator {
    private static final float SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;

    private Texture lifeTexture = TextureFactory.create(LIFE);

    @Getter
    private int instances;

    @Override
    public void update(Player player) {
        instances = player.getHealth();
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void draw(SpriteBatch batch) {
        float y = getScreenYMax() - SIZE;

        IntStream.rangeClosed(0, instances - 1)
                .forEach(i -> {
                    float x = SIZE * i;
                    batch.draw(lifeTexture, x, y, SIZE, SIZE);
                });
    }
}
