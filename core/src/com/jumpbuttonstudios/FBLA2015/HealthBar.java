package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class HealthBar extends Actor {
	Image background, bar;
	Player player;
	Label healthText;
	Boss boss;
	float initialWidth, barWidth;

	public HealthBar(Player player) {
		this.player = player;

		background = new Image(Textures.getTex("ui/healthbar/healthbarbg.png"));
		background.setWidth(background.getWidth() * 2);
		bar = new Image(Textures.getTex("ui/healthbar/healthbar.png"));
		bar.setWidth(bar.getWidth() * 2);
		initialWidth = bar.getWidth();
		setSize(background.getWidth(), background.getHeight());

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		labelStyle.fontColor = Color.WHITE;
		healthText = new Label("", labelStyle);
	}

	public HealthBar(Boss boss) {
		this.boss = boss;

		background = new Image(Textures.getTex("ui/healthbar/healthbarbg.png"));
		background.setWidth(background.getWidth() * 2);
		bar = new Image(Textures.getTex("ui/healthbar/healthbar.png"));
		bar.setWidth(bar.getWidth() * 2);
		initialWidth = bar.getWidth();
		setSize(background.getWidth(), background.getHeight());
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (player != null) {
			healthText.act(delta);
		}
	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		bar.draw(batch, parentAlpha);
		background.draw(batch, parentAlpha);
		bar.setPosition(getX() + background.getWidth() - bar.getWidth(), getY());
		background.setPosition(getX(), getY());

		if (player != null) {
			healthText.setText((int) (player.health / player.healthMax * 100f) + "%");
			healthText.setPosition(getX() + getWidth() - healthText.getMinWidth() - 10,
					Constants.HEIGHT - getHeight() / 2);
			healthText.draw(batch, parentAlpha);
			barWidth = player.health / player.healthMax * initialWidth;
		} else {
			barWidth = boss.health / boss.healthMax * initialWidth;
		}
		bar.setWidth(barWidth);

		if (player != null) {
			if (player.health / player.healthMax * initialWidth < 0) {
				bar.setWidth(0);
			}
		} else {
			if (boss.health / boss.healthMax * initialWidth < 0) {
				bar.setWidth(0);
			}
		}
	}
}
