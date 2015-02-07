package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class OptionsScreen extends BaseScreen {

	public OptionsScreen(FBLA2015 game) {
		super(game);

	}

	class Resolution {
		float width, height;

		public Resolution(float width, float height) {
			this.width = width;
			this.height = height;
		}

		public boolean isEqualTo(Resolution other) {
			return width == other.width && height == other.height;
		}
	}

	@Override
	public void show() {
		super.show();

		table.add("Options").colspan(2);

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

		BitmapFont font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));

		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
		checkBoxStyle.checkboxOn = new Image(new Texture(
				Gdx.files.internal("ui/checkbox/checkOn.png"))).getDrawable();
		checkBoxStyle.checkboxOff = new Image(new Texture(
				Gdx.files.internal("ui/checkBox/checkOff.png"))).getDrawable();
		checkBoxStyle.font = font;

		final CheckBox sound = new CheckBox("", checkBoxStyle);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Prefs.putBoolean("sound", sound.isChecked());
				FBLA2015.soundManager.play("button", 0.5f);
				FBLA2015.soundManager.setPlay(sound.isChecked());
			}
		});

		final CheckBox fullscreen = new CheckBox("", checkBoxStyle);
		fullscreen.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				Gdx.graphics.setDisplayMode(1024, 768, fullscreen.isChecked());
				Prefs.putBoolean("fullscreen", fullscreen.isChecked());
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		LabelStyle lstyle = new LabelStyle();
		lstyle.fontColor = Color.WHITE;
		lstyle.font = font;
		Label label = new Label("Sound Enabled", lstyle);
		Label fullscreenLabel = new Label("Fullscreen Enabled", lstyle);

		table.row();
		table.add(sound).padBottom(15f).padTop(30f);
		table.add(label).padLeft(20f).padBottom(15f).padTop(30f);
		sound.setChecked(Prefs.prefs.getBoolean("sound"));
		

		table.row();
		table.add(fullscreen).padBottom(60f);
		table.add(fullscreenLabel).padLeft(20f).padBottom(60f);
		fullscreen.setChecked(Prefs.prefs.getBoolean("fullscreen"));


		table.row();
		table.add(back).colspan(2);

	}
}
