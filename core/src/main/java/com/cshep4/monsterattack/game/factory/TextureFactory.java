package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureFactory {
    private TextureFactory() {}

    public static Texture create(String fileName) {
        try {
            return new Texture(Gdx.files.internal(fileName));
        } catch (GdxRuntimeException gre) {
            Gdx.app.log("Error!", "Could not load texture");
            return null;
        }
    }
}
