package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class Player extends GameSprite {

	float speed = 5f, healthMax = 100, health = healthMax;
	float angle, stateTime, feetWidth;
	boolean drawStill, facingRight, inAir = true;

	Vector2 centerPosition, mouse, dir;
	Vector3 coords = new Vector3();

	World world;
	Body feet;
	Arm arm;
	Gun gun;
	Jetpack jetpack;
	PlayerInputController controller;
	AnimatedImage walk;
	Image still;
	Rectangle hitbox;

	public Player(String path, World world) {
		super(path, world);
		hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
		still = new Image(getDrawable());
		controller = new PlayerInputController(this, 2);
		this.world = world;

		arm = new Arm("pistol.png", getX() + 0.2f, getY() + 1.2f, 0);
		gun = new Gun(0.2f, 10, world, this);
		jetpack = new Jetpack("jetpack/jetpack.png", this, 1.0f, 1.0f, 2, 1 / 250f);

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

		walk = new AnimatedImage("Run/WithoutArms.png", 1 / 5f, 150, 200, 11);
		
		body.getFixtureList().get(0).setFriction(0.0f);
		Filter f = body.getFixtureList().get(0).getFilterData();
		f.categoryBits = Bits.PLAYER;
		f.maskBits = (short) (Bits.ENEMY | Bits.MAP);
		body.getFixtureList().get(0).setFilterData(f);
	}
	
	public void hurt(float amount){
		health -= amount;
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		feet.setTransform(getX() + (getWidth() - feetWidth) / 2 + feetWidth / 2, getY(), 0);
		walk.animatedSprite.setPosition(getX() - 0.35f, getY());

		if (drawStill) {
			setDrawable(still.getDrawable());
		} else {
			if (inAir) {
				setDrawable(still.getDrawable());
			} else {
				setDrawable(null);
				walk.draw(batch, parentAlpha);
			}
		}
		
		if(!inAir){
			body.setTransform(body.getTransform().getPosition(), 0);
		}

		gun.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		
		hitbox.set(getX() / Constants.SCALE, getY() / Constants.SCALE, getWidth() / Constants.SCALE, getHeight() / Constants.SCALE);
		walk.act(delta);

		stateTime += delta;

		checkKeys();

		getStage().getCamera().position.set(Math.round((getX() + getWidth() / 2) * 100f) / 100f,
				Math.round((getY() + getHeight() / 2) * 100f) / 100f, 0);

		gun.updateFire(delta);

		checkMouseRotation();
		
		if (angle > 270 || angle < 90) {
			facingRight = true;
			setScaleX(1);
			arm.setRotation(angle);
			arm.setScaleY(1);
			if (inAir || drawStill) {
				arm.setPosition(getX() + 0.15f, getY() + 0.9f);
			} else {
				arm.setPosition(getX() + 0.15f, getY() + 0.9f);
			}
			jetpack.setScaleX(1);
			jetpack.setPosition(getX() - jetpack.getWidth() / 3,
					getY() + getHeight() - jetpack.getHeight() * 1.5f);

		} else {
			facingRight = false;
			setScaleX(-1);
			arm.setRotation(angle);
			arm.setScaleY(-1);
			if (inAir || drawStill) {
				arm.setPosition(getX() + 0.3f, getY() + 0.9f);
			} else {
				arm.setPosition(getX() + 0.33f, getY() + 0.9f);
			}
			jetpack.setScaleX(-1);
			jetpack.setPosition(getX() + getWidth() - jetpack.getWidth() / 3 * 2, getY() + getHeight()
					- jetpack.getHeight() * 1.5f);
		}
		
		gun.act(delta);
		controller.update(delta);
	}

	public void checkMouseRotation() {
		centerPosition = new Vector2(Constants.WIDTH / 2, Constants.HEIGHT / 2);

		mouse = new Vector2(Gdx.input.getX(), Constants.HEIGHT - Gdx.input.getY());

		dir = mouse.sub(centerPosition);
		angle = dir.angle();

	}

	public void checkKeys() {

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A)
				&& !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D)) {
			drawStill = true;
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		} else {

			drawStill = false;

		}
	}

}
