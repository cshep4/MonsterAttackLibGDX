package com.cshep4.monsterattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cshep4.monsterattack.game.core.SoundWrapper;

public class MonsterAttack  extends Game {

	private AppInterface appInterface;
	public void setInterface(AppInterface appInterface) {
		this.appInterface = appInterface;
	}

	SpriteBatch batch;
	BitmapFont font;

	static public Skin gameSkin;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();

		gameSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		SoundWrapper.dispose();
		screen.dispose();
	}
}
