package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class HudTable extends Table {
	HealthBar healthBar;
	Player player;
	CoinsBar coinsBar;
	GameScreen gs;

	public HudTable(Player player, GameScreen gs, Skin skin) {
		super(skin);

		this.gs = gs;
		this.player = player;

		Table infoTable = new Table(skin);

		healthBar = new HealthBar(player);
		infoTable.add(healthBar);
		infoTable.row();
		coinsBar = new CoinsBar(0, skin);

		add(coinsBar).left().top();
		add(infoTable).expand().top().right();
		row();


	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		coinsBar.amount = gs.byteCoins;
	}
}
