package com.cshep4.monsterattack.game;

public class Constants {

	private Constants() { }

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public static final int CHARACTER_WIDTH_DIVIDER = 14;
	public static final int CHARACTER_HEIGHT_DIVIDER = 10;

	public static final int BULLET_SIZE_DIVIDER = 5;

	public static final int BUTTON_SIZE_DIVIDER = 10;

	public static final int BULLET_SPEED = 15;

	public static final int ENEMY_SPEED = 10;
	public static final int PLAYER_SPEED = 10;

	public static final int SHOOT_DELAY = 1000;

	public static final int SPAWN_DELAY_MIN = 1500;
	public static final int SPAWN_DELAY_MAX = 2500;

	public static final int PRODCUER_SPAWN_DELAY_MIN = 1500;
	public static final int PRODCUER_SPAWN_DELAY_MAX = 2500;

	public static final int MUTATE_DELAY_MIN = 3000;
	public static final int MUTATE_DELAY_MAX = 4000;
	
	//sprite dividers
	public static final int CHARACTER_IDLE_DIVIDER = 2;
	public static final int CHARACTER_GOT_HIT_DIVIDER = 1;
	public static final int CHARACTER_RUNNING_DIVIDER = 6;
	public static final int S_1_GOT_HIT_DIVIDER = 1;
	public static final int S_MOVE_DIVIDER = 2;
	public static final int S_GOT_HIT_DIVIDER = 2;
	public static final int S_SHIELD_DIVIDER = 2;
	public static final int B_GOT_HIT_DIVIDER = 2;
	public static final int B_MOVE_DIVIDER = 7;
	public static final int S_P_IDLE_DIVIDER = 8;
	public static final int S_P_PRODUCE_DIVIDER = 2;
	public static final int B_P_IDLE_DIVIDER = 2;
	public static final int B_P_PRODUCE_DIVIDER = 1;

	//Define the frame rate
	public static final int FRAME_RATE = 60;
}
