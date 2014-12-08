package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class HudTable extends Table {
	JetpackBar jetpackBar;
	HealthBar healthBar;
	Player player;
	CoinsBar coinsBar;

	public HudTable(Player player, Skin skin) {
		super(skin);

		this.player = player;

		Table infoTable = new Table(skin);

		healthBar = new HealthBar(player);
		infoTable.add(healthBar);
		infoTable.row();
		jetpackBar = new JetpackBar(player);
		infoTable.add(jetpackBar);
		coinsBar = new CoinsBar(0, skin);
		
		Table coinsTable = new Table(skin);

		add(coinsBar).left().top();
		add(infoTable).expand().top().right();
		row();


	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
}
