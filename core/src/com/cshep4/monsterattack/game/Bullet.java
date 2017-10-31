package com.cshep4.monsterattack.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bullet {
	protected int xPos;
	protected int yPos;	
	protected int xVel;	
	protected int width;
	protected int height;	
	protected Rect bullet = new Rect();
	
	public Bullet(int aXPos, int aYPos, int aWidth, int aHeight){
		xPos = aXPos;
		yPos = aYPos;
		width = aWidth;
		height = aHeight;
		xVel = Constants.BULLET_SPEED;
	}
	
	//Method to draw a rectangle on the screen
	public void drawRect(Paint p, Canvas c){
	    p.setStrokeWidth(3);
	    int left = this.xPos;
	    int top = this.yPos;
	    int right = this.width + this.xPos;
	    int bottom = this.height + this.yPos;
	    bullet.set(left,top,right,bottom);
	    p.setColor(Color.BLACK);
        c.drawRect(left, top, right, bottom, p);
	}	
	
	//Set the x of the rectangle
	public void setXPos(int xIn){
		this.xPos = xIn;
	}
	
	//Set the y of the rectangle
	public void setYPos(int yIn){
		this.yPos = yIn;
	}
	
	//Get the x of the rectangle
	public int getXPos() {
		return this.xPos;
	}
	
	//Get the y of the rectangle
	public int getYPos (){
		return this.yPos;
	}	
	
	//Get the rect that holds the block co-ordinates
	public Rect getRect(){
		return bullet;
	}	
	
	public void update(){
    	//Set the speed of the bullet
    	this.xPos += this.xVel;
	}
	
	public void setXVel(int aXVel){
		this.xVel = aXVel;
	}
	
	public int getXVel() {
		return this.xVel;
	}	
	
	//Set the width of the rectangle
	public void setWidth (int widthIn){
		this.width = widthIn;
	}
	
	//Set the height of the rectangle
	public void setHeight (int heightIn){
		this.height = heightIn;
	}	
	
	//Get the width of the rectangle
	public int getWidth() {
		return this.width;
	}
	
	//Get the height of the rectangle
	public int getHeight (){
		return this.height;
	}	
	
	public void collisionSound() {
		
	}
}
