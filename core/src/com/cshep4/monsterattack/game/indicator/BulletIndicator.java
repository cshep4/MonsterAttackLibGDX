package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.INDICATOR_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_BULLET;
import static java.lang.String.valueOf;

@EqualsAndHashCode(callSuper=true)
public class BulletIndicator extends ScreenIndicator {
    private static final float BULLET_SIZE = getScreenXMax() / INDICATOR_SIZE_DIVIDER;

    private TextureFactory textureFactory;
    private Texture bulletCaseTexture;

    @Getter
    private GlyphLayout layout;

    public BulletIndicator(TextureFactory textureFactory) {
        bulletCaseTexture = textureFactory.create(PICKUP_BULLET);
    }

    @Override
    public void update(Player player) {
        text = valueOf(player.getNumberOfBullets());
    }

    @Override
    public void draw(SpriteBatch batch, BitmapFont font) {
        batch.draw(bulletCaseTexture, 0, 0, BULLET_SIZE, BULLET_SIZE);

        layout = new GlyphLayout(font, text);
        font.draw(batch, text, BULLET_SIZE, BULLET_SIZE);
    }

    @Override
    public void draw(SpriteBatch batch) {
        throw new UnsupportedOperationException();
    }


}
