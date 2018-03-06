package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.RunningEnemy;
import com.cshep4.monsterattack.game.character.Standard;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnemyUtils {
    public static void setAbility(RunningEnemy runningEnemy) {
        if (runningEnemy instanceof Bomber) {
            setBomberAbility(runningEnemy);
        } else if (runningEnemy instanceof Standard) {
            setStandardAbility(runningEnemy);
        }
    }

    private void setBomberAbility(RunningEnemy runningEnemy) {
        switch (runningEnemy.getLevel()) {
            case 1:
                runningEnemy.setHealth(1);
                break;
            case 2:
                runningEnemy.setCanShoot(true).setHealth(1);
                break;
            case 3:
                runningEnemy.setCanShoot(true).setHealth(2);
                break;
            case 4:
                runningEnemy.setCanShoot(true).setCanShootBombs(true).setHealth(2);
                break;
            default:
                break;
        }
    }

    private void setStandardAbility(RunningEnemy runningEnemy) {
        switch (runningEnemy.getLevel()) {
            case 1:
                runningEnemy.setHealth(1);
                break;
            case 2:
                runningEnemy.setCanShoot(true).setHealth(1);
                break;
            case 3:
                runningEnemy.setCanShoot(true).setCanDodge(true).setHealth(2);
                break;
            case 4:
                runningEnemy.setCanShoot(true).setCanDodge(true).setCanShield(true).setShieldHealth(2).setHealth(1);
                break;
            default:
                break;
        }
    }

}
