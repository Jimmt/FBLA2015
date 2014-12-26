package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Enemy extends GameSprite implements Destroyable {
	float healthMax, health, aggroRange = 4;
	Image still;
	Player player;
	Gun gun;
	boolean clearShot, aggro;
	Vector2 direction;
	World world;
	EnemyRaycastCallback callback;

	public Enemy(String path, float x, float y, float healthMax, World world) {
		super(path, x, y, 0, 1, false, BodyType.DynamicBody, world);

		this.world = world;
		direction = new Vector2();
		gun = new Gun(world, 3f, ItemStats.PISTOL, this);
		this.healthMax = healthMax;
		health = healthMax;
		UserData userData = new UserData();
		userData.value = this;
		userData.tag = "enemy";

		body.setUserData(userData);

		callback = new EnemyRaycastCallback();

		Filter f = body.getFixtureList().get(0).getFilterData();
		f.categoryBits = Bits.ENEMY;
		f.maskBits = (short) (Bits.PLAYER | Bits.MAP);
		body.getFixtureList().get(0).setFilterData(f);

	}

	public void hurt(float amount) {
		health -= amount;
	}

	public void setRange(float aggroRange) {
		this.aggroRange = aggroRange;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		world.rayCast(callback, player.body.getWorldCenter(), body.getWorldCenter());

		direction.set(
				player.getX() - getX() + MathUtils.random(player.body.getLinearVelocity().x / 4)
						- player.body.getLinearVelocity().x / 8,
				player.getY() - getY() + MathUtils.random(player.body.getLinearVelocity().y / 4)
						- player.body.getLinearVelocity().y / 8);

		gun.updateFire(delta);
		gun.act(delta);

		if (direction.len() < aggroRange) {
			follow(delta);
			aggro = true;
		} else {

		}

		if (!callback.isHit() && aggro) {
			gun.fire(1f, 0, 0.1f, false, direction);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		gun.draw(batch, parentAlpha);

	}

	public void follow(float delta) {
		setRotation(direction.angle() - 90);

	}

	@Override
	public void destroy() {

	}

}
