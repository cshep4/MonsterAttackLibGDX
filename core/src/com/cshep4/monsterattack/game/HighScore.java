package com.cshep4.monsterattack.game;

public class HighScore {
	private int place;
	private int numkills;
	private String name;
	
	public void setPlace(int aPlace){
		place = aPlace;
	}
	
	public int getPlace(){
		return place;
	}
	
	public void setNumkills(int aNumkills){
		numkills = aNumkills;
	}
	
	public int getNumkills(){
		return numkills;
	}
	
	public void setName(String aName){
		name = aName;
	}
	
	public String getName(){
		return name;
	}	
}
