package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.FRAME_RATE;

public abstract class GameObject {
    private Rectangle rectangle;
    private Texture texture;
    private Animation<TextureRegion> animation;
    private int frameCols, frameRows;

    public GameObject(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        this.rectangle = rectangle;
        this.texture = texture;

        this.createAnimation(frameCols, frameRows);
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

        float frameNumber = frameCols * frameRows;
        float frameDuration = frameNumber / FRAME_RATE;

        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<TextureRegion>(0.1f, frames);
    }

    public void changeAnimation(Texture texture, int frameCols, int frameRows) {
        this.texture.dispose();
        this.texture = texture;
        this.createAnimation(frameCols, frameRows);
    }

    public Rectangle getRectangle() { return rectangle; }

    public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public int getFrameCols() {
        return frameCols;
    }

    public void setFrameCols(int frameCols) {
        this.frameCols = frameCols;
    }

    public int getFrameRows() {
        return frameRows;
    }

    public void setFrameRows(int frameRows) {
        this.frameRows = frameRows;
    }
}
