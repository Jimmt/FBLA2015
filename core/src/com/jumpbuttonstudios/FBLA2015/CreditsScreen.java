package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreditsScreen extends BaseScreen {

	public CreditsScreen(FBLA2015 game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();

		table.add("Credits");

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
		table.row();
		table.add(back);
	}

}
