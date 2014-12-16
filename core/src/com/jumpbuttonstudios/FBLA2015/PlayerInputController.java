package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputController implements InputProcessor {
	Player parent;
	float at, dt, wt, st;
	float speed;
	boolean w, a, d, left;
	float moveAngle = 90;

	public PlayerInputController(Player parent, float speed) {
		this.parent = parent;
		this.speed = speed;
	}

	public void update(float delta) {
		parent.body.setTransform(parent.body.getTransform().getPosition(), moveAngle * MathUtils.degRad);
		if (a) {
			moveAngle += 3;
			at += delta;

		} else {
			at = 0;
		}

		if (d) {
			moveAngle -= 3;
			dt += delta;

		} else {
			dt = 0;
		}

		if (w) {
			
			parent.body.setLinearVelocity(MathUtils.cosDeg(moveAngle) * speed, MathUtils.sinDeg(moveAngle) * speed);
		}

		if (at < dt && dt != 0 && at != 0) {
			parent.body.setLinearVelocity(-speed, parent.body.getLinearVelocity().y);
		}

		if (left) {
			parent.gun.fire(1.5f, 0, 1, true, parent.dir);
		}

		if (!w && !a  && !d) {
			parent.body.setLinearVelocity(0, 0);
		}
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.W) {
			w = true;
		}
		if (keycode == Keys.A) {
			a = true;
		}
		if (keycode == Keys.D) {
			d = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W) {
			w = false;
		}
		if (keycode == Keys.A) {
			a = false;
		}
		if (keycode == Keys.D) {
			d = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT) {
			left = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT) {
			left = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
