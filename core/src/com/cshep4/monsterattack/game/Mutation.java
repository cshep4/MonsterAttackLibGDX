package com.cshep4.monsterattack.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;


public class Mutation {
	private long mutateTime = 0;//System.currentTimeMillis();
	MyApp myApp = MyApp.getInstance();
	Sound sound = new Sound();

	public void mutate(ArrayList<Enemy> enemies) {
		int x;
		int y;
		int type;
		if (checkMutateDelay()) {
			if (enemies != null) {
				for (int enemyLoop =0; enemyLoop < enemies.size(); enemyLoop++){
					if ((enemies.get(enemyLoop) != null) && (enemies.get(enemyLoop).getType() < 4)) {
						type = enemies.get(enemyLoop).getType() + 1;
						Gdx.app.log("Mutation", enemies.get(enemyLoop).getType() + "->" + type);
						sound.playMutateStandard();
						x = enemies.get(enemyLoop).getXPos();
						y = enemies.get(enemyLoop).getYPos();
						type = enemies.get(enemyLoop).getType() + 1;

						if (enemies.get(enemyLoop) instanceof Standard) {
							enemies.remove(enemyLoop);
							enemies.add(enemyLoop, new Standard(x, y, type));
						} else {
							enemies.remove(enemyLoop);
							enemies.add(enemyLoop, new Bomber(x, y, type));
						}
						mutateTime = System.currentTimeMillis();
						return;
					}
				}
			}
		}
	}
	
	private boolean checkMutateDelay() {
		Random rand = new Random();
		int delay = rand.nextInt(Constants.MUTATE_DELAY_MAX) + Constants.MUTATE_DELAY_MIN;

        return System.currentTimeMillis() - mutateTime > delay;
	}	

}
