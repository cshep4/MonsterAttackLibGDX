package com.cshep4.monsterattack.game;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;


public class Mutation {
	private long mutateTime = 0;//System.currentTimeMillis();
	MyApp myApp = MyApp.getInstance();
	Sound sound = new Sound();
	
//	public void mutate(ArrayList<Standard> aS, ArrayList<Bomber> aB) {
//		int x;
//		int y;
//		if (checkMutateDelay()) {
////			if (aCurrentWave.getS1Mutate()) {
//				if (aS != null) {
//					for (int enemyLoop =0; enemyLoop < aS.size(); enemyLoop++){
//						if ((aS.get(enemyLoop) != null) && (aS.get(enemyLoop).getType() == 1)) {
//							Log.v("Mutation", "S1->S2");
//							sound.playMutateStandard();
//							x = aS.get(enemyLoop).getXPos();
//							y = aS.get(enemyLoop).getYPos();
//							aS.remove(enemyLoop);
//							aS.add(new Standard(x, y,2));
////							aCurrentWave.setS1(aCurrentWave.getS1()-1);
////							aCurrentWave.setS2(aCurrentWave.getS2()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
////			if (aCurrentWave.getS2Mutate()) {
//				if (aS != null) {
//					for (int enemyLoop =0; enemyLoop < aS.size(); enemyLoop++){
//						if ((aS.get(enemyLoop) != null) && (aS.get(enemyLoop).getType() == 2)) {
//							Log.v("Mutation", "S2->S3");
//							sound.playMutateStandard();
//							x = aS.get(enemyLoop).getXPos();
//							y = aS.get(enemyLoop).getYPos();
//							aS.remove(enemyLoop);
//							aS.add(new Standard(x, y,3));
////							aCurrentWave.setS2(aCurrentWave.getS2()-1);
////							aCurrentWave.setS3(aCurrentWave.getS3()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
////			if (aCurrentWave.getS3Mutate()) {
//				if (aS != null) {
//					for (int enemyLoop =0; enemyLoop < aS.size(); enemyLoop++){
//						if ((aS.get(enemyLoop) != null) && (aS.get(enemyLoop).getType() == 3)) {
//							Log.v("Mutation", "S3->S4");
//							sound.playMutateStandard();
//							x = aS.get(enemyLoop).getXPos();
//							y = aS.get(enemyLoop).getYPos();
//							aS.remove(enemyLoop);
//							aS.add(new Standard(x, y,4));
////							aCurrentWave.setS3(aCurrentWave.getS3()-1);
////							aCurrentWave.setS4(aCurrentWave.getS4()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
//		}
//		if (checkMutateDelay()) {
////			if (aCurrentWave.getB1Mutate()) {
//				if (aB != null) {
//					for (int enemyLoop =0; enemyLoop < aB.size(); enemyLoop++){
//						if ((aB.get(enemyLoop) != null) && (aB.get(enemyLoop).getType() == 1)) {
//							Log.v("Mutation", "B1->B2");
//							sound.playMutateBomb();
//							x = aB.get(enemyLoop).getXPos();
//							y = aB.get(enemyLoop).getYPos();
//							aB.remove(enemyLoop);
//							aB.add(new Bomber(x, y,2));
////							aCurrentWave.setB1(aCurrentWave.getB1()-1);
////							aCurrentWave.setB2(aCurrentWave.getB2()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
////			if (aCurrentWave.getB2Mutate()) {
//				if (aB != null) {
//					for (int enemyLoop =0; enemyLoop < aB.size(); enemyLoop++){
//						if ((aB.get(enemyLoop) != null) && (aB.get(enemyLoop).getType() == 2)) {
//							Log.v("Mutation", "B2->B3");
//							sound.playMutateBomb();
//							x = aB.get(enemyLoop).getXPos();
//							y = aB.get(enemyLoop).getYPos();
//							aB.remove(enemyLoop);
//							aB.add(new Bomber(x, y,3));
////							aCurrentWave.setB2(aCurrentWave.getB2()-1);
////							aCurrentWave.setB3(aCurrentWave.getB3()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
////			if (aCurrentWave.getB3Mutate()) {
//				if (aB != null) {
//					for (int enemyLoop =0; enemyLoop < aB.size(); enemyLoop++){
//						if ((aB.get(enemyLoop) != null) && (aB.get(enemyLoop).getType() == 3)) {
//							Log.v("Mutation", "B3->B4");
//							sound.playMutateBomb();
//							x = aB.get(enemyLoop).getXPos();
//							y = aB.get(enemyLoop).getYPos();
//							aB.remove(enemyLoop);
//							aB.add(new Bomber(x, y,4));
////							aCurrentWave.setB3(aCurrentWave.getB3()-1);
////							aCurrentWave.setB4(aCurrentWave.getB4()+1);
//							enemyLoop--;
//						}
//					}
//				}
////			}
//			mutateTime = System.currentTimeMillis();
//		}
//	}

	public void mutate(ArrayList<Enemy> enemies) {
		int x;
		int y;
		int type;
		if (checkMutateDelay()) {
			if (enemies != null) {
				for (int enemyLoop =0; enemyLoop < enemies.size(); enemyLoop++){
					if ((enemies.get(enemyLoop) != null) && (enemies.get(enemyLoop).getType() < 4)) {
						type = enemies.get(enemyLoop).getType() + 1;
						Log.v("Mutation", enemies.get(enemyLoop).getType() + "->" + type);
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
