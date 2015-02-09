package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class EnemiesKilledDialog extends Dialog {
	int killed = 0, cap;
	Label label;

	public EnemiesKilledDialog(int cap, Skin skin) {
		super("", skin);
		Image bg = new Image(new Texture(Gdx.files.internal("ui/bosshealthbg.png")));
		background(bg.getDrawable());
		this.cap = cap;

		LabelStyle style = new LabelStyle();
		style.fontColor = Color.WHITE;
		style.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		label = new Label("0 /" + cap, style);

		getContentTable().add("Malware Destroyed");
		getContentTable().row();
		getContentTable().add(label);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		label.setText(killed + " / " + cap);

	}
}
