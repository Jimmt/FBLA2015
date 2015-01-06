package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class Worm extends Boss {
	Array<WormSegment> segments;
	Array<RevoluteJoint> joints;
	Vector2 distance = new Vector2();
	float aggroRange = 5;

	class WormSegment extends Image {
		Body body;
		BodyDef bd;
		FixtureDef fd;
		PolygonShape shape;
		boolean first, isOpen;
		Drawable open, closed;
		float changeTime = 0, changeCap = 0.5f;

		public WormSegment(String path, boolean first, float x, float y, World world) {
			super(new Texture(Gdx.files.internal(path)));

			this.first = first;

			if (first) {
				open = getDrawable();
				isOpen = true;
				closed = new Image(new Texture(
						Gdx.files.internal("enemy/worm/wormsegmentfirst1.png"))).getDrawable();
			}

			setSize(getWidth() * Constants.SCALE, getHeight() * Constants.SCALE);
			setPosition(x, y);

			bd = new BodyDef();
			bd.position.set(x, y);
			bd.type = BodyType.DynamicBody;
			bd.linearDamping = 1.0f;
			body = world.createBody(bd);
			fd = new FixtureDef();
			shape = new PolygonShape();
			shape.setAsBox(getWidth() / 2, getHeight() / 2);
			fd.shape = shape;
			fd.density = 1.0f;
			fd.friction = 1.0f;
			body.createFixture(fd);
			UserData data = new UserData();
			data.tag = "wormsegment";
			data.value = Worm.this;
			body.setUserData(data);
			setOrigin(getWidth() / 2, getHeight() / 2);

		}

		@Override
		public void act(float delta) {
			super.act(delta);

			setPosition(body.getTransform().getPosition().x - getWidth() / 2, body.getTransform()
					.getPosition().y - getHeight() / 2);

			if (!first) {
				setRotation(body.getTransform().getRotation() * MathUtils.radDeg);
			} else {

				if (changeTime > changeCap) {
					changeTime = 0;

					if (isOpen) {
						setDrawable(closed);
					} else {
						setDrawable(open);
					}
					isOpen = !isOpen;

				} else {
					changeTime += delta;
				}
			}

		}

	}

	public Worm(float x, float y, World world) {
		super(1000, 20, "Worm");
		segments = new Array<WormSegment>();
		joints = new Array<RevoluteJoint>();

		setPosition(x, y);

		for (int i = 0; i < 5; i++) {
			boolean first = false;
			String path = "enemy/worm/wormsegment.png";
			if (i == 0) {
				first = true;
				path = "enemy/worm/wormsegmentfirst.png";
			}
			segments.add(new WormSegment(path, first, getX() + i * 0.5f, getY(), world));

			if (i > 0) {
				RevoluteJointDef def = new RevoluteJointDef();
				def.initialize(segments.get(i - 1).body, segments.get(i).body,
						segments.get(i - 1).body.getWorldCenter());
				def.bodyA = segments.get(i - 1).body;
				def.bodyB = segments.get(i).body;
				def.collideConnected = true;
				def.localAnchorA.set(segments.get(i).getWidth() / 2, 0);
				def.localAnchorB.set(-segments.get(i).getWidth() / 2, 0);
				def.collideConnected = false;
				def.enableLimit = true;
				def.upperAngle = MathUtils.PI / 8;
				def.lowerAngle = -MathUtils.PI / 8;
				RevoluteJoint joint = (RevoluteJoint) world.createJoint(def);
				joints.add(joint);

			}
		}

	}

	public void moveTowards(float x, float y) {

		Vector2 direction = new Vector2(x - segments.get(0).body.getWorldCenter().x, y
				- segments.get(0).body.getWorldCenter().y);
		segments.get(0).body.applyForceToCenter(
				direction.nor().scl(7f).sub(segments.get(0).body.getLinearVelocity()), true);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (player != null) {
			distance.set(player.getX() - segments.get(0).getX(), player.getY()
					- segments.get(0).getY());

			if (distance.len() <= aggroRange) {
				aggro = true;
				moveTowards(player.getX(), player.getY());
			}
		}

		for (int i = 0; i < segments.size; i++) {
			segments.get(i).act(delta);
		}
		segments.get(0).setRotation(distance.angle() + 180);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (int i = 0; i < segments.size; i++) {
			segments.get(i).draw(batch, parentAlpha);
		}
	}
}
