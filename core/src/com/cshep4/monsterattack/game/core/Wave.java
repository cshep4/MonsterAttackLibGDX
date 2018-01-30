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
	
	public Wave() {
		
	}
	
	public Wave(Wave wave){
		id = wave.getId();
		s1 = wave.getS1();
		s1Mutate = wave.isS1Mutate();
		s2 = wave.getS2();
		s2Mutate = wave.isS2Mutate();
		s3 = wave.getS3();
		s3Mutate = wave.isS3Mutate();
		s4 = wave.getB4();
		sProducer = wave.getSProducer();
		b1 = wave.getB1();
		b1Mutate = wave.isB1Mutate();
		b2 = wave.getB2();
		b2Mutate = wave.isB2Mutate();
		b3 = wave.getB3();
		b3Mutate = wave.isB3Mutate();
		b4 = wave.getB4();
		bProducer = wave.getBProducer();
	}
}