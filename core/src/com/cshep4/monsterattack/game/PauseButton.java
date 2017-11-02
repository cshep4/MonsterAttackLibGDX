package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PauseButton extends GameObject {
	
	public PauseButton(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
	}
	
//	private Bitmap resizeImage(Bitmap image,int maxWidth, int maxHeight)
//	{
//	    Bitmap resizedImage = null;
//	    try {
//	        int imageHeight = image.getHeight();
//
//
//	        if (imageHeight > maxHeight)
//	            imageHeight = maxHeight;
//	        int imageWidth = (imageHeight * image.getWidth())
//	                / image.getHeight();
//
//	        if (imageWidth > maxWidth) {
//	            imageWidth = maxWidth;
//	            imageHeight = (imageWidth * image.getHeight())
//	                    / image.getWidth();
//	        }
//
//	        if (imageHeight > maxHeight)
//	            imageHeight = maxHeight;
//	        if (imageWidth > maxWidth)
//	            imageWidth = maxWidth;
//
//
//	        resizedImage = Bitmap.createScaledBitmap(image, imageWidth,
//	                imageHeight, true);
//	    } catch (OutOfMemoryError e) {
//
//	        e.printStackTrace();
//	    }catch(NullPointerException e)
//	    {
//	        e.printStackTrace();
//	    }
//	    catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    return resizedImage;
//	}
//
//	public void drawButton(Canvas canvas){
//		canvas.drawBitmap(this.bmp,this.xPos,this.yPos,null);
//	}
//
//	//Set the x of the button
//	public void setXPos(int x){
//		this.xPos = x;
//	}
//
//	//Set the y of the button
//	public void setYPos(int y){
//		this.yPos = y;
//	}
//
//	//Get the x of the button
//	public int getXPos() {
//		return this.xPos;
//	}
//
//	//Get the y of the button
//	public int getYPos (){
//		return this.yPos;
//	}
//
//	//Set the x of the button
//	public void setWidth(int width){
//		this.width = width;
//	}
//
//	//Set the y of the button
//	public void setHeight(int height){
//		this.height = height;
//	}
//
//	//Get the x of the button
//	public int getWidth() {
//		return this.width;
//	}
//
//	//Get the y of the button
//	public int getHeight(){
//		return this.height;
//	}
}
