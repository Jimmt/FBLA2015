package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Arm extends Image {

	public Arm(String path, float x, float y, float rotation) {
		super(new Texture(Gdx.files.internal(path)));
		
		setPosition(x, y);
		setRotation(rotation);
		setSize(getWidth() * Constants.SCALE, getHeight() * Constants.SCALE);
		setOrigin(0.13f, 0.38f);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
