package com.cshep4.monsterattack.game.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import lombok.Data;

@Data
public abstract class GameObject {
    private Rectangle rectangle;
    private Texture texture;
    private Animation<TextureRegion> animation;

    public GameObject(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        this.rectangle = rectangle;
        this.texture = texture;
        animation = null;

        createAnimation(frameCols, frameRows);
    }

    private void createAnimation(int frameCols, int frameRows) {
        int tileWidth = texture.getWidth() / frameCols;
        int tileHeight = texture.getHeight() / frameRows;
        TextureRegion[][] tmp = TextureRegion.split(texture, tileWidth, tileHeight);

        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<>(0.1f, frames);
    }

    public void changeAnimation(Texture texture, int frameCols, int frameRows) {
        this.texture.dispose();
        this.texture = texture;
        createAnimation(frameCols, frameRows);
    }

    public float getMidX() {
        return getX()+(getWidth()/2);
    }

    public float getMidY() {
        return getY()+(getHeight()/2);
    }

    public float getX() {
        return rectangle.x;
    }

    public void setX(float x) {
        rectangle.x = x;
    }

    public float getY() {
        return rectangle.y;
    }

    public void setY(float y) {
        rectangle.y = y;
    }

    public float getWidth () {
        return rectangle.width;
    }

    public void setWidth (float width) {
        rectangle.width = width;
    }

    public float getHeight () {
        return rectangle.height;
    }

    public void setHeight (float height) {
        rectangle.height = height;
    }

}
