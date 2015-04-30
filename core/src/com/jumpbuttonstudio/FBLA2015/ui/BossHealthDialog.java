package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jumpbuttonstudio.FBLA2015.boss.Boss;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class BossHealthDialog extends Dialog {
	Boss boss;
	HealthBar healthBar;

	public BossHealthDialog(Boss boss, Skin skin) {
		super("", skin);

		Image bg = new Image(Textures.getTex("ui/bosshealthbg.png"));
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
