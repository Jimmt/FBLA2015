package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ControlsScreen extends BaseScreen {

	public ControlsScreen(FBLA2015 game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();

		Texture tex = new Texture(Gdx.files.internal("instructions.png"));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Image pic = new Image(tex);

		table.add(pic).width(pic.getWidth()).height(pic.getHeight());

		pic.addAction(Actions.sequence(Actions.alpha(0.0f),
				Actions.fadeIn(1.0f)));
		
		table.row();

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});
		
		table.add(back);

	}
}