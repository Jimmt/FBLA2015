package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StoryScreen extends BaseScreen {
	Label textLabel;
	String text = "This data center has been infected from the bottom up. As a computer virus tracker, it's your job to traverse each level of the data center and clear any malware that you encounter. Let's begin with the customization of your anti-virus ship.";
	CustomizationDialog customsDialog;

	public StoryScreen(final FBLA2015 game) {
		super(game);

		Image background = new Image(Textures.getTex("ui/storybg.png"));
		table.setBackground(background.getDrawable());

		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("skin/geosanslight_white.fnt"));
		textStyle.fontColor = Color.WHITE;
		textLabel = new Label(text, textStyle);
		textLabel.setAlignment(Align.center);
		textLabel.setWrap(true);
		
		customsDialog = new CustomizationDialog(skin, game);

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton continueButton = new TextImageButton("Continue",
				skin.getFont("default-font"), style);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FBLA2015.soundManager.play("button", 0.5f);
				customsDialog.show(dialogStage);
			}
		});

		table.add("Warning").padBottom(30f).row();
		table.add(textLabel).width(Constants.WIDTH / 3 * 2).padBottom(30f);
		table.row();
		table.add(continueButton).width(continueButton.textLabel.getWidth() * 1.25f)
				.height(continueButton.textLabel.getHeight() * 2f);

	}

}
