package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class JetpackBar extends Actor {
	Image background, bar;
	Player player;
	float initialWidth;

	public JetpackBar(Player player) {
		this.player = player;

		background = new Image(new Texture(Gdx.files.internal("jetpack/jetpackbarbg.png")));
		background.setWidth(background.getWidth() * 2);
		bar = new Image(new Texture(Gdx.files.internal("jetpack/jetpackbar.png")));
		bar.setWidth(bar.getWidth() * 2);
		initialWidth = background.getWidth();
		setSize(background.getWidth(), background.getHeight());
	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		bar.draw(batch, parentAlpha);
		background.draw(batch, parentAlpha);
		bar.setPosition(getX(), getY());
		background.setPosition(getX(), getY());

		if (player.jetpack.fuel / player.jetpack.fuelMax * initialWidth > initialWidth) {
			bar.setWidth(initialWidth);
		} else {
			bar.setWidth(player.jetpack.fuel / player.jetpack.fuelMax * initialWidth);
		}

	}
}
