package com.jumpbuttonstudio.FBLA2015.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	public ParticleEffect effect;
	public boolean scaled;

	public ParticleEffectActor(String path, String dir, boolean scaled) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal(path), Gdx.files.internal(dir));

		this.scaled = scaled;

		if (scaled) {
			scale();
		}

	}

	public void scale() {
		ParticleEmitter emitter = effect.getEmitters().get(0);
		emitter.getScale().setHigh(emitter.getScale().getHighMax() * Constants.SCALE);
		emitter.getVelocity().setHigh(emitter.getVelocity().getHighMin() * Constants.SCALE,
				emitter.getVelocity().getHighMax() * Constants.SCALE);
	}

	public ParticleEffectActor(ParticleEffect effect) {
		this.effect = effect;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		effect.update(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (!scaled) {
			effect.setPosition(getX() + 16, getY() + 16);
		} else {
			effect.setPosition(getX(), getY());
		}
		effect.draw(batch);

	}
}
