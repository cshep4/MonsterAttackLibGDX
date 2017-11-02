package com.cshep4.monsterattack.game;

public class HighScore {
	private int place;
	private int numkills;
	private String name;
	
	public void setPlace(int place){
		this.place = place;
	}
	
	public int getPlace(){
		return place;
	}
	
	public void setNumkills(int numkills){
		this.numkills = numkills;
	}
	
	public int getNumkills(){
		return numkills;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}	
}
