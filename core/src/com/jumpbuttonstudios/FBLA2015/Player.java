package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Player extends GameSprite {

	float speed = 5f;
	float lastFireTime = 999f, fireCap = 0.2f, angle, stateTime;
	boolean drawStill;

	Array<Bullet> bullets = new Array<Bullet>();
	Vector2 centerPosition, mouse, dir;
	Vector3 coords = new Vector3();
	World world;
	Arm arm;

	public Player(String path, World world) {
		super(path, world);

		this.world = world;
		arm = new Arm("bodyparts/arms/front/bent.png", getX() + 0.2f, getY() + 0.8f, 0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		arm.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta;

		checkKeys();

		if (Gdx.input.isButtonPressed(Buttons.LEFT) && lastFireTime > fireCap) {
			lastFireTime = 0;
			fire(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());
		} else {
			lastFireTime += delta;
		}

		getStage().getCamera().position.set((getX() + getWidth() / 2), (getY() + getHeight() / 2),
				0);

		checkMouseRotation();	
		arm.setRotation(angle);
		arm.act(delta);
		arm.setPosition(getX() + 0.2f, getY() + 0.8f);
	}

	public void checkMouseRotation() {
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		mouse = new Vector2(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());

		dir = mouse.sub(centerPosition);
		angle = dir.angle();

	}

	public void fire(float x, float y) {
		Vector2 coords = new Vector2(getX() + getWidth() / 2 + MathUtils.cosDeg(angle) * 1.5f,
				getY() + getHeight() / 2 + MathUtils.sinDeg(angle) * 1.5f);
		Bullet bullet = new Bullet("bullet.png", coords.x, coords.y, angle * MathUtils.degRad,
				world);

		getStage().addActor(bullet);

		bullet.body.setLinearVelocity(dir.nor().scl(1f));
		bullets.add(bullet);

	}

	public void checkKeys() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			body.setLinearVelocity(body.getLinearVelocity().x, 1);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			body.setLinearVelocity(-1, body.getLinearVelocity().y);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			body.setLinearVelocity(body.getLinearVelocity().x, -1);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			body.setLinearVelocity(1, body.getLinearVelocity().y);
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A)
				&& !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D)) {
			drawStill = true;
			body.setLinearVelocity(0, 0);
		} else {
			drawStill = false;
		}
	}

}
