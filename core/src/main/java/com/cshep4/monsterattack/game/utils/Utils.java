package com.cshep4.monsterattack.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.core.GameObject;

import java.util.Random;
import java.util.function.BiPredicate;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.game.constants.Constants.SCREEN_X_MAX;

@UtilityClass
public class Utils {
    private static final Random RANDOM = new Random();
    public static final BiPredicate<Bullet, Bullet> HAS_BULLET_SHOT_BOMB = (bullet1, bullet2) -> (bullet1 instanceof Bomb || bullet2 instanceof Bomb) && hasCollided(bullet2, bullet1);

    public static int getRandomNumber(int min, int max) {
        return RANDOM.nextInt((max + 1) - min) + min;
    }

    public static boolean hasCollided(GameObject obj1, GameObject obj2) {
        return obj1.getRectangle().overlaps(obj2.getRectangle());
    }

    public static boolean hasCollided(Rectangle obj1, GameObject obj2) {
        return obj1.overlaps(obj2.getRectangle());
    }

    public static float getTextWidth(BitmapFont font, String text) {
        return new GlyphLayout(font, text).width;
    }

    public static float getTextHeight(BitmapFont font, String text) {
        return new GlyphLayout(font, text).height;
    }

    public static float getScreenXMax() {
        return SCREEN_X_MAX;
    }

    public static float getScreenYMax() {
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        float ratio = width / height;

        return SCREEN_X_MAX / ratio;
    }
}