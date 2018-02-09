package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.core.ProducerAI;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import java.util.List;
import java.util.Random;

import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MIN;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerIdleCols;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerIdleRows;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerIdleSprite;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerProducingCols;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerProducingRows;
import static com.cshep4.monsterattack.game.utils.EnemyUtils.getProducerProducingSprite;

@EqualsAndHashCode
public abstract class ProducerEnemy extends Character implements ProducerAI {
    private static final String PRODUCER_AI = "ProducerAI";
    private long spawnTime = System.currentTimeMillis();
    private long producingTime = 0;

    private static final int PRODUCE_DURATION = 250;

    protected ProducerEnemy(Rectangle rectangle, Texture texture, int idleCols, int idleRows) {
        super(rectangle, texture, idleCols, idleRows);
        health = 300;
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
        Random rand = new Random();
        int delay = rand.nextInt(PRODCUER_SPAWN_DELAY_MAX) + PRODCUER_SPAWN_DELAY_MIN;

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
            Gdx.app.log("RunningAI", "Produce");

            xVel = 0;
            yVel = 0;
            spawnTime = System.currentTimeMillis();

            Random rand = new Random();
            int level = rand.nextInt(4) + 1;

            runningEnemies.add(createEnemy(x, y, level));

            //reset producing time
            producingTime = 0;

            //change sprite back to normal
            resetSprite();
        }
    }

    private RunningEnemy createEnemy(float x, float y, int level) {
        if (this instanceof StandardProducer) {
            return Standard.create(x, y, level);
        } else {
            return Bomber.create(x, y, level);
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
        Gdx.app.log(PRODUCER_AI,"Move Forward");
        xVel = -ENEMY_SPEED;
    }

    @Override
    public void checkPlayerHasBeenKilled(Player player) {
        //check if player has collided, if so KILL!!!
        if (getRectangle().overlaps(player.getRectangle())) {
            Gdx.app.log("Death", "COLLIDED!");
            player.setHealth(player.getHealth()-100);
        }
    }

    private void setProducingSprite() {
        Texture texture = TextureFactory.create(getProducerProducingSprite(this));
        changeAnimation(texture, getProducerProducingCols(this), getProducerProducingRows(this));
    }

    private void resetSprite() {
        Texture texture = TextureFactory.create(getProducerIdleSprite(this));
        changeAnimation(texture, getProducerIdleCols(this), getProducerIdleRows(this));
    }
}
