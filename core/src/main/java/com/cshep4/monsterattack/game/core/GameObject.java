package com.cshep4.monsterattack.game.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class GameObject {
    private Rectangle rectangle;
    private Animation animation;


    protected GameObject(Rectangle rectangle, String texture, int frameCols, int frameRows) {
        this.rectangle = rectangle;
        animation = AnimationFactory.createAnimation(frameCols, frameRows, texture);
    }

    public void changeAnimation(String texture, int frameCols, int frameRows) {
        animation.dispose();
        animation = AnimationFactory.createAnimation(frameCols, frameRows, texture);
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

    public GameObject setX(float x) {
        rectangle.x = x;
        return this;
    }

    public float getY() {
        return rectangle.y;
    }

    public GameObject setY(float y) {
        rectangle.y = y;
        return this;
    }

    public float getWidth () {
        return rectangle.width;
    }

    public GameObject setWidth (float width) {
        rectangle.width = width;
        return this;

    }

    public float getHeight () {
        return rectangle.height;
    }

    public GameObject setHeight (float height) {
        rectangle.height = height;
        return this;
    }

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> getAnimation() {
        return animation.getAnimation();
    }

    public Texture getTexture() {
        return animation.getTexture();
    }

    public void dispose() {
        animation.dispose();
    }
}
