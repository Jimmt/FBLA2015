package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameSprite extends Image {
	private World world;
	PolygonShape shape;
	BodyDef bodyDef;
	FixtureDef fixtureDef;
	public Body body;
	float width, height;

	public GameSprite(String path, float x, float y, float angle, float scale, World world) {
		super(new Texture(Gdx.files.internal(path)));

		width = getWidth() * Constants.SCALE * scale;
		height = getHeight() * Constants.SCALE * scale;

		this.world = world;

		shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.groupIndex = -1;

		setSize(width, height);
		setOrigin(width / 2, height / 2);
		createBody();
		body.setTransform(x, y, angle);
		setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
	}
	
	public GameSprite(String path, float x, float y, float angle, World world) {
		super(new Texture(Gdx.files.internal(path)));

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
		createBody();
		body.setTransform(x, y, angle);
		setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
	}

	public GameSprite(String path, World world) {
		this(path, 0, 0, 0, world);
	}

	public void createBody() {
		bodyDef.type = BodyType.DynamicBody;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		setRotation(MathUtils.radDeg * body.getTransform().getRotation());
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);

	}
}
