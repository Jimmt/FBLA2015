package com.jumpbuttonstudio.FBLA2015.sprite;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Custom libgdx Action for the player sprite.
 *
 */
public class TurnAction extends Action {
	Actor actor;
	float finalAngle, startAngle, percent, inc = 999f, cap = 0.5f;

	public TurnAction(Actor actor, float finalAngle) {
		this.actor = actor;
		this.finalAngle = finalAngle;
		startAngle = actor.getRotation();

		if (finalAngle - startAngle > 91) {
			while (finalAngle - startAngle > 91) {
				finalAngle -= 360;
				System.out.println(finalAngle);
			}
		} else if (finalAngle - startAngle < 91) {
			while (finalAngle - startAngle < 91) {
				System.out.println(finalAngle);
				finalAngle += 360;
			}
		}
	}

	@Override
	public boolean act(float delta) {

		percent += delta * 10;

		actor.setRotation(startAngle + (finalAngle - startAngle) * percent);

		if (percent >= 1.0f) {
			return true;
		}

		return false;
	}

}
