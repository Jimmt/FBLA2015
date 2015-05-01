package com.jumpbuttonstudio.FBLA2015.weapon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Custom gun for the trojan horse level.
 */
public class ColorGun extends Gun {
	Color color;

	public ColorGun(World world, float bulletSpeed, ItemStats stats, Body body, Color color) {
		super(world, bulletSpeed, stats, body);

		this.color = color;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (Bullet b : bullets) {
			b.setColor(color);
		}
	}

}
