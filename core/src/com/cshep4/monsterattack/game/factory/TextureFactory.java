package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public final class TextureFactory {
    private static Texture texture;

    private TextureFactory() {}

    public static Texture create(String fileName) {
        try {
            texture = new Texture(Gdx.files.internal(fileName));
        } catch (GdxRuntimeException gre) {
            // Do not create texture
            //return last loaded texture
            //should only occur in development
            Gdx.app.log("Error!", "Could not load texture");
        }

        return texture;
    }

    public static Texture getLastTexture() {
        return texture;
    }

    public static void setTexture(Texture texture) {
        TextureFactory.texture = texture;
    }
}
