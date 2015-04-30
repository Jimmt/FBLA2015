package com.jumpbuttonstudio.FBLA2015.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;

public class Gun extends Actor {

	public Array<Bullet> bullets;
	public float bonusDamage = 0;
	public boolean bounce;
	float lastFireTime = 999f;
	World world;
	ItemStats stats;
	Body body;
	Vector2 temp = new Vector2();

	public Gun(World world, float bulletSpeed, ItemStats stats, Body body) {

		this.stats = stats;
		bullets = new Array<Bullet>();
		this.world = world;
		this.body = body;
	}



	public void fire(float radius, float distance, float volume, boolean friendly, Vector2 direction) {

		if (direction.len() < distance || distance == 0) {
			if (lastFireTime > stats.getROF()) {
				lastFireTime = 0;

				FBLA2015.soundManager.playRandom("gunshot", volume, 0.85f, 1.15f);

				// half width is factored in
				Vector2 coords = new Vector2(body.getPosition().x
						+ MathUtils.cosDeg(direction.angle()) * radius, body.getPosition().y
						+ MathUtils.sinDeg(direction.angle()) * radius);

				Bullet bullet = new Bullet(stats.getBulletPath(), friendly, coords.x, coords.y,
						direction.angle() * MathUtils.degRad, stats.getDamage() + bonusDamage, world);
				bullet.bounce = bounce;

				Vector2 temp = body.getLinearVelocity();
				if (direction.x > 0 && direction.y > 0) {
					bullet.body.setLinearVelocity(direction.nor().scl(stats.getBulletSpeed())
							.add(body.getLinearVelocity()));
				} else {
					bullet.body.setLinearVelocity(direction.nor().scl(stats.getBulletSpeed()));
				}

				bullets.add(bullet);
			}
		}

	}

	public void fireStraight(float radius, float volume, float angle) {
		if (lastFireTime > stats.getROF()) {
			lastFireTime = 0;
			FBLA2015.soundManager.play("gunshot", volume);

			Vector2 coords = new Vector2(body.getPosition().x + MathUtils.cosDeg(angle) * radius,
					body.getPosition().y + MathUtils.sinDeg(angle) * radius);

			Bullet bullet = new Bullet(stats.getBulletPath(), false, coords.x, coords.y, angle
					* MathUtils.degRad, stats.getDamage(), world);
			bullet.bounce = bounce;
			temp.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
			bullet.body.setLinearVelocity(temp.nor().scl(stats.getBulletSpeed()));

			bullets.add(bullet);
		}
	}

	public void updateFire(float delta) {
		lastFireTime += delta;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (int i = 0; i < bullets.size; i++) {
			bullets.get(i).draw(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (int i = 0; i < bullets.size; i++) {
			bullets.get(i).act(delta);
			if (bullets.get(i).delete) {
				world.destroyBody(bullets.get(i).body);
				bullets.removeIndex(i);
			}

		}

	}
}
