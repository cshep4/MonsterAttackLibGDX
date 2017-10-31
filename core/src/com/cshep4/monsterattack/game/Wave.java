package com.cshep4.monsterattack.game;

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
	
	public Wave(Wave aWave){
		id = aWave.getId();
		s1 = aWave.getS1();
		s1Mutate = aWave.getS1Mutate();
		s2 = aWave.getS2();
		s2Mutate = aWave.getS2Mutate();
		s3 = aWave.getS3();
		s3Mutate = aWave.getS3Mutate();
		s4 = aWave.getB4();
		sProducer = aWave.getSProducer();
		b1 = aWave.getB1();
		b1Mutate = aWave.getB1Mutate();
		b2 = aWave.getB2();
		b2Mutate = aWave.getB2Mutate();
		b3 = aWave.getB3();
		b3Mutate = aWave.getB3Mutate();
		b4 = aWave.getB4();
		bProducer = aWave.getBProducer();	
	}
	
	public void setId(int aId) {
		id = aId;
	}
	
	public int getId(){
		return id;
	}
	
	public void setS1(int aS1){
		s1 = aS1;
	}
	
	public int getS1(){
		return s1;
	}
	
	public void setS2(int aS2){
		s2 = aS2;
	}
	
	public int getS2(){
		return s2;
	}
	
	public void setS3(int aS3){
		s3 = aS3;
	}
	
	public int getS3(){
		return s3;
	}
	
	public void setS4(int aS4){
		s4 = aS4;
	}
	
	public int getS4(){
		return s4;
	}	
	
	public void setSProducer(int aSProducer){
		sProducer = aSProducer;
	}
	
	public int getSProducer(){
		return sProducer;
	}
	
	public void setS1Mutate(boolean aS1Mutate){
		s1Mutate = aS1Mutate;
	}
	
	public boolean getS1Mutate(){
		return s1Mutate;
	}
	
	public void setS2Mutate(boolean aS2Mutate){
		s2Mutate = aS2Mutate;
	}
	
	public boolean getS2Mutate(){
		return s2Mutate;
	}
	
	public void setS3Mutate(boolean aS3Mutate){
		s3Mutate = aS3Mutate;
	}
	
	public boolean getS3Mutate(){
		return s3Mutate;
	}
	
	public void setB1(int aB1){
		b1 = aB1;
	}
	
	public int getB1(){
		return b1;
	}
	
	public void setB2(int aB2){
		b2 = aB2;
	}
	
	public int getB2(){
		return b2;
	}
	
	public void setB3(int aB3){
		b3 = aB3;
	}
	
	public int getB3(){
		return b3;
	}
	
	public void setB4(int aB4){
		b4 = aB4;
	}
	
	public int getB4(){
		return b4;
	}	
	
	public void setBProducer(int aBProducer){
		bProducer = aBProducer;
	}
	
	public int getBProducer(){
		return bProducer;
	}
	
	public void setB1Mutate(boolean aB1Mutate){
		b1Mutate = aB1Mutate;
	}
	
	public boolean getB1Mutate(){
		return b1Mutate;
	}
	
	public void setB2Mutate(boolean aB2Mutate){
		b2Mutate = aB2Mutate;
	}
	
	public boolean getB2Mutate(){
		return b2Mutate;
	}
	
	public void setB3Mutate(boolean aB3Mutate){
		b3Mutate = aB3Mutate;
	}
	
	public boolean getB3Mutate(){
		return b3Mutate;
	}
	
}