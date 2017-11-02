package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;

public class Bomb extends Bullet {

	public Bomb(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
	}

//	public Bomb(int x, int y, int width, int height) {
//		super(x, y, width, height);
//	}
//
//	//Method to draw a rectangle on the screen
//	public void drawRect(Paint p, Canvas c){
//	    p.setStrokeWidth(3);
//	    int left = this.xPos;
//	    int top = this.yPos;
//	    int right = this.width + this.xPos;
//	    int bottom = this.height + this.yPos;
//	    bullet.set(left,top,right,bottom);
//	    left = this.xPos + this.width/2;
//	    top = this.yPos + this.height/2;
//	    right = this.width /3 + this.xPos + this.width/2;
//	    bottom = this.height /3 + this.yPos + this.width/2;
//	    p.setColor(Color.RED);
//        c.drawRect(left, top, right, bottom, p);
//	}
	
//	public void update(){
//    	//Set the speed of the bullet
//    	this.xPos += this.xVel;
//	}
	
	
	public void collisionSound() {
		Sounds.playExplode();
	}	

}
