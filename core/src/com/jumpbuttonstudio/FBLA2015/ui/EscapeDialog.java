package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.screens.BaseScreen;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.screens.LevelSelectScreen;
import com.jumpbuttonstudio.FBLA2015.screens.MenuScreen;
import com.jumpbuttonstudio.FBLA2015.screens.StoryScreen;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class EscapeDialog extends Dialog {
	BaseScreen game;

	public EscapeDialog(final FBLA2015 fbla, final BaseScreen game, Skin skin) {
		super("", skin);

		this.game = game;

		Image panel = new Image(Textures.getTex("ui/escapebg.png"));
		background(panel.getDrawable());

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton yes = new TextImageButton("Yes", skin.getFont("default-font"), style);
		yes.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game instanceof GameScreen || game instanceof StoryScreen) {
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
