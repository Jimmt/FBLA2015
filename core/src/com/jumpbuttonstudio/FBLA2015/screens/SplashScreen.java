package com.jumpbuttonstudio.FBLA2015.screens;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.util.Textures;

/**
 * Lynbrook FBLA Splash Screen
 *
 */
public class SplashScreen extends BaseScreen {

	public SplashScreen(final FBLA2015 game) {
		super(game);


		Image splash = new Image(Textures.getTex("ui/fblalogo.png"));
		Image white = new Image(Textures.getTex("white.png"));
		table.setFillParent(true);
		table.setBackground(white.getDrawable());
		table.add(splash);

		Action switchScreenAction = new Action() {
			public boolean act(float delta) {
				game.setScreen(new MenuScreen(game));
				return true;
			}
		};

		splash.addAction(Actions.sequence(Actions.delay(1f), Actions.fadeOut(1f),
				switchScreenAction));

	}

}
