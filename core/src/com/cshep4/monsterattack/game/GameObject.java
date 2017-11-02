package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    private Rectangle rectangle;
    private Texture texture;

    public GameObject(Rectangle rectangle, Texture texture) {
        this.rectangle = rectangle;
        this.texture = texture;
    }

    public Rectangle getRectangle() { return rectangle; }

    public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
