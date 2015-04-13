package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class Enemy extends GameSprite implements Destroyable {
	float healthMax, health, aggroRange = Constants.ENEMY_AGGRO_RANGE,
			optimalFireDistance = Constants.ENEMY_FIRE_RANGE, lastKnownAngle;
	Image still;
	Player player;
	Gun gun;
	boolean clearShot, aggro, objective, effectPlaying;
	Vector2 direction;
	World world;
	EnemyRaycastCallback callback;
	AStar astar;
	IntArray path;
	float effectX, effectY, startAngle, finalAngle, angularSpeed = 2f, pathSpeed;

	public Enemy(String path, ItemStats stats, float x, float y, float healthMax, AStar astar,
			World world) {
		super(path, x, y, 0, 1, true, BodyType.DynamicBody, world);
		this.astar = astar;
		this.world = world;
		this.pathSpeed = stats.getMoveSpeed();
		direction = new Vector2();
		gun = new Gun(world, 3f, stats, body);
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
		body.getFixtureList().get(0).setFriction(0.0f);
		this.path = new IntArray();

		setOrigin(width / 2, height / 2);
		stdRotate = false;
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

		if (aggroRange == -1) {
			if (direction.x < Constants.SCLWIDTH / 2) {
				follow(delta);
				aggro = true;
			}
			if (direction.y < Constants.SCLHEIGHT / 2) {
				follow(delta);
				aggro = true;
			}
		}

		if (!callback.isHit() && aggro) {
			gun.fire((getWidth() + getHeight()) / 2, 0, 0.05f, false, direction);

		}

		startAngle = getRotation();
		finalAngle = direction.angle() - 90;

		addAction(Actions.rotateBy(-MathUtils.atan2((MathUtils.sinDeg(startAngle - finalAngle)),
				MathUtils.cosDeg(startAngle - finalAngle)) * angularSpeed));

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		gun.draw(batch, parentAlpha);

	}

	public void follow(float delta) {
		path = astar.getPath((int) (player.getX() / Constants.SCALE / Constants.TILE_SIZE),
				(int) (player.getY() / Constants.SCALE / Constants.TILE_SIZE),
				(int) (body.getPosition().x / Constants.SCALE / Constants.TILE_SIZE),
				(int) (body.getPosition().y / Constants.SCALE / Constants.TILE_SIZE));

		if (path.size >= 4) {
			Vector2 pathDir = new Vector2(path.get(2) - path.get(0), path.get(3) - path.get(1));
			if (direction.len() > optimalFireDistance) {
				body.setLinearVelocity(pathDir.nor());
			} else {
				body.setLinearVelocity(pathDir.nor().scl(pathSpeed));
			}
		}

	}

	@Override
	public void destroy() {
	}

}
