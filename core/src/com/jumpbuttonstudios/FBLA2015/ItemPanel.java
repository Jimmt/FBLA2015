package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ItemPanel extends ImageButton {
	Image item;

	public ItemPanel(Image item, Skin skin) {
		super(new ImageButtonStyle());
		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/itembg.png"))).getDrawable();
		setStyle(style);
		this.item = item;
		item.setPosition(getX() + getWidth() / 2 - item.getWidth() / 2, getY() + getHeight() / 2
				- item.getHeight() / 2);

		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("click");
			}
		});

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		item.setPosition(getX() + getWidth() / 2 - item.getWidth() / 2, getY() + getHeight() / 2
				- item.getHeight() / 2);
		item.draw(batch, parentAlpha);
	}
}
