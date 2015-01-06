package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class HealthBar extends Actor {
	Image background, bar;
	Player player;
	Boss boss;
	float initialWidth, barWidth;

	public HealthBar(Player player) {
		this.player = player;

		background = new Image(new Texture(Gdx.files.internal("ui/healthbarbg.png")));
		background.setWidth(background.getWidth() * 2);
		bar = new Image(new Texture(Gdx.files.internal("ui/healthbar.png")));
		bar.setWidth(bar.getWidth() * 2);
		initialWidth = bar.getWidth();
		setSize(background.getWidth(), background.getHeight());
	}

	public HealthBar(Boss boss) {
		this.boss = boss;
		
		background = new Image(new Texture(Gdx.files.internal("ui/healthbarbg.png")));
		background.setWidth(background.getWidth() * 2);
		bar = new Image(new Texture(Gdx.files.internal("ui/healthbar.png")));
		bar.setWidth(bar.getWidth() * 2);
		initialWidth = bar.getWidth();
		setSize(background.getWidth(), background.getHeight());
	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		bar.draw(batch, parentAlpha);
		background.draw(batch, parentAlpha);
		bar.setPosition(getX() + background.getWidth() - bar.getWidth(), getY());
		background.setPosition(getX(), getY());

		if (player != null) {
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
