package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class EnemyRaycastCallback implements RayCastCallback {
	boolean hit;

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		
		UserData userData = (UserData) fixture.getBody().getUserData();
		if (userData.value instanceof Enemy || userData.value instanceof Bullet || userData.value instanceof Boss) {
			hit = false;
			return -1;
		} else {
			hit = true;
			return 0;
		}
	}

	public boolean isHit() {
		return hit;
	}
}
