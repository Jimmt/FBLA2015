package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameSprite {
	Body feet;
	float speed = 5f;
	float angle, stateTime, feetWidth;
	boolean drawStill, canJump;
	Vector2 centerPosition, mouse, dir;
	Vector3 coords = new Vector3();
	World world;
	Arm arm;
	Gun gun;

	public Player(String path, World world) {
		super(path, world);

		this.world = world;
		arm = new Arm("pistol.png", getX() + 0.2f, getY() + 1.2f, 0);
		gun = new Gun(0.2f, world, this);

		PolygonShape feetShape = new PolygonShape();
		feetWidth = getWidth() / 4;
		feetShape.setAsBox(feetWidth, getHeight() / 8);
		BodyDef feetbd = new BodyDef();
		feetbd.type = BodyType.DynamicBody;
		feetbd.gravityScale = 0.0f;
		feet = world.createBody(feetbd);
		FixtureDef feetfd = new FixtureDef();
		feetfd.isSensor = true;
		feetfd.shape = feetShape;
		feet.createFixture(feetfd);
		
		UserData fuserData = new UserData();
		fuserData.setValue(this);
		fuserData.setTag("feet");
		feet.setUserData(fuserData);
		
		UserData userData = new UserData();
		userData.setValue(this);
		userData.setTag("player");
		body.setUserData(userData);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		feet.setTransform(getX() + (getWidth() - feetWidth) / 2 + feetWidth / 2, getY(), 0);
		arm.draw(batch, parentAlpha);
		gun.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta;

		checkKeys();

		getStage().getCamera().position.set(Math.round((getX() + getWidth() / 2) * 100f) / 100f,
				Math.round((getY() + getHeight() / 2) * 100f) / 100f, 0);

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			gun.fire(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY(), dir);
		}
		gun.updateFire(delta);

		checkMouseRotation();

		if (angle > 270 || angle < 90) {
			setScaleX(1);
			arm.setRotation(angle);
			arm.setScaleY(1);
			arm.setPosition(getX() + 0.15f, getY() + 0.9f);
		} else {
			setScaleX(-1);
			arm.setRotation(angle);
			arm.setScaleY(-1);
			arm.setPosition(getX() + 0.3f, getY() + 0.9f);
		}

		arm.act(delta);
		gun.act(delta);

	}

	public void checkMouseRotation() {
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		mouse = new Vector2(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());

		dir = mouse.sub(centerPosition);
		angle = dir.angle();

	}

	public void checkKeys() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			if (canJump) {
				body.setLinearVelocity(body.getLinearVelocity().x, 2);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			body.setLinearVelocity(-1, body.getLinearVelocity().y);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			body.setLinearVelocity(1, body.getLinearVelocity().y);
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A)
				&& !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D)) {
			drawStill = true;
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		} else {
			drawStill = false;
		}
	}

}
