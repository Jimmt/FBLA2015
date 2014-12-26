package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
	ParticleEffect effect;

	public ParticleEffectActor(String path, String dir) {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal(path), Gdx.files.internal(dir));
		ParticleEmitter emitter = effect.getEmitters().get(0);
		emitter.getScale().setHigh(emitter.getScale().getHighMax() * Constants.SCALE);
		emitter.getVelocity().setHigh(emitter.getVelocity().getHighMin() * Constants.SCALE,
				emitter.getVelocity().getHighMax() * Constants.SCALE);
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		effect.update(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		effect.draw(batch);

	}
}
