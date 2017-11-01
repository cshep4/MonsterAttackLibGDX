package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class Character extends GameObject {
    protected int health;
    protected int xVel;
    protected int yVel;
    protected int directionFacing;

    public Character(Rectangle rectangle, Texture texture) {
        super(rectangle, texture);
        health = 100;
        this.getRectangle().setWidth(40);
        this.getRectangle().setHeight(40);
    }

    public void update(){
        //Set the speed of the object
        this.getRectangle().setX(this.getRectangle().getX() + this.xVel);
        this.getRectangle().setY(this.getRectangle().getY() + this.yVel);

//        directionChange = (this.xVel <= 0 && this.directionFacing == Constants.RIGHT) ||
//                (this.xVel > 0 && this.directionFacing == Constants.LEFT);
//        if (this.xVel <= 0)
//        {
//            this.directionFacing = Constants.LEFT;
//        } else {
//            this.directionFacing = Constants.RIGHT;
//        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getxVel() {
        return xVel;
    }

    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    public int getyVel() {
        return yVel;
    }

    public void setyVel(int yVel) {
        this.yVel = yVel;
    }

    public int getDirectionFacing() {
        return directionFacing;
    }

    public void setDirectionFacing(int directionFacing) {
        this.directionFacing = directionFacing;
    }
}
