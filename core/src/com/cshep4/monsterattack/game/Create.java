package com.cshep4.monsterattack.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.cshep4.monsterattack.game.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.Constants.S1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.Constants.S4_SPRITE_MOVE;

public final class Create {
    private Create () {}

    public static Player player(int x, int y) {
        Rectangle rectangle = new Rectangle().setPosition(x,y);
        Texture texture = new Texture(Gdx.files.internal(CHARACTER_IDLE));

        return new Player(rectangle, texture);
    }

    public static Bomber bomber(int x, int y, int type) {
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

    public static Standard standard(int x, int y, int type) {
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
}
