package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.ai.ProducerAI;

import java.util.List;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.BOMBER;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.constants.Constants.STANDARD;
import static com.cshep4.monsterattack.game.utils.DifficultyUtils.produceEnemyBasedDifficulty;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleCols;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleRows;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingCols;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingRows;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingSprite;
import static com.cshep4.monsterattack.game.utils.Utils.getRandomNumber;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;

@EqualsAndHashCode(callSuper=true)
public abstract class ProducerEnemy extends Character implements ProducerAI {
    private final String logName = getClass().getSimpleName();
    private long spawnTime = System.currentTimeMillis();
    private long producingTime = 0;

    private static final int PRODUCE_DURATION = 250;

    protected ProducerEnemy(Rectangle rectangle, String texture, int idleCols, int idleRows) {
        super(rectangle, texture, idleCols, idleRows);
        health = 3;
    }

    public void decisionTree(List<RunningEnemy> runningEnemies) {
        if (isProducerNotInPosition()) {
            moveForward();
            //IF READY TO SPAWN, PRODUCE ENEMY
        } else if (checkSpawnDelay()) {
            produce(runningEnemies);
        } else {
            //WAIT
            xVel = 0;
            yVel = 0;
        }

        update();
    }

    private boolean isProducerNotInPosition() {
        return getX() > getScreenXMax() - getWidth();
    }

    private boolean checkSpawnDelay() {
        int delay = getRandomNumber(PRODCUER_SPAWN_DELAY_MIN, PRODCUER_SPAWN_DELAY_MAX);

        return System.currentTimeMillis() - spawnTime > delay;
    }

    private void produce(List<RunningEnemy> runningEnemies) {
        if (hasStartedProducing()) {
            processProduction(runningEnemies);
        } else {
            //set time of produce method beginning
            producingTime = System.currentTimeMillis();

            //set sprite
            setProducingSprite();
        }
    }

    private void processProduction(List<RunningEnemy> runningEnemies) {
        if (isReadyToReleaseEnemy()) {
            float width = getScreenXMax() / CHARACTER_WIDTH_DIVIDER;
            float x = getX() - width;
            float y = getY();
            Gdx.app.log(logName, "Produce!");

            xVel = 0;
            yVel = 0;
            spawnTime = System.currentTimeMillis();

            int enemyType = getEnemyType();

            runningEnemies.add(produceEnemyBasedDifficulty(x, y, enemyType));

            //reset producing time
            producingTime = 0;

            //change sprite back to normal
            resetSprite();
        }
    }

    private int getEnemyType() {
        if (this instanceof StandardProducer) {
            return STANDARD;
        } else {
            return BOMBER;
        }
    }

    private boolean isReadyToReleaseEnemy() {

        return System.currentTimeMillis() - producingTime > PRODUCE_DURATION;
    }

    private boolean hasStartedProducing() {
        return producingTime != 0;
    }

    public void update(Player player, List<RunningEnemy> runningEnemies){
        decisionTree(runningEnemies);

        checkPlayerHasBeenKilled(player);
    }

    @Override
    public void moveForward() {
        Gdx.app.log(logName,"Move Forward");
        xVel = -ENEMY_SPEED;
    }

    @Override
    public void checkPlayerHasBeenKilled(Player player) {
        //check if player has collided, if so KILL!!!
        if (hasCollided(this, player)) {
            Gdx.app.log(logName, "COLLIDED!");
            player.loseLife();
            kill();
        }
    }

    private void setProducingSprite() {
        changeAnimation(getProducerProducingSprite(this), getProducerProducingCols(this), getProducerProducingRows(this));
    }

    private void resetSprite() {
        changeAnimation(getProducerIdleSprite(this), getProducerIdleCols(this), getProducerIdleRows(this));
    }
}
