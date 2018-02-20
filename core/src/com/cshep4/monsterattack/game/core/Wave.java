package com.cshep4.monsterattack.game.core;

import lombok.Data;

@Data
public class Wave {
	private int id;
	private int s1;
	private boolean s1Mutate;
	private int s2;
	private boolean s2Mutate;
	private int s3;
	private boolean s3Mutate;
	private int s4;
	private int sProducer;
	private int b1;
	private boolean b1Mutate;
	private int b2;
	private boolean b2Mutate;
	private int b3;
	private boolean b3Mutate;
	private int b4;
	private int bProducer;
}