package com.jumpbuttonstudio.FBLA2015.screens;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.ui.TextImageButton;
import com.jumpbuttonstudio.FBLA2015.util.Fonts;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.util.Textures;

/**
 * Basic options menu.
 *
 */
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
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		BitmapFont font = Fonts.getFont("skin/orbitron.fnt");

		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();
		checkBoxStyle.checkboxOn = new Image(new Texture(
				Gdx.files.internal("ui/checkbox/checkOn.png"))).getDrawable();
		checkBoxStyle.checkboxOff = new Image(new Texture(
				Gdx.files.internal("ui/checkbox/checkOff.png"))).getDrawable();
		checkBoxStyle.font = font;

		final CheckBox sound = new CheckBox("", checkBoxStyle);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GamePrefs.putBoolean("sound", sound.isChecked());
				FBLA2015.soundManager.play("button", 0.5f);
				FBLA2015.soundManager.setPlay(sound.isChecked());
				FBLA2015.soundManager.playMusic("menu", 0.09f);
			}
		});

		final CheckBox fullscreen = new CheckBox("", checkBoxStyle);
		fullscreen.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				Gdx.graphics.setDisplayMode((int) d.getWidth(), (int) d.getHeight(),
						fullscreen.isChecked());
				GamePrefs.putBoolean("fullscreen", fullscreen.isChecked());
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
		sound.setChecked(GamePrefs.prefs.getBoolean("sound"));

		table.row();
		table.add(fullscreen).padBottom(60f);
		table.add(fullscreenLabel).padLeft(20f).padBottom(60f);
		fullscreen.setChecked(GamePrefs.prefs.getBoolean("fullscreen"));

		table.row();
		table.add(back).colspan(2);

	}
}
