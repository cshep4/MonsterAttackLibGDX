package com.cshep4.monsterattack.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.GameObject;

import java.util.Random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt((max + 1) - min) + min;
    }

    public static boolean hasCollided(GameObject obj1, GameObject obj2) {
        return obj1.getRectangle().overlaps(obj2.getRectangle());
    }

    public static boolean hasCollided(Rectangle obj1, GameObject obj2) {
        return obj1.overlaps(obj2.getRectangle());
    }
}