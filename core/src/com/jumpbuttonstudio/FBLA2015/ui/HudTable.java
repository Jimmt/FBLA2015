package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.sprite.Player;

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
