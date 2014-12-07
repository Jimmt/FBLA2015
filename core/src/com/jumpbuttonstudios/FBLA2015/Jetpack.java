package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Jetpack extends Image {
	float fuel, fuelMax, regenRate, lastActiveTime = 999f, activeDelay = 2f;
	boolean draining;
	Vector2 power;
	GameSprite parent;
	ParticleEffectActor flame;

	public Jetpack(String path, GameSprite parent, float fuel, float fuelMax, float power,
			float regenRate) {
		super(new Texture(Gdx.files.internal(path)));

		setWidth(getWidth() * Constants.SCALE);
		setHeight(getHeight() * Constants.SCALE);

		setOrigin(getWidth() / 2, getHeight() / 2);

		this.parent = parent;
		this.fuel = fuel;
		this.fuelMax = fuelMax;
		this.regenRate = regenRate;
		this.power = new Vector2(0, power);
		flame = new ParticleEffectActor("jetpack/jetpack.p", "");

	}

	public void activate(float delta) {

		if (fuelMax != -5) {
			if (fuel <= 0) {
				draining = false;

				fuel = 0;
			} else {
				fuel -= delta;
			}
		}

		if (parent.body.getLinearVelocity().y < 3 && fuel > 0.0f) {
			draining = true;
			parent.body.applyLinearImpulse(power, parent.body.getLocalCenter(), false);

		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (draining) {
			flame.effect.start();
			lastActiveTime = 0;
		} else {
			lastActiveTime += delta;
			flame.effect.allowCompletion();
		}

		if (getScaleX() == 1) {
			flame.effect.setPosition(getX() + 0.24f, getY());
		} else {
			flame.effect.setPosition(getX() + 0.55f, getY());
		}
		flame.effect.update(delta);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (fuel < fuelMax && !draining && lastActiveTime > 0.5f) {
			fuel += regenRate;
		}
	}
}
