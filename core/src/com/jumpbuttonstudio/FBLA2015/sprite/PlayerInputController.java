package com.jumpbuttonstudio.FBLA2015.sprite;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class PlayerInputController implements InputProcessor {
	Player parent;
	float at, dt, wt, st;
	float speed, angularSpeed;
	boolean w, a, s, d, left;
	Facing facing;
	float finalAngle, startAngle = 0;
	Vector2 target = new Vector2(0, 1), heading = new Vector2();

	enum Facing {
		LEFT(-1, 0), RIGHT(1, 0), UP(0, 1), DOWN(0, -1);

		private Vector2 direction;

		Facing(float x, float y) {
			direction = new Vector2(x, y);
		}

		public Vector2 getDirection() {
			return direction;
		}
	}

	public PlayerInputController(Player parent, float speed, float angularSpeed) {
		this.parent = parent;
		this.speed = speed;
		this.angularSpeed = angularSpeed;
		facing = Facing.UP;
	}

	public void update(float delta) {
		startAngle = parent.getRotation();
		finalAngle = parent.body.getTransform().getRotation() * MathUtils.radDeg;
		
		if (a) {
			facing = Facing.LEFT;
			parent.body.setLinearVelocity(-speed, parent.body.getLinearVelocity().y);
			at += delta;
			parent.body.setTransform(parent.body.getTransform().getPosition(), MathUtils.PI / 2);
			parent.addAction(Actions.rotateBy(-MathUtils.atan2((MathUtils.sinDeg(startAngle - finalAngle)), MathUtils.cosDeg(startAngle - finalAngle)) * angularSpeed));
		} else {
			at = 0;
		}

		if (d) {
			facing = Facing.RIGHT;
			parent.body.setLinearVelocity(speed, parent.body.getLinearVelocity().y);
			dt += delta;
			parent.body.setTransform(parent.body.getTransform().getPosition(), -MathUtils.PI / 2);
			parent.addAction(Actions.rotateBy(-MathUtils.atan2((MathUtils.sinDeg(startAngle - finalAngle)), MathUtils.cosDeg(startAngle - finalAngle)) * angularSpeed));

		} else {
			dt = 0;
		}

		if (w) {
			facing = Facing.UP;
			wt += delta;
			parent.body.setLinearVelocity(parent.body.getLinearVelocity().x, speed);
			parent.body.setTransform(parent.body.getTransform().getPosition(), 0);
			parent.addAction(Actions.rotateBy(-MathUtils.atan2((MathUtils.sinDeg(startAngle - finalAngle)), MathUtils.cosDeg(startAngle - finalAngle)) * angularSpeed));

		} else {
			wt = 0;
		}

		if (s) {
			facing = Facing.DOWN;
			st += delta;
			parent.body.setLinearVelocity(parent.body.getLinearVelocity().x, -speed);
			parent.body.setTransform(parent.body.getTransform().getPosition(), -MathUtils.PI);
			parent.addAction(Actions.rotateBy(-MathUtils.atan2((MathUtils.sinDeg(startAngle - finalAngle)), MathUtils.cosDeg(startAngle - finalAngle)) * angularSpeed));

		} else {
			st = 0;
		}

		if (w && d) {
			parent.body.setTransform(parent.body.getTransform().getPosition(), -MathUtils.PI / 4);
		}
		if (w && a) {
			parent.body.setTransform(parent.body.getTransform().getPosition(), MathUtils.PI / 4);
		}
		if (s && d) {
			parent.body.setTransform(parent.body.getTransform().getPosition(),
					-MathUtils.PI / 4 * 3);
		}
		if (s && a) {
			parent.body
					.setTransform(parent.body.getTransform().getPosition(), MathUtils.PI / 4 * 3);
		}

		if (at < dt && dt != 0 && at != 0) {
			parent.body.setLinearVelocity(-speed, parent.body.getLinearVelocity().y);
		}

		if (wt < st && st != 0 && wt != 0) {
			parent.body.setLinearVelocity(parent.body.getLinearVelocity().x, -speed);
		}

		if (left) {
			parent.gun.fire(0.4f, 0, 1, true, parent.dir);
		}

		if (!w && !s) {
			parent.body.setLinearVelocity(parent.body.getLinearVelocity().x, 0);
		}
		if (!a && !d) {
			parent.body.setLinearVelocity(0, parent.body.getLinearVelocity().y);
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
		if (keycode == Keys.S) {
			s = true;
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
		if (keycode == Keys.S) {
			s = false;
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
