package com.cshep4.monsterattack.game.wrapper;

import com.badlogic.gdx.Gdx;

public final class Sound {

	private static com.badlogic.gdx.audio.Sound enemyDie;
	private static com.badlogic.gdx.audio.Sound enemyHit;
	private static com.badlogic.gdx.audio.Sound explosion;
	private static com.badlogic.gdx.audio.Sound gameOver;
	private static com.badlogic.gdx.audio.Sound mutateBomb;
	private static com.badlogic.gdx.audio.Sound mutateStandard;
	private static com.badlogic.gdx.audio.Sound select;
	private static com.badlogic.gdx.audio.Sound backgroundMusic;
	private static long musicId;

	private Sound() {}

	public static void loadSounds() {
		backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/background_music.mp3"));
		enemyDie = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_hit_die.wav"));
		enemyHit = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_hit_dont_die.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav"));
		gameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.wav"));
		mutateBomb = Gdx.audio.newSound(Gdx.files.internal("sounds/mutate_bomb.wav"));
		mutateStandard = Gdx.audio.newSound(Gdx.files.internal("sounds/mutate_standard.wav"));
		select = Gdx.audio.newSound(Gdx.files.internal("sounds/select.wav"));
	}

	public static void playBackgroundMusic() {
		musicId = backgroundMusic.play(0.5f);      // plays the sound a second time, this is treated as a different instance
		backgroundMusic.setLooping(musicId, true); // keeps the sound looping
	}

	public static void stopBackgroundMusic() {
		backgroundMusic.stop(musicId);
	}
	
	public static void playEnemyDie() {
		enemyDie.play(1.0f);
	}

	public static void playEnemyHit() {
		enemyHit.play(1.0f);
	}	
	
	public static void playExplode() {
		explosion.play(1.0f);
	}

	public static void playGameOver() {
		gameOver.play(1.0f);
	}	
	
	public static void playMutateBomb() {
		mutateBomb.play(1.0f);
	}	
	
	public static void playMutateStandard() {
		mutateStandard.play(1.0f);
	}

	public static void playSelect() {
		select.play(1.0f);
	}

	public static void dispose() {
		backgroundMusic.dispose();
		enemyDie.dispose();
		enemyHit.dispose();
		explosion.dispose();
		gameOver.dispose();
		mutateBomb.dispose();
		mutateStandard.dispose();
		select.dispose();
	}
}
