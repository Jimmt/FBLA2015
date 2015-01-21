package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class Player extends GameSprite {

	float speed = 5f, healthMax = 100, health = healthMax;
	float angle, stateTime;

	Vector2 centerPosition, mouse, dir;
	Vector3 coords = new Vector3();

	World world;
	Gun gun;
	ItemStats stats = ItemStats.PISTOL;
	PlayerInputController controller;
	Rectangle hitbox, commHitbox;
	ParticleEffectActor streak;

	public Player(String path, World world) {
		super(path, 1, world);
		
		streak = new ParticleEffectActor("effects/streak.p", "", true);

		hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
		commHitbox = new Rectangle(getX() / Constants.SCALE, getY() / Constants.SCALE, getWidth() / Constants.SCALE, getHeight() / Constants.SCALE);
		controller = new PlayerInputController(this, 2, 10);
		this.world = world;

		gun = new Gun(world, 10f, stats, body);

		UserData userData = new UserData();
		userData.setValue(this);
		userData.setTag("player");
		body.setUserData(userData);
		body.getFixtureList().get(0).setFriction(0.0f);
		Filter f = body.getFixtureList().get(0).getFilterData();
		f.categoryBits = Bits.PLAYER;
		f.maskBits = (short) (Bits.ENEMY | Bits.MAP);
		body.getFixtureList().get(0).setFilterData(f);

		this.stdRotate = false;
	}

	public void hurt(float amount) {
		health -= amount;

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		streak.effect.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
		streak.effect.getEmitters().get(0).getAngle().setHigh(getRotation() - 90);
		streak.effect.getEmitters().get(0).getRotation().setHigh(getRotation());
		streak.draw(batch, parentAlpha);
		gun.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		streak.act(delta);
		hitbox.set(getX(), getY(), getWidth(), getHeight());
		commHitbox.set(getX() / Constants.SCALE, getY() / Constants.SCALE, getWidth() / Constants.SCALE, getHeight() / Constants.SCALE);
		
		stateTime += delta;

		getStage().getCamera().position.set(Math.round((getX() + getWidth() / 2) * 100f) / 100f,
				Math.round((getY() + getHeight() / 2) * 100f) / 100f, 0);

		checkMouseRotation();
		controller.update(delta);

		gun.act(delta);
		gun.updateFire(delta);

	}

	public void checkMouseRotation() {
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		mouse = new Vector2(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());

		dir = mouse.sub(centerPosition);
		angle = dir.angle();

	}

}
