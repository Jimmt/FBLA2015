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

		if (a.tag.equals("target") && b.value instanceof Bullet) {
			((Target) a.value).playParticles();
		}
		if (b.tag.equals("target") && a.value instanceof Bullet) {
			((Target) b.value).playParticles();
		}

		if (a.value instanceof Bullet && !(b.value instanceof Bullet)) {
			if (!((Bullet) a.value).bounce) {
				((Bullet) a.value).delete = true;
			}
		}
		if (b.value instanceof Bullet && !(a.value instanceof Bullet)) {
			if (!((Bullet) b.value).bounce) {
				((Bullet) b.value).delete = true;
			}
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
			if (!((Bullet) b.value).friendly) {
				((Player) a.value).hurt(((Bullet) b.value).damage);
			}
		}
		if (b.value instanceof Player && a.value instanceof Bullet) {
			if (!((Bullet) a.value).friendly) {
				((Player) b.value).hurt(((Bullet) a.value).damage);
			}
		}

		if (a.value instanceof Bullet && b.value instanceof Boss) {
			if (((Bullet) a.value).friendly) {
				((Boss) b.value).health -= ((Bullet) a.value).damage;
			}
			if(b.value instanceof TrojanHorse){
				((TrojanHorse) b.value).activate();
			}
		}
		if (b.value instanceof Bullet && a.value instanceof Boss) {
			if (((Bullet) b.value).friendly) {
				((Boss) a.value).health -= ((Bullet) b.value).damage;
			}
			if(a.value instanceof TrojanHorse){
				((TrojanHorse) a.value).activate();
			}
		}
		

		if (a.value instanceof Player && b.value instanceof Worm) {

			((Player) a.value).hurt(((Boss) b.value).damage);

		}
		if (b.value instanceof Player && a.value instanceof Worm) {
			((Player) b.value).hurt(((Boss) a.value).damage);
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
