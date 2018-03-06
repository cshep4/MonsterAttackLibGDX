package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshep4.monsterattack.game.wrapper.Animation;

import java.util.Arrays;

public class AnimationFactory {
    private static final TextureFactory TEXTURE_FACTORY = new TextureFactoryImpl();

    public Animation createAnimation(int frameCols, int frameRows, String sprite) {
        Texture texture = TEXTURE_FACTORY.create(sprite);

        if (null == texture) {
            return null;
        }

        int tileWidth = texture.getWidth() / frameCols;
        int tileHeight = texture.getHeight() / frameRows;

        TextureRegion[] frames = Arrays
                .stream(TextureRegion.split(texture, tileWidth, tileHeight))
                .flatMap(Arrays::stream)
                .toArray(TextureRegion[]::new);

        return new Animation(texture, new com.badlogic.gdx.graphics.g2d.Animation<>(0.1f, frames));
    }
}
