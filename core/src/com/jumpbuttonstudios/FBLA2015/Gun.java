package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Gun extends Image {
	Array<Bullet> bullets;
	float lastFireTime = 999f, fireCap;
	World world;
	GameSprite parent;
	float damage;

	public Gun(float fireCap, float damage, World world, GameSprite parent) {

		this.damage = damage;
		this.fireCap = fireCap;

		bullets = new Array<Bullet>();
		this.world = world;
		this.parent = parent;

		setSize(getWidth() * Constants.SCALE, getHeight() * Constants.SCALE);
	}

	public void fire(float radius, float distance, float volume, boolean friendly, Vector2 direction) {

		if (direction.len() < distance || distance == 0) {
			if (lastFireTime > fireCap) {
				lastFireTime = 0;

				FBLA2015.soundManager.play("gunshot", volume);

				Vector2 coords = new Vector2(parent.getX() + parent.getWidth() / 2
						+ MathUtils.cosDeg(direction.angle()) * radius , parent.getY()
						+ parent.getHeight() / 3 * 2 + MathUtils.sinDeg(direction.angle()) * radius);

				Bullet bullet = new Bullet("bullet.png", friendly, coords.x, coords.y, direction.angle()
						* MathUtils.degRad, damage, world);

				bullet.body.setLinearVelocity(direction.nor().scl(30f));
				bullets.add(bullet);
			}
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
