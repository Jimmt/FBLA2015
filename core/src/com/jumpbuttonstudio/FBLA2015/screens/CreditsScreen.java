package com.jumpbuttonstudio.FBLA2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.ui.TextImageButton;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class CreditsScreen extends BaseScreen {

	public CreditsScreen(FBLA2015 game) {
		super(game);

	}

	@Override
	public void show() {
		super.show();

		table.add("Credits").padBottom(30f);

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		LabelStyle lstyle = new LabelStyle();
		lstyle.fontColor = Color.WHITE;
		BitmapFont orbitron = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		lstyle.font = orbitron;

		Label code = new Label("Code: Austin Hsieh", lstyle);
		Label art = new Label("Art: Austin Hsieh", lstyle);
		Label design = new Label("Design: Andy Zhang / Austin Hsieh", lstyle);

		table.row();
		table.add(code).expandX().center().padBottom(30f);
		table.row();
		table.add(art).expandX().center().padBottom(30f);
		table.row();
		table.add(design).expandX().center().padBottom(30f);
		table.row();

		table.row();
		table.add(back);
	}

}
