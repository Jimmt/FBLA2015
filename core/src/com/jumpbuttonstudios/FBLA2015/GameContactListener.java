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
		if (a.value instanceof Player && b.value instanceof Map) {
			if (a.tag.equals("feet")) {
				((Player) a.value).canJump = true;
			}
		}
		if (b.value instanceof Player && a.value instanceof Map) {
			if (b.tag.equals("feet")) {
				((Player) b.value).canJump = true;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		UserData a = (UserData) contact.getFixtureA().getBody().getUserData();
		UserData b = (UserData) contact.getFixtureB().getBody().getUserData();
		
		if (a.value instanceof Player && b.value instanceof Map) {
			if (a.tag.equals("feet")) {
				((Player) a.value).canJump = false;
			}
		}
		if (b.value instanceof Player && a.value instanceof Map) {
			if (b.tag.equals("feet")) {
				((Player) b.value).canJump = false;
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
