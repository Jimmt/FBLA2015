package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LiveTile extends Image {
	Vector2 direction = new Vector2();
	float lastTurnTime = 0, turnCap = 0.5f;
	boolean horizontal;

	public LiveTile(Texture tex, float x, float y, float vx, float vy) {
		super(tex);

		setPosition(x, y);

		horizontal = MathUtils.randomBoolean();

		float magnitude = 10f, centerX = x + getWidth() / 2, centerY = y + getHeight() / 2;

		setOrigin(getWidth() / 2, getHeight() / 2);

		if (horizontal) {

			if (centerX > Constants.WIDTH / 2) {
				magnitude = -magnitude;
				setScale(-1, 1);
			}
			direction.x = MathUtils.random(magnitude / 2, magnitude);
		} else {
			rotateBy(90);
			if (centerY > Constants.HEIGHT / 2) {
				magnitude = -magnitude;
				rotateBy(180);
			}

			direction.y = MathUtils.random(magnitude / 2, magnitude);
		}

		if (vx != 0 && vy != 0) {
			direction.set(vx, vy);
		}

		addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);


		setX(getX() + direction.x);
		setY(getY() + direction.y);

		if (lastTurnTime > turnCap) {
			lastTurnTime = 0;
		} else {
			lastTurnTime += delta;
		}

	}
}
