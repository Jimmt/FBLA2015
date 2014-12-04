package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class PlayerInputController {
	Player parent;
	float aTime, dTime;
	float speed;

	public PlayerInputController(Player parent, float speed) {
		this.parent = parent;
		this.speed = speed;
	}

	public void update(float delta) {

		if (Gdx.input.isKeyPressed(Keys.W)) {
			parent.jetpack.activate(delta);
		} else {
			parent.jetpack.draining = false;
		}

		if (parent.facingRight) {
			if (parent.walk.animatedSprite.isFlipX()) {
				parent.walk.animatedSprite.flipFrames(true, false);
			}
		} else {
			if (!parent.walk.animatedSprite.isFlipX()) {
				parent.walk.animatedSprite.flipFrames(true, false);

			}
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			aTime += delta;
			parent.body.setLinearVelocity(-speed, parent.body.getLinearVelocity().y);
			if (parent.facingRight) {
				parent.walk.animatedSprite.getAnimation().setPlayMode(PlayMode.LOOP_REVERSED);
			} else {
				parent.walk.animatedSprite.getAnimation().setPlayMode(PlayMode.LOOP);
			}
		} else {
			aTime = 0;
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			dTime += delta;
			parent.body.setLinearVelocity(speed, parent.body.getLinearVelocity().y);
			if (!parent.facingRight) {
				parent.walk.animatedSprite.getAnimation().setPlayMode(PlayMode.LOOP_REVERSED);
			} else {
				parent.walk.animatedSprite.getAnimation().setPlayMode(PlayMode.LOOP);
			}
		} else {
			dTime = 0;
		}

		if (aTime < dTime && dTime != 0 && aTime != 0) {
			parent.body.setLinearVelocity(-speed, parent.body.getLinearVelocity().y);
		}

		if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
			parent.drawStill = true;
			parent.body.setLinearVelocity(0, parent.body.getLinearVelocity().y);
		} else {
			parent.drawStill = false;
		}

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			parent.gun.fire(1.5f, 0, 1, parent.dir);
		}
	}
}
