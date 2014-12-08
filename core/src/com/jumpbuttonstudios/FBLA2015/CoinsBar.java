package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class CoinsBar extends Table {
	int amount;
	Image icon, bar;
	Label label;

	public CoinsBar(int amount, Skin skin) {
		super(skin);
		this.amount = amount;
		icon = new Image(new Texture(Gdx.files.internal("ui/coinicon.png")));
		bar = new Image(new Texture(Gdx.files.internal("ui/coinbar.png")));
		LabelStyle labelStyle = new LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		labelStyle.font = font;
		labelStyle.background = bar.getDrawable();
		label = new Label("", labelStyle);

		add(icon).width(icon.getWidth()).height(icon.getHeight());
		add(label).padLeft(5f);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		label.setText(String.valueOf(amount));
	}

}
