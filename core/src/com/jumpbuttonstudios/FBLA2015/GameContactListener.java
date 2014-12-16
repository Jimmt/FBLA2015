package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {


	@Override
	public void beginContact(Contact contact) {
		UserData a = (UserData) contact.getFixtureA().getBody().getUserData();
		UserData b = (UserData) contact.getFixtureB().getBody().getUserData();

		if (a.value instanceof Bullet) {
			((Bullet) a.value).delete = true;
		}
		if (b.value instanceof Bullet) {
			((Bullet) b.value).delete = true;
		}
		if (a.value instanceof Enemy && b.value instanceof Bullet) {
			if (((Bullet) b.value).friendly) {
				((Enemy) a.value).hurt(((Bullet) b.value).damage);
			}
		}
		if (b.value instanceof Enemy && a.value instanceof Bullet) {
			if (((Bullet) a.value).friendly) {
				((Enemy) b.value).hurt(((Bullet) a.value).damage);
			}
		}
		if (a.value instanceof Player && b.value instanceof Bullet) {
			((Player) a.value).hurt(((Bullet) b.value).damage);
		}
		if (b.value instanceof Player && a.value instanceof Bullet) {
			((Player) b.value).hurt(((Bullet) a.value).damage);
		}
	}

	@Override
	public void endContact(Contact contact) {
		UserData a = (UserData) contact.getFixtureA().getBody().getUserData();
		UserData b = (UserData) contact.getFixtureB().getBody().getUserData();
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
