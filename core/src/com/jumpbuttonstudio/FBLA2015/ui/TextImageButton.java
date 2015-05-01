package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * Simple custon UI component
 */
public class TextImageButton extends ImageButton {
	public Label textLabel;

	public TextImageButton(String text, BitmapFont font, ImageButtonStyle ibs) {
		super(ibs);

		LabelStyle style = new LabelStyle();
		style.font = font;
		textLabel = new Label(text, style);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		textLabel.setPosition(getX() + getWidth() / 2 - textLabel.getWidth() / 2, getY()
				+ getHeight() / 2 - textLabel.getHeight() / 2);
		textLabel.draw(batch, parentAlpha);
	}
}
