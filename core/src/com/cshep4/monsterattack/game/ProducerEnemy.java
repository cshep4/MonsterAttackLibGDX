package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.game.Constants.CHARACTER_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.Constants.PRODCUER_SPAWN_DELAY_MAX;
import static com.cshep4.monsterattack.game.Constants.PRODCUER_SPAWN_DELAY_MIN;

public abstract class ProducerEnemy extends Enemy {
    private long spawnTime = System.currentTimeMillis();
    private long producingTime = 0;

    private static final int PRODUCE_DURATION = 250;

    public ProducerEnemy(Rectangle rectangle, Texture texture, int frameCols, int frameRows) {
        super(rectangle, texture, frameCols, frameRows);
        health = 300;
    }

    public void decisionTree(ArrayList<Enemy> enemies) {
        //IF NOT IN POSITION, MOVE TO POSITION

        if (getRectangle().getX() > getScreenXMax()-getRectangle().getWidth()) {
            moveForward();
            //IF READY TO SPAWN, PRODUCE ENEMY
        } else if (checkSpawnDelay()) {
            produce(enemies);
        } else {
            //WAIT
            this.xVel = 0;
            this.yVel = 0;
        }

        this.update();
    }

    private boolean checkSpawnDelay() {
        Random rand = new Random();
        int delay = rand.nextInt(PRODCUER_SPAWN_DELAY_MAX) + PRODCUER_SPAWN_DELAY_MIN;

        return System.currentTimeMillis() - spawnTime > delay;
    }

    private void produce(ArrayList<Enemy> enemies) {
        if (producingTime == 0) { // is the enemy already in the processing of producing?
            //set time of produce method beginning
            producingTime = System.currentTimeMillis();

            //set sprite
            setProducingSprite();
        } else {
            if (System.currentTimeMillis() - producingTime > PRODUCE_DURATION) { // has the enemy finished producing?
                float width = getScreenXMax() / CHARACTER_WIDTH_DIVIDER;
                float x = getRectangle().getX() - width;
                float y = getRectangle().getY();
                Gdx.app.log("AI", "Produce");

                this.xVel = 0;
                this.yVel = 0;
                spawnTime = System.currentTimeMillis();

                Random rand = new Random();
                int difficulty = rand.nextInt(4) + 1;

                if (difficulty == 1) {
                    if (this instanceof StandardProducer) {
                        enemies.add(Create.standard(x, y, 1));
                    } else {
                        enemies.add(Create.bomber(x, y, 1));
                    }
//			currentWave.setS1(currentWave.getS1()+1);
                } else if (difficulty == 2) {
                    if (this instanceof StandardProducer) {
                        enemies.add(Create.standard(x, y, 2));
                    } else {
                        enemies.add(Create.bomber(x, y, 2));
                    }
//			currentWave.setS2(currentWave.getS2()+1);
                } else if (difficulty == 3) {
                    if (this instanceof StandardProducer) {
                        enemies.add(Create.standard(x, y, 3));
                    } else {
                        enemies.add(Create.bomber(x, y, 3));
                    }
//			currentWave.setS3(currentWave.getS3()+1);
                } else if (difficulty == 4) {
                    if (this instanceof StandardProducer) {
                        enemies.add(Create.standard(x, y, 4));
                    } else {
                        enemies.add(Create.bomber(x, y, 4));
                    }
//			currentWave.setS4(currentWave.getS4()+1);
                } else {
                    rand = new Random();
                    int type = rand.nextInt(2) + 1;
                    if (this instanceof StandardProducer) {
                        enemies.add(Create.standard(x, y, type));
                    } else {
                        enemies.add(Create.bomber(x, y, type));
                    }
                }

                //reset producing time
                producingTime = 0;

                //change sprite back to normal
                resetSprite();
            }
        }
    }

    protected abstract void setProducingSprite();

    protected abstract void resetSprite();

    public void update(Player player, ArrayList<Enemy> enemies){
        decisionTree(enemies);

        super.update();

        //check if player has collided, if so KILL!!!
        if (this.getRectangle().overlaps(player.getRectangle())) {
            Gdx.app.log("Death", "COLLIDED!");
            player.setHealth(player.getHealth()-100);
        }
    }
}
