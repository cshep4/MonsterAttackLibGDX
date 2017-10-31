package com.cshep4.monsterattack.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameObject {
	protected int health;
	protected int xPos;
	protected int yPos;	
	protected int xVel;
	protected int yVel;	
	protected int width;
	protected int height;	
	protected Rect gameObject = new Rect();	
	protected int directionFacing;
	protected Bitmap bmp;
	private MyApp myApp = MyApp.getInstance();
	
	protected int frameNr;        // number of frames in animation
	private int currentFrame;   // the current frame
	private long frameTicker;   // the time of the last frame update
	private int framePeriod = 1000/10;    // milliseconds between each frame (1000/fps)
	protected int spriteWidth;
	protected int spriteHeight;
	protected Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
	
	protected boolean directionChange = false;
	

	public int getHealth(){
		return health;
	}
	
	public void setHealth(int aHealth){
		health = aHealth;
	}
	
	public GameObject(){
		health = 100;
		width = 40;
		height = 40;
		frameTicker = 0l;
		currentFrame = 0;

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
	
	//Get the rect that holds the block co-ordinates
	public Rect getRect(){
		return gameObject;
	}	
	
	//Method to draw a rectangle on the screen
	public void drawObject(Paint p, Canvas c){
		long gameTime = System.currentTimeMillis();
		if (gameTime > frameTicker + framePeriod) {
	        frameTicker = gameTime;
	        // increment the frame
	        currentFrame++;
	        if (currentFrame >= frameNr) {
	            currentFrame = 0;
	        }
	    }

	    p.setStrokeWidth(3);
	    int left = this.xPos;
	    int top = this.yPos;
	    int right = this.width + this.xPos;
	    int bottom = this.height + this.yPos;
	    gameObject.set(left,top,right,bottom);
	    this.sourceRect.left = currentFrame * spriteWidth;
	    this.sourceRect.right = this.sourceRect.left + spriteWidth;
	    p.setColor(Color.BLUE);
	    
		 // where to draw the sprite
		 Rect destRect = new Rect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height);
		 c.drawBitmap(bmp, sourceRect, destRect, null); 
	}	
	
	public Bullet shoot(){
		// create and return a bullet
		Bullet bullet;
		bullet = new Bullet(xPos+(width /2), yPos+(height /2), height / Constants.BULLET_SIZE_DIVIDER, width / Constants.BULLET_SIZE_DIVIDER);
		return bullet;
	}	
	
	public void setDirectionFacing(int aDirection){
		this.directionFacing = aDirection;
	}
	
	public int getDirectionFacing() {
		return this.directionFacing;
	}	
	
	public void update(){
    	//Set the speed of the object
    	this.xPos += this.xVel;
    	this.yPos += this.yVel;
		directionChange = (this.xVel <= 0 && this.directionFacing == Constants.RIGHT) ||
				(this.xVel > 0 && this.directionFacing == Constants.LEFT);
    	if (this.xVel <= 0)
    	{
    		this.directionFacing = Constants.LEFT;
    	} else {
    		this.directionFacing = Constants.RIGHT;
    	}
	}	
	
	public void setXVel(int aXVel){
		this.xVel = aXVel;
	}
	
	public int getXVel() {
		return this.xVel;
	}	
	
	public void setYVel(int aYVel){
		this.yVel = aYVel;
	}
	
	public int getYVel() {
		return this.yVel;
	}	
	
	public void setObjectRect(int aLeft, int aTop, int aRight, int aBottom) {
		gameObject.set(aLeft,aTop,aRight,aBottom);
	}
	
	public void setNewBitmap(Bitmap aBmp, int aFrameNr) {
		frameNr = aFrameNr;
		currentFrame = 0;
		bmp = aBmp;
		//bmp = Bitmap.createScaledBitmap(bmp, this.width, this.height,false);
		spriteWidth = bmp.getWidth()/frameNr;
		spriteHeight = bmp.getHeight();		
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
	}
	
	public void setDirectionChange(boolean aChange) {
		directionChange = aChange;
	}
}
