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

		
		coinsBar = new CoinsBar(0, skin);
		add(coinsBar).expand().left().top();
		healthBar = new HealthBar(player);
		add(healthBar).expand().right().top();
		
		
//		add(infoTable).expand();
		row();

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		coinsBar.amount = gs.byteCoins;
	}
}
