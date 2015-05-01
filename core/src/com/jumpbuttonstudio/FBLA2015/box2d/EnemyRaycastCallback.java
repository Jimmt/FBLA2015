package com.jumpbuttonstudio.FBLA2015.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.jumpbuttonstudio.FBLA2015.boss.Boss;
import com.jumpbuttonstudio.FBLA2015.sprite.Enemy;
import com.jumpbuttonstudio.FBLA2015.weapon.Bullet;

/**
 * Raycasting for enemy pathfinding
 *
 */
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
