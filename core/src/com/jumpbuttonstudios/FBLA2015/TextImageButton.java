package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TextImageButton extends ImageButton {
	Label textLabel;

	public TextImageButton(String text, BitmapFont font, ImageButtonStyle ibs) {
		super(ibs);

		LabelStyle style = new LabelStyle();
		style.font = font;
		textLabel = new Label(text, style);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		textLabel.setPosition(getX() + getWidth() / 2 - textLabel.getWidth() / 2, getY()
				+ getHeight() / 2 - textLabel.getHeight() / 2);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		textLabel.draw(batch, parentAlpha);
	}
}
