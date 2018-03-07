package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.ProducerEnemy;

import lombok.experimental.UtilityClass;

import static com.cshep4.monsterattack.game.constants.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_PRODUCE;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_PRODUCE;

@UtilityClass
public class SpriteUtils {
    public static String getStandardSpriteLeft(int level) {
        String sprite;

        switch (level) {
            case 1:
                sprite = S1_SPRITE_MOVE_LEFT;
                break;
            case 2:
                sprite = S2_SPRITE_MOVE_LEFT;
                break;
            case 3:
                sprite = S3_SPRITE_MOVE_LEFT;
                break;
            case 4:
                sprite = S4_SPRITE_MOVE_LEFT;
                break;
            default:
                sprite = S1_SPRITE_MOVE_LEFT;
                break;
        }

        return sprite;
    }

    public static String getStandardSpriteRight(int level) {
        String sprite;

        switch (level) {
            case 1:
                sprite = S1_SPRITE_MOVE_RIGHT;
                break;
            case 2:
                sprite = S2_SPRITE_MOVE_RIGHT;
                break;
            case 3:
                sprite = S3_SPRITE_MOVE_RIGHT;
                break;
            case 4:
                sprite = S4_SPRITE_MOVE_RIGHT;
                break;
            default:
                sprite = S1_SPRITE_MOVE_RIGHT;
                break;
        }

        return sprite;
    }

    public static String getBomberSprite(int level) {
        String sprite;

        switch (level) {
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

        return sprite;
    }

    public static String getProducerIdleSprite(ProducerEnemy producerEnemy) {
        String sprite;

        if (producerEnemy instanceof BomberProducer) {
            sprite = BP_SPRITE_IDLE;
        } else {
            sprite = SP_SPRITE_IDLE;
        }

        return sprite;
    }

    public static String getProducerProducingSprite(ProducerEnemy producerEnemy) {
        String sprite;

        if (producerEnemy instanceof BomberProducer) {
            sprite = BP_SPRITE_PRODUCE;
        } else {
            sprite = SP_SPRITE_PRODUCE;
        }

        return sprite;
    }

    public static int getProducerIdleCols(ProducerEnemy producerEnemy) {
        int result;

        if (producerEnemy instanceof BomberProducer) {
            result = BP_IDLE_COLS;
        } else {
            result = SP_IDLE_COLS;
        }

        return result;
    }

    public static int getProducerIdleRows(ProducerEnemy producerEnemy) {
        int result;

        if (producerEnemy instanceof BomberProducer) {
            result = BP_IDLE_ROWS;
        } else {
            result = SP_IDLE_ROWS;
        }

        return result;
    }

    public static int getProducerProducingCols(ProducerEnemy producerEnemy) {
        int result;

        if (producerEnemy instanceof BomberProducer) {
            result = BP_PRODUCING_COLS;
        } else {
            result = SP_PRODUCING_COLS;
        }

        return result;
    }

    public static int getProducerProducingRows(ProducerEnemy producerEnemy) {
        int result;

        if (producerEnemy instanceof BomberProducer) {
            result = BP_PRODUCING_ROWS;
        } else {
            result = SP_PRODUCING_ROWS;
        }

        return result;
    }

    public static String getPlayerIdleSprite(int remainingShieldTime) {
        if (remainingShieldTime > 0) {
            return CHARACTER_IDLE_SHIELD;
        } else {
            return CHARACTER_IDLE;
        }
    }

    public static String getPlayerLeftSprite(int remainingShieldTime) {
        if (remainingShieldTime > 0) {
            return CHARACTER_MOVE_LEFT_SHIELD;
        } else {
            return CHARACTER_MOVE_LEFT;
        }
    }

    public static String getPlayerRightSprite(int remainingShieldTime) {
        if (remainingShieldTime > 0) {
            return CHARACTER_MOVE_RIGHT_SHIELD;
        } else {
            return CHARACTER_MOVE_RIGHT;
        }
    }
}
