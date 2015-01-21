package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EscapeDialog extends Dialog {
	BaseScreen game;

	public EscapeDialog(final FBLA2015 fbla, final BaseScreen game, Skin skin) {
		super("", skin);

		this.game = game;

		Image panel = new Image(new Texture(Gdx.files.internal("ui/levelcompletebg.png")));
		background(panel.getDrawable());

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		TextImageButton yes = new TextImageButton("Yes", skin.getFont("default-font"), style);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game instanceof GameScreen) {
					fbla.setScreen(new LevelSelectScreen(fbla));
				}
				if (game instanceof LevelSelectScreen || game instanceof MenuScreen) {
					Gdx.app.exit();
				}

			}
		});
		TextImageButton no = new TextImageButton("No", skin.getFont("default-font"), style);
		no.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		getContentTable().add("Really exit?").padTop(20f).padBottom(20f);
		button(yes);
		button(no);
	}

	
}
