package com.cshep4.monsterattack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;

public class Bomb extends Bullet {

	public Bomb(Rectangle rectangle, Texture texture) {
		super(rectangle, texture);
	}
	
	public void collisionSound() {
		Sounds.playExplode();
	}	

}
