package com.jumpbuttonstudio.FBLA2015.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class GameSprite extends Image {
	private World world;
	PolygonShape shape;
	CircleShape cshape;
	BodyDef bodyDef;
	FixtureDef fixtureDef;
	public Body body;
	float width, height;
	boolean stdRotate = true;

	public GameSprite(String path, float x, float y, float angle, float scale, boolean circle,
			BodyType type, World world) {
		super(Textures.getTex(path));

		width = getWidth() * Constants.SCALE * scale;
		height = getHeight() * Constants.SCALE * scale;

		this.world = world;

		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();

		if (circle) {
			cshape = new CircleShape();
			cshape.setRadius((width + height) / 4);
			fixtureDef.shape = cshape;
		} else {
			shape = new PolygonShape();
			shape.setAsBox(width / 2, height / 2);
			fixtureDef.shape = shape;
		}
		fixtureDef.filter.groupIndex = -1;

		setSize(width, height);
		setOrigin(width / 2, height / 2);
		createBody(type);
		body.setTransform(x, y, angle);
		setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
	}

	public GameSprite(String path, float x, float y, float angle, BodyType type, World world) {
		super(Textures.getTex(path));

		width = getWidth() * Constants.SCALE;
		height = getHeight() * Constants.SCALE;

		this.world = world;

		shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		setSize(width, height);
		setOrigin(width / 2, height / 2);
		createBody(type);
		body.setTransform(x, y, angle);
		setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
	}

	public GameSprite(String path, float scale, World world) {
		this(path, 0, 0, 0, scale, true, BodyType.DynamicBody, world);
	}

	public void createBody(BodyType type) {
		bodyDef.type = type;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (stdRotate) {
			setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		}

		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

	}
}
