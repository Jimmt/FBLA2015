package com.jumpbuttonstudio.FBLA2015;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumpbuttonstudio.FBLA2015.screens.MenuScreen;
import com.jumpbuttonstudio.FBLA2015.screens.SplashScreen;
import com.jumpbuttonstudio.FBLA2015.sprite.PlayerProfile;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;

/**
 * Base game class. Not much is done here because it's mostly abstracted to the
 * screen classes.
 *
 */
public class FBLA2015 extends Game {
	public static CustomSoundManager soundManager;
	public static boolean DEBUG = false;

	@Override
	public void create() {
		soundManager = new CustomSoundManager();
		soundManager.loadSound("gunshot", Gdx.files.internal("sfx/gunshot.wav"));
		soundManager.loadSound("button", Gdx.files.internal("sfx/buttonpress.wav"));
		soundManager.loadSound("enemydeath", Gdx.files.internal("sfx/enemydeath.wav"));
		soundManager.loadMusic("menu", Gdx.files.internal("sfx/menu.ogg"));

		DEBUG = true;

		GamePrefs.initialize();
		PlayerProfile.initialize();
		
		

		Gdx.graphics.setDisplayMode(GamePrefs.prefs.getInteger("width"),
				GamePrefs.prefs.getInteger("height"), false);
		Constants.update();

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
