package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends BaseScreen {

	public SplashScreen(final FBLA2015 game) {
		super(game);

		Image splash = new Image(new Texture(Gdx.files.internal("fblalogo.png")));
		Image white = new Image(new Texture(Gdx.files.internal("white.png")));
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
