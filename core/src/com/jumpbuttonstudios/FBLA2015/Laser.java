package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Laser extends Image {

	float repeat, lastRepeat = 999f;
	Rectangle hitbox;

	public Laser(float x, float y, float width, float height, float repeat) {
		super(new Texture(Gdx.files.internal("obstacle/laser.png")));

		this.repeat = repeat;

		setPosition(x, y);
		setSize(width, height);

		hitbox = new Rectangle(x, y, width, height);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		hitbox.set(getX(), getY(),  getWidth(), getHeight());
		
		if (getActions().size == 0) {
			this.addAction(Actions.sequence(Actions.alpha(0, 0.1f), Actions.delay(1f), Actions.alpha(1, 0.1f), Actions.delay(1f)));
		}

	}
}
