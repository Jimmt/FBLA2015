package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FBLA2015 extends Game {
	static CustomSoundManager soundManager;
	static boolean DEBUG = false;

	@Override
	public void create() {
		soundManager = new CustomSoundManager();
		soundManager.loadSound("gunshot", Gdx.files.internal("sfx/gunshot.wav"));
		soundManager.loadSound("button", Gdx.files.internal("sfx/buttonpress.wav"));
		soundManager.loadSound("enemydeath", Gdx.files.internal("sfx/enemydeath.wav"));
		soundManager.loadMusic("menu", Gdx.files.internal("sfx/menu.ogg"));
		
		
//		DEBUG = true;
		
		if (DEBUG) {
			setScreen(new MenuScreen(this));
		} else {
			setScreen(new SplashScreen(this));
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
