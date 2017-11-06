package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import static com.cshep4.monsterattack.GameScreen.getScreenXMax;


public abstract class Character extends GameObject {
    protected int health;
    protected int xVel;
    protected int yVel;
    protected int directionFacing;

    public Character(Rectangle rectangle, Texture texture) {
        super(rectangle, texture);
        health = 100;
        getRectangle().setWidth(getScreenXMax() / Constants.CHARACTER_WIDTH_DIVIDER);
        getRectangle().setHeight(getScreenXMax() / Constants.CHARACTER_HEIGHT_DIVIDER);
    }

    public void update(){
        //Set the speed of the object
        this.getRectangle().setX(this.getRectangle().getX() + this.xVel);
        this.getRectangle().setY(this.getRectangle().getY() + this.yVel);

        if (this.xVel <= 0)
        {
            this.directionFacing = Constants.LEFT;
        } else {
            this.directionFacing = Constants.RIGHT;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXVel() {
        return xVel;
    }

    public void setXVel(int xVel) {
        this.xVel = xVel;
    }

    public int getYVel() {
        return yVel;
    }

    public void setYVel(int yVel) {
        this.yVel = yVel;
    }

    public int getDirectionFacing() {
        return directionFacing;
    }

    public void setDirectionFacing(int directionFacing) {
        this.directionFacing = directionFacing;
    }
}
