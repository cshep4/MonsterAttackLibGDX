package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_WIDTH_DIVIDER;


public abstract class Character extends GameObject {
    protected int health;
    protected float xVel;
    protected float yVel;

    public Character(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
        health = 100;
        getRectangle().setWidth(getScreenXMax() / CHARACTER_WIDTH_DIVIDER);
        getRectangle().setHeight(getScreenXMax() / CHARACTER_HEIGHT_DIVIDER);
    }

    public void update(){
        //Set the speed of the object
        this.getRectangle().setX(this.getRectangle().getX() + (this.xVel* Gdx.graphics.getDeltaTime()));
        this.getRectangle().setY(this.getRectangle().getY() + (this.yVel*Gdx.graphics.getDeltaTime()));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getXVel() {
        return xVel;
    }

    public void setXVel(float xVel) {
        this.xVel = xVel;
    }

    public float getYVel() {
        return yVel;
    }

    public void setYVel(float yVel) {
        this.yVel = yVel;
    }
}
