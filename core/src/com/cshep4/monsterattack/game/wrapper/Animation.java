package com.cshep4.monsterattack.game.wrapper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@AllArgsConstructor
public class Animation {
    private Texture texture;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;

    public void dispose() {
        texture.dispose();
    }
}
