package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ControlsScreen extends BaseScreen {

	public ControlsScreen(FBLA2015 game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();

		Image pic = new Image(new Texture(Gdx.files.internal("instructions.png")));

		table.add(pic).width(pic.getWidth() * 2).height(pic.getHeight() * 2);

		pic.addAction(Actions.sequence(Actions.alpha(0.0f),
				Actions.fadeIn(1.0f, Interpolation.bounce)));
	}
}
