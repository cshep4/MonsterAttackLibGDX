package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class Character extends GameObject {
    protected int health;
    protected float xVel;
    protected float yVel;

    Character(Rectangle rectangle, String texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
        health = 1;
        setWidth(getScreenXMax() / CHARACTER_WIDTH_DIVIDER);
        setHeight(getScreenXMax() / CHARACTER_HEIGHT_DIVIDER);
    }

    public void update(){
        setX(getX() + getXVelByDeltaTime());
        setY(getY() + getYVelByDeltaTime());
    }

    public void kill() {
        health = 0;
    }

    public void loseLife() {
        health -= 1;
    }

    float getXVelByDeltaTime() {
        return xVel*Gdx.graphics.getDeltaTime();
    }

    float getYVelByDeltaTime() {
        return yVel*Gdx.graphics.getDeltaTime();
    }
}
