package com.cshep4.monsterattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cshep4.monsterattack.game.wrapper.Sound;

public class MonsterAttack extends Game {
	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Skin skin = new Skin(Gdx.files.internal("skin/comic/skin/comic-ui.json"));
		font = skin.getFont("font");
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
