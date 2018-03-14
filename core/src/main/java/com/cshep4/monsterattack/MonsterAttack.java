package com.cshep4.monsterattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cshep4.monsterattack.game.wrapper.Sound;

public class MonsterAttack extends Game {

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
		font.setColor(Color.BLACK);
		Sound.loadSounds();

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		Sound.dispose();
		screen.dispose();
		font.dispose();
	}
}
