package com.cshep4.monsterattack.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.Constants.PAUSE_BUTTON;
import static com.cshep4.monsterattack.game.Constants.S1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.SHOOT_BUTTON;

public final class Create {
    private Create () {}

    public static Player player(float x, float y) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        Texture texture = new Texture(Gdx.files.internal(CHARACTER_IDLE));

        return new Player(rectangle, texture);
    }

    public static Bomber bomber(float x, float y, int type) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        String sprite;

        switch (type) {
            case 1:
                sprite = B1_SPRITE_MOVE;
                break;
            case 2:
                sprite = B2_SPRITE_MOVE;
                break;
            case 3:
                sprite = B3_SPRITE_MOVE;
                break;
            case 4:
                sprite = B4_SPRITE_MOVE;
                break;
            default:
                sprite = B1_SPRITE_MOVE;
                break;
        }

        Texture texture = new Texture(Gdx.files.internal(sprite));

        return new Bomber(rectangle, texture, type);
    }

    public static Standard standard(float x, float y, int type) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        String sprite;

        switch (type) {
            case 1:
                sprite = S1_SPRITE_MOVE;
                break;
            case 2:
                sprite = S2_SPRITE_MOVE;
                break;
            case 3:
                sprite = S3_SPRITE_MOVE;
                break;
            case 4:
                sprite = S4_SPRITE_MOVE;
                break;
            default:
                sprite = S1_SPRITE_MOVE;
                break;
        }

        Texture texture = new Texture(Gdx.files.internal(sprite));

        return new Standard(rectangle, texture, type);
    }

    public static BomberProducer bomberProducer(float x, float y) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        Texture texture = new Texture(Gdx.files.internal(BP_SPRITE_IDLE));

        return new BomberProducer(rectangle, texture);
    }

    public static StandardProducer standardProducer(float x, float y) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        Texture texture = new Texture(Gdx.files.internal(BP_SPRITE_IDLE));

        return new StandardProducer(rectangle, texture);
    }

    public static ShootButton shootButton(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        Texture texture = new Texture(Gdx.files.internal(SHOOT_BUTTON));

        return new ShootButton(rectangle, texture);
    }

    public static PauseButton pauseButton(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        Texture texture = new Texture(Gdx.files.internal(PAUSE_BUTTON));

        return new PauseButton(rectangle, texture);
    }

    public static Bullet bullet(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        Texture texture = new Texture(Gdx.files.internal(SHOOT_BUTTON));

        return new Bullet(rectangle, texture);
    }

    public static Bomb bomb(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        Texture texture = new Texture(Gdx.files.internal(SHOOT_BUTTON));

        return new Bomb(rectangle, texture);
    }
}
