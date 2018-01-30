package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.constants.Constants.PRODCUER_SPAWN_DELAY_MIN;

public abstract class ProducerEnemy extends Enemy {
    private long spawnTime = System.currentTimeMillis();
    private long producingTime = 0;

    private static final int PRODUCE_DURATION = 250;

    protected ProducerEnemy(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
        health = 300;
    }

    public void decisionTree(List<Enemy> enemies) {
        if (producerNotInPosition()) {
            moveForward();
            //IF READY TO SPAWN, PRODUCE ENEMY
        } else if (checkSpawnDelay()) {
            produce(enemies);
        } else {
            //WAIT
            xVel = 0;
            yVel = 0;
        }

        update();
    }

    private boolean producerNotInPosition() {
        return getRectangle().getX() > getScreenXMax()-getRectangle().getWidth();
    }

    private boolean checkSpawnDelay() {
        Random rand = new Random();
        int delay = rand.nextInt(PRODCUER_SPAWN_DELAY_MAX) + PRODCUER_SPAWN_DELAY_MIN;

        return System.currentTimeMillis() - spawnTime > delay;
    }

    private void produce(List<Enemy> enemies) {
        if (startedProducing()) {
            processProduction(enemies);
        } else {
            //set time of produce method beginning
            producingTime = System.currentTimeMillis();

            //set sprite
            setProducingSprite();
        }
    }

    private void processProduction(List<Enemy> enemies) {
        if (readyToReleaseEnemy()) {
            float width = getScreenXMax() / CHARACTER_WIDTH_DIVIDER;
            float x = getRectangle().getX() - width;
            float y = getRectangle().getY();
            Gdx.app.log("AI", "Produce");

            xVel = 0;
            yVel = 0;
            spawnTime = System.currentTimeMillis();

            Random rand = new Random();
            int level = rand.nextInt(4) + 1;

            enemies.add(createEnemy(x, y, level));

            //reset producing time
            producingTime = 0;

            //change sprite back to normal
            resetSprite();
        }
    }

    private Enemy createEnemy(float x, float y, int level) {
        if (this instanceof StandardProducer) {
            return Standard.create(x, y, level);
        } else {
            return Bomber.create(x, y, level);
        }
    }

    private boolean readyToReleaseEnemy() {
        return System.currentTimeMillis() - producingTime > PRODUCE_DURATION;
    }

    private boolean startedProducing() {
        return producingTime != 0;
    }

    protected abstract void setProducingSprite();

    protected abstract void resetSprite();

    public void update(Player player, List<Enemy> enemies){
        decisionTree(enemies);

        super.update();

        checkPlayerDeath(player);
    }
}
