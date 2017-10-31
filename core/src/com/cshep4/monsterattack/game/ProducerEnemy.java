package com.cshep4.monsterattack.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by CShepherd on 11/08/2017.
 */

public class ProducerEnemy extends Enemy {
    protected long spawnTime = System.currentTimeMillis();

    public ProducerEnemy(int aXPos, int aYPos) {
        super();
        xPos = aXPos;
        yPos = aYPos;
        width = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER;
        height = myApp.getScreenWidth() / Constants.CHARACTER_HEIGHT_DIVIDER;
    }

    public void decisionTree(ArrayList<Enemy> enemies) {
        //IF NOT IN POSITION, MOVE TO POSITION

        if (this.xPos > myApp.getScreenWidth()-this.width) {
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
        int delay = rand.nextInt(Constants.PRODCUER_SPAWN_DELAY_MAX) + Constants.PRODCUER_SPAWN_DELAY_MIN;

        return System.currentTimeMillis() - spawnTime > delay;
    }

    private void produce(ArrayList<Enemy> enemies) {
        int W = myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER;
        int x = this.xPos - W;
        int y = this.yPos;
        Log.v("AI", "Produce");

        this.xVel = 0;
        this.yVel = 0;
        spawnTime = System.currentTimeMillis();

        Random rand = new Random();
        int difficulty = rand.nextInt(4) + 1;

        if (difficulty == 1) {
            if (this instanceof StandardProducer) {
                enemies.add(new Standard(x, y, 1));
            } else {
                enemies.add(new Bomber(x, y, 1));
            }
//			currentWave.setS1(currentWave.getS1()+1);
        } else if (difficulty == 2) {
            if (this instanceof StandardProducer) {
                enemies.add(new Standard(x, y, 2));
            } else {
                enemies.add(new Bomber(x, y, 2));
            }
//			currentWave.setS2(currentWave.getS2()+1);
        } else if (difficulty == 3) {
            if (this instanceof StandardProducer) {
                enemies.add(new Standard(x, y, 3));
            } else {
                enemies.add(new Bomber(x, y, 3));
            }
//			currentWave.setS3(currentWave.getS3()+1);
        } else if (difficulty == 4) {
            if (this instanceof StandardProducer) {
                enemies.add(new Standard(x, y, 4));
            } else {
                enemies.add(new Bomber(x, y, 4));
            }
//			currentWave.setS4(currentWave.getS4()+1);
        } else {
            rand = new Random();
            int type = rand.nextInt(2) + 1;
            if (this instanceof StandardProducer) {
                enemies.add(new Standard(x, y, type));
            } else {
                enemies.add(new Bomber(x, y, type));
            }
        }
    }
}
