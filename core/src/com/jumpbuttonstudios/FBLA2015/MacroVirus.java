package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MacroVirus extends Boss {
	Body body;
	Image virus;
	float width, height, aggroRange = 4f, startAngle, finalAngle, angularSpeed = 2f;
	Vector2 direction = new Vector2();
	Gun cannon;

	public MacroVirus(float x, float y, World world) {
		super(250, 2f, "Macro Virus");

		virus = new Image(new Texture(Gdx.files.internal("level4/boss.png")));
		virus.setSize(virus.getWidth() * Constants.SCALE, virus.getHeight() * Constants.SCALE);
		width = virus.getWidth();
		height = virus.getHeight();

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(x, y);
		body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(width / 2);
		fd.shape = shape;
		body.createFixture(fd);
		UserData data = new UserData();
		data.tag = "macro";
		data.value = this;
		body.setUserData(data);
		virus.setOrigin(virus.getWidth() / 2, virus.getHeight() / 2);

		cannon = new Gun(world, 10f, ItemStats.MACRO_CANNON, body);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		cannon.updateFire(delta);
		virus.act(delta);
		cannon.act(delta);
		direction.set(player.getX() - body.getWorldCenter().x,
				player.getY() - body.getWorldCenter().y);
	}

	public void moveTowards(float x, float y) {

		body.applyForceToCenter(direction.nor().scl(7f).sub(body.getLinearVelocity()).scl(0.05f),
				true);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		virus.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		virus.draw(batch, parentAlpha);

		if (player != null) {
			if (direction.len() < aggroRange) {
				aggro = true;
				moveTowards(player.getX(), player.getY());
			}
		}

		cannon.draw(batch, parentAlpha);
		cannon.fire(width, 0, 0.5f, false, direction.cpy());

		startAngle = virus.getRotation();
		finalAngle = direction.angle() - 90;

		virus.addAction(Actions.rotateBy(-MathUtils.atan2(
				(MathUtils.sinDeg(startAngle - finalAngle)),
				MathUtils.cosDeg(startAngle - finalAngle))
				* angularSpeed));
	}

}
