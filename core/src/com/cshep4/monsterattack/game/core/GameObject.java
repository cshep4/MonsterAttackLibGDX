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
    private int frameCols;
    private int frameRows;

    public GameObject(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        this.rectangle = rectangle;
        this.texture = texture;

        createAnimation(frameCols, frameRows);
    }

    private void createAnimation(int frameCols, int frameRows) {
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / frameCols,
                texture.getHeight() / frameRows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<>(0.1f, frames);
    }

    public void changeAnimation(Texture texture, int frameCols, int frameRows) {
        texture.dispose();
        this.texture = texture;
        createAnimation(frameCols, frameRows);
    }

    protected float getMidX() {
        return rectangle.getX()+(rectangle.getWidth()/2);
    }

    protected float getMidY() {
        return rectangle.getY()+(rectangle.getHeight()/2);
    }
}
