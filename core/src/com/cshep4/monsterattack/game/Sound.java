package com.cshep4.monsterattack.game;

import android.app.Application;
//import android.media.AudioManager;
import android.media.MediaPlayer;
//import android.media.SoundPool;

public class Sound {
	
	MyApp myApp = MyApp.getInstance();
	
	public void playEnemyDie() {
		if (myApp.getSoundOn()) {
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.enemy_hit_die);
		    mp.start();
		}
	}

	public void playEnemyHit() {
		if (myApp.getSoundOn()) {		
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.enemy_hit_dont_die);
		    mp.start();
		}
	}	
	
	public void playExplode() {
		if (myApp.getSoundOn()) {		
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.explode);
	    	mp.start();
		}
	}

	public void playGameOver() {
		if (myApp.getSoundOn()) {
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.game_over);
			mp.start();
		}
	}	
	
	public void playMutateBomb() {
		if (myApp.getSoundOn()) {		
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.mutate_bomb);
		    mp.start();
		}
	}	
	
	public void playMutateStandard() {
		if (myApp.getSoundOn()) {		
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.mutate_standard);
		    mp.start();
		}
	}

	public void playSelect() {
		if (myApp.getSoundOn()) {		
			MediaPlayer mp = MediaPlayer.create(myApp.appContext, R.raw.select);
		    mp.start();
		}
	}		
}
