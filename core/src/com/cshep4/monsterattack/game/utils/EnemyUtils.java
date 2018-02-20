package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.ProducerEnemy;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;

import static com.cshep4.monsterattack.game.constants.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.BOMBER;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_PRODUCE;
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

public class EnemyUtils {
    private EnemyUtils() {}

    public static void setAbility(RunningEnemy runningEnemy) {
        if (runningEnemy instanceof Bomber) {
            setBomberAbility(runningEnemy);
        } else if (runningEnemy instanceof Standard) {
            setStandardAbility(runningEnemy);
        }
    }

    private static void setBomberAbility(RunningEnemy runningEnemy) {
        switch (runningEnemy.getLevel()) {
            case 1:
                runningEnemy.setHealth(1);
                break;
            case 2:
                runningEnemy.setCanShoot(true);
                runningEnemy.setHealth(1);
                break;
            case 3:
                runningEnemy.setCanShoot(true);
                runningEnemy.setHealth(2);
                break;
            case 4:
                runningEnemy.setCanShoot(true);
                runningEnemy.setCanShootBombs(true);
                runningEnemy.setHealth(2);
                break;
            default:
                break;
        }
    }

    private static void setStandardAbility(RunningEnemy runningEnemy) {
        switch (runningEnemy.getLevel()) {
            case 1:
                runningEnemy.setHealth(1);
                break;
            case 2:
                runningEnemy.setCanShoot(true);
                runningEnemy.setHealth(1);
                break;
            case 3:
                runningEnemy.setCanShoot(true);
                runningEnemy.setCanDodge(true);
                runningEnemy.setHealth(2);
                break;
            case 4:
                runningEnemy.setCanShoot(true);
                runningEnemy.setCanDodge(true);
                runningEnemy.setCanShield(true);
                runningEnemy.setHealth(1);
                runningEnemy.setShieldHealth(2);
                break;
            default:
                break;
        }
    }

    public static String getStandardSpriteLeft(int level) {
        switch (level) {
            case 1:
                return S1_SPRITE_MOVE_LEFT;
            case 2:
                return S2_SPRITE_MOVE_LEFT;
            case 3:
                return S3_SPRITE_MOVE_LEFT;
            case 4:
                return S4_SPRITE_MOVE_LEFT;
            default:
                return S1_SPRITE_MOVE_LEFT;
        }
    }

    public static String getStandardSpriteRight(int level) {
        switch (level) {
            case 1:
                return S1_SPRITE_MOVE_RIGHT;
            case 2:
                return S2_SPRITE_MOVE_RIGHT;
            case 3:
                return S3_SPRITE_MOVE_RIGHT;
            case 4:
                return S4_SPRITE_MOVE_RIGHT;
            default:
                return S1_SPRITE_MOVE_RIGHT;
        }
    }

    public static String getBomberSprite(int level) {
        switch (level) {
            case 1:
                return B1_SPRITE_MOVE;
            case 2:
                return B2_SPRITE_MOVE;
            case 3:
                return B3_SPRITE_MOVE;
            case 4:
                return B4_SPRITE_MOVE;
            default:
                return B1_SPRITE_MOVE;
        }
    }

    public static String getProducerIdleSprite(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_SPRITE_IDLE;
        } else {
            return SP_SPRITE_IDLE;
        }
    }

    public static String getProducerProducingSprite(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_SPRITE_PRODUCE;
        } else {
            return SP_SPRITE_PRODUCE;
        }
    }

    public static int getProducerIdleCols(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_IDLE_COLS;
        } else {
            return SP_IDLE_COLS;
        }
    }

    public static int getProducerIdleRows(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_IDLE_ROWS;
        } else {
            return SP_IDLE_ROWS;
        }
    }

    public static int getProducerProducingCols(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_PRODUCING_COLS;
        } else {
            return SP_PRODUCING_COLS;
        }
    }

    public static int getProducerProducingRows(ProducerEnemy producerEnemy) {
        if (producerEnemy instanceof BomberProducer) {
            return BP_PRODUCING_ROWS;
        } else {
            return SP_PRODUCING_ROWS;
        }
    }
}
