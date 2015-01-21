package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BossHealthDialog extends Dialog {
	Boss boss;
	HealthBar healthBar;

	public BossHealthDialog(Boss boss, Skin skin) {
		super("", skin);

		Image bg = new Image(new Texture(Gdx.files.internal("ui/bosshealthbg.png")));
		background(bg.getDrawable());

		getContentTable().add(boss.name);

		healthBar = new HealthBar(boss);
		getContentTable().row();
		getContentTable().add(healthBar);
		
		this.boss = boss;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
}