package com.cshep4.monsterattack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public final class Sounds {

	private static Sound enemyDie = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_hit_die.wav"));
	private static Sound enemyHit = Gdx.audio.newSound(Gdx.files.internal("sounds/enemy_hit_dont_die.wav"));
	private static Sound explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav"));
	private static Sound gameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.wav"));
	private static Sound mutateBomb = Gdx.audio.newSound(Gdx.files.internal("sounds/mutate_bomb.wav"));
	private static Sound mutateStandard = Gdx.audio.newSound(Gdx.files.internal("sounds/mutate_standard.wav"));
	private static Sound select = Gdx.audio.newSound(Gdx.files.internal("sounds/select.wav"));
	
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
}
