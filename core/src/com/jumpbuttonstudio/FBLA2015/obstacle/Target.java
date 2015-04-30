package com.jumpbuttonstudio.FBLA2015.obstacle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.jumpbuttonstudio.FBLA2015.box2d.UserData;
import com.jumpbuttonstudio.FBLA2015.sprite.GameSprite;
import com.jumpbuttonstudio.FBLA2015.util.ParticleEffectActor;

public class Target extends GameSprite {
	ParticleEffectActor effect;
	float effectX, effectY;

	public Target(float x, float y, float angle, World world) {
		super("obstacle/target.png", x, y, angle, BodyType.StaticBody, world);

		UserData data = new UserData();
		data.tag = "target";
		data.value = this;
		body.setUserData(data);
		effect = new ParticleEffectActor("effects/hit.p", "", true);
		effectX = getX() + getWidth() / 2;
		effectY = getY() + getHeight() / 2;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		effect.setPosition(effectX, effectY);
		effect.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		effect.act(delta);
	}

	public void playParticles() {
		effect.effect.allowCompletion();
		effect.effect.start();
	}
}
