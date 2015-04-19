package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverDialog extends Dialog {
	TextImageButton button;

	public GameOverDialog(final GameScreen game, final FBLA2015 fbla, Skin skin) {
		super("", skin);

		game.pauseGame();

		Image panel = new Image(Textures.getTex("ui/comm/commbg.png"));
		background(panel.getDrawable());

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		button = new TextImageButton("Back", skin.getFont("default-font"), style);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fbla.setScreen(new LevelSelectScreen(fbla));

			}
		});

		getContentTable().add("GAME OVER");
		getContentTable().row();
		getContentTable().add(button);
	}
}
