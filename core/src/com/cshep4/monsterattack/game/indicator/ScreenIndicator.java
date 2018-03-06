package com.cshep4.monsterattack.game.indicator;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.character.Player;

import lombok.Getter;

public abstract class ScreenIndicator {
    @Getter
    String text = "";

    public abstract void update(Player player);

    public abstract void draw(SpriteBatch batch, BitmapFont font);

    public abstract void draw(SpriteBatch batch);
}
