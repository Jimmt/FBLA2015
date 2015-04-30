package com.jumpbuttonstudio.FBLA2015.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class Hole extends Image {
	public Rectangle hitbox;

	public Hole() {
		super(Textures.getTex("level2/hole.png"));

		setSize(getWidth() * Constants.SCALE, getHeight() * Constants.SCALE);

		hitbox = new Rectangle();

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		hitbox.set(getX(), getY(), getWidth(), getHeight());
	}
}
