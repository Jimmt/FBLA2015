package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HudTable extends Table {
	JetpackBar jetpackBar;
	HealthBar healthBar;
	Player player;

	public HudTable(Player player, Skin skin) {
		super(skin);

		this.player = player;

		Table infoTable = new Table(skin);

		healthBar = new HealthBar(player);
		infoTable.add("HP");
		infoTable.add(healthBar);
		infoTable.row();
		jetpackBar = new JetpackBar(player);
		infoTable.add(new Image(player.jetpack.getDrawable()));
		infoTable.add(jetpackBar);

		add(infoTable).expand().top().right();

	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
}
