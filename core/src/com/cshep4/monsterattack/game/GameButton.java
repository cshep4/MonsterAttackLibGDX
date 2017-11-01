package com.cshep4.monsterattack.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GameButton {
	private Bitmap bmp;
	private int xPos;
	private int yPos;
	private int width;
	private int height;	
	
	public GameButton(int width, int height, Resources res, int x, int y, int button) {
		Bitmap bitmap = BitmapFactory.decodeResource(res, button);
		bitmap = resizeImage(bitmap, width, height);
		this.bmp = bitmap;
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;
	}
	
	private Bitmap resizeImage(Bitmap image,int maxWidth, int maxHeight)
	{
	    Bitmap resizedImage = null;
	    try {
	        int imageHeight = image.getHeight();


	        if (imageHeight > maxHeight)
	            imageHeight = maxHeight;
	        int imageWidth = (imageHeight * image.getWidth())
	                / image.getHeight();

	        if (imageWidth > maxWidth) {
	            imageWidth = maxWidth;
	            imageHeight = (imageWidth * image.getHeight())
	                    / image.getWidth();
	        }

	        if (imageHeight > maxHeight)
	            imageHeight = maxHeight;
	        if (imageWidth > maxWidth)
	            imageWidth = maxWidth;


	        resizedImage = Bitmap.createScaledBitmap(image, imageWidth,
	                imageHeight, true);
	    } catch (OutOfMemoryError e) {

	        e.printStackTrace();
	    }catch(NullPointerException e)
	    {
	        e.printStackTrace();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resizedImage;
	}	
	
	public void drawButton(Canvas canvas){
		canvas.drawBitmap(this.bmp,this.xPos,this.yPos,null);
	}
	
	//Set the x of the button
	public void setXPos(int x){
		this.xPos = x;
	}
	
	//Set the y of the button
	public void setYPos(int y){
		this.yPos = y;
	}
	
	//Get the x of the button
	public int getXPos() {
		return this.xPos;
	}
	
	//Get the y of the button
	public int getYPos (){
		return this.yPos;
	}	
	
	//Set the x of the button
	public void setWidth(int width){
		this.width = width;
	}
	
	//Set the y of the button
	public void setHeight(int height){
		this.height = height;
	}
	
	//Get the x of the button
	public int getWidth() {
		return this.width;
	}
	
	//Get the y of the button
	public int getHeight(){
		return this.height;
	}		
}
