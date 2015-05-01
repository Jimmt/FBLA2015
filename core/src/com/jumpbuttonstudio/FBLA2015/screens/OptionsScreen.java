package com.jumpbuttonstudio.FBLA2015.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.ui.TextImageButton;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
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
		int width, height;

		public Resolution(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public boolean isEqualTo(Resolution other) {
			return width == other.width && height == other.height;
		}

		public String toString() {
			if (width == GamePrefs.prefs.getInteger("maxWidth")
					&& height == GamePrefs.prefs.getInteger("maxHeight")) {
				return "Max window";
			}
			return width + "x" + height + "     ";
		}
	}

	@Override
	public void show() {
		super.show();

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

		SelectBoxStyle boxStyle = new SelectBoxStyle();
		boxStyle.background = new Image(Textures.getTex("ui/selectbox/boxbg.png")).getDrawable();
		boxStyle.font = font;
		boxStyle.fontColor = Color.WHITE;
		ListStyle listStyle = new ListStyle();
		listStyle.font = font;
		listStyle.fontColorSelected = Color.WHITE;
		listStyle.fontColorUnselected = Color.WHITE;
		listStyle.selection = new Image(Textures.getTex("ui/selectbox/listbg.png")).getDrawable();
		boxStyle.listStyle = listStyle;
		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.background = new Image(Textures.getTex("ui/black.png")).getDrawable();
		boxStyle.scrollStyle = scrollStyle;
		final SelectBox<Resolution> resolutions = new SelectBox<Resolution>(boxStyle);
		Array<Resolution> resList = new Array<Resolution>();
		resList.add(new Resolution(1024, 768));
		resList.add(new Resolution(1366, 768));
		resList.add(new Resolution(1600, 900));
		resList.add(new Resolution(1920, 1080));
		resList.add(new Resolution(GamePrefs.prefs.getInteger("maxWidth"), GamePrefs.prefs
				.getInteger("maxHeight")));
		resolutions.setItems(resList);

		for (int i = 0; i < resList.size; i++) {
			if (GamePrefs.prefs.getInteger("width") == resList.get(i).width) {
				if (GamePrefs.prefs.getInteger("height") == resList.get(i).height) {
					resolutions.setSelectedIndex(i);
					break;
				}
			}
		}

		TextImageButton update = new TextImageButton("Update", Fonts.getFont("skin/orbitron.fnt"),
				style);
		update.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FBLA2015.soundManager.play("button", 0.5f);
				Resolution res = resolutions.getSelected();
				int width = res.width, height = res.height;
				Gdx.graphics.setDisplayMode(width, height, false);
				Constants.update();
				GamePrefs.putInteger("width", width);
				GamePrefs.putInteger("height", height);
				game.setScreen(new OptionsScreen(game));
			}
		});

		table.add("Options").colspan(1);

		LabelStyle lstyle = new LabelStyle();
		lstyle.fontColor = Color.WHITE;
		lstyle.font = font;
		Label label = new Label("Sound Enabled", lstyle);

		lstyle.font = Fonts.getFont("skin/orbitron.fnt");
		Label resLabel = new Label("Resolution: ", lstyle);

		table.row();
		Table checkBoxTable = new Table(skin);
		checkBoxTable.add(sound).padBottom(15f).padTop(30f);
		checkBoxTable.add(label).padLeft(20f).padBottom(15f).padTop(30f);
		sound.setChecked(GamePrefs.prefs.getBoolean("sound"));
		table.add(checkBoxTable);
		table.row();

		Table resTable = new Table(skin);
		resTable.add(resLabel).padRight(15f);
		resTable.add(resolutions).padRight(30f);
		resTable.add(update).width(update.textLabel.getWidth() + 30f)
				.height(update.textLabel.getHeight() + 20f);
		table.add(resTable);
		table.row();
		table.add(back).colspan(1).padTop(30f);

	}
}
