package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public abstract class ProducerEnemy extends Enemy {
    protected long spawnTime = System.currentTimeMillis();

    public ProducerEnemy(Rectangle rectangle, Texture texture) {
        super(rectangle, texture);
//        getRectangle().setX(xPos);
//        getRectangle().setY(yPos);
        getRectangle().setWidth(myApp.getScreenWidth() / Constants.CHARACTER_WIDTH_DIVIDER);
        getRectangle().setHeight(myApp.getScreenWidth() / Constants.CHARACTER_HEIGHT_DIVIDER);
    }

    public void decisionTree(ArrayList<Enemy> enemies) {
        //IF NOT IN POSITION, MOVE TO POSITION

        if (getRectangle().getX() > myApp.getScreenWidth()-getRectangle().getWidth()) {
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
        int x = (int) getRectangle().getX() - W;
        int y = (int) getRectangle().getY();
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
    }
}
