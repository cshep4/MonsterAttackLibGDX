package com.cshep4.monsterattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MonsterAttack  extends Game {

	private AppInterface appInterface;
	public void setInterface(AppInterface appInterface) {
		this.appInterface = appInterface;
	}

	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
