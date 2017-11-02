package com.cshep4.monsterattack.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;


public final class Mutation {
	private static long mutateTime = 0;//System.currentTimeMillis();
	MyApp myApp = MyApp.getInstance();

	public static void mutate(ArrayList<Enemy> enemies) {
		int x;
		int y;
		int type;
		if (checkMutateDelay()) {
			if (enemies != null) {
				for (int enemyLoop =0; enemyLoop < enemies.size(); enemyLoop++){
					if ((enemies.get(enemyLoop) != null) && (enemies.get(enemyLoop).getType() < 4)) {
						type = enemies.get(enemyLoop).getType() + 1;
						Gdx.app.log("Mutation", enemies.get(enemyLoop).getType() + "->" + type);
						Sounds.playMutateStandard();
						x = (int) enemies.get(enemyLoop).getRectangle().getX();
						y = (int) enemies.get(enemyLoop).getRectangle().getY();
						type = enemies.get(enemyLoop).getType() + 1;

						if (enemies.get(enemyLoop) instanceof Standard) {
							enemies.remove(enemyLoop);
							enemies.add(enemyLoop, Create.standard(x, y, type));
						} else {
							enemies.remove(enemyLoop);
							enemies.add(enemyLoop, Create.bomber(x, y, type));
						}
						mutateTime = System.currentTimeMillis();
						return;
					}
				}
			}
		}
	}
	
	private static boolean checkMutateDelay() {
		Random rand = new Random();
		int delay = rand.nextInt(Constants.MUTATE_DELAY_MAX) + Constants.MUTATE_DELAY_MIN;

        return System.currentTimeMillis() - mutateTime > delay;
	}	

}
