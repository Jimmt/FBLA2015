package com.jumpbuttonstudio.FBLA2015.boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.box2d.EnemyRaycastCallback;
import com.jumpbuttonstudio.FBLA2015.box2d.UserData;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.Textures;
import com.jumpbuttonstudio.FBLA2015.weapon.ColorGun;
import com.jumpbuttonstudio.FBLA2015.weapon.ItemStats;

/**
 * Trojan Horse boss class
 *
 */
public class TrojanHorse extends Boss {
	public Body body;
	Image horse;
	Vector2 distance = new Vector2(), temp = new Vector2(), direction = new Vector2();
	Array<ColorGun> guns = new Array<ColorGun>();
	float aggroRange = 4, lastMoveTime = 0f, moveCap = 2f;
	EnemyRaycastCallback callback;
	World world;
	boolean active;

	public TrojanHorse(float x, float y, World world) {
		super(500, 20, "Trojan Horse");

		setPosition(x, y);
		this.world = world;

		horse = new Image(Textures.getTex("level3/horse.png"));
		horse.setSize(horse.getWidth() * Constants.SCALE, horse.getHeight() * Constants.SCALE);

		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.DynamicBody;
		body = world.createBody(bd);
		CircleShape shape = new CircleShape();
		shape.setRadius((horse.getWidth() + horse.getHeight()) / 4);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = 0.0f;
		body.createFixture(fd);
		UserData data = new UserData();
		data.tag = "horse";
		data.value = this;
		body.setUserData(data);

		horse.setOrigin(horse.getWidth() / 2, horse.getHeight() / 2);
		callback = new EnemyRaycastCallback();

		Color[] colors = { Color.RED, Color.YELLOW, Color.ORANGE, Color.GREEN, Color.BLUE,
				Color.OLIVE, Color.PURPLE, Color.CYAN, Color.GRAY, Color.WHITE };
		
		for (int i = 0; i < 10; i++) {
			ColorGun gun = new ColorGun(world, 2f, ItemStats.HORSE, body, colors[i]);
			gun.bounce = true;
			guns.add(gun);
		}
	}

	public void moveTowards(float x, float y) {

		direction.set(x - body.getWorldCenter().x, y - body.getWorldCenter().y);
		body.applyForceToCenter(direction.nor().scl(7f).sub(body.getLinearVelocity()).scl(0.1f),
				true);
	}

	public void activate() {
		active = true;
		aggro = true;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		world.rayCast(callback, player.body.getWorldCenter(), body.getWorldCenter());

		if (active) {
			for (int i = 0; i < guns.size; i++) {
				guns.get(i).fireStraight(horse.getWidth() / 2f, 0, ((float) i) / guns.size * 360f);

				guns.get(i).act(delta);
				guns.get(i).updateFire(delta);
			}
		}
		horse.act(delta);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (active) {
			for (int i = 0; i < guns.size; i++) {
				guns.get(i).draw(batch, parentAlpha);

				if (guns.get(i).bullets.size > 2) {
					guns.get(i).bullets.get(0).delete = true;
				}
			}

			if (player != null) {
				distance.set(player.getX() - horse.getX(), player.getY() - horse.getY());

				if (aggro) {
					moveTowards(player.getX(), player.getY());

				}
				if (player.getX() - (horse.getX() + horse.getWidth() / 2) > 0) {
					horse.setScaleX(1);
				} else {
					horse.setScaleX(-1);
				}
			}
		}

		horse.setPosition(body.getPosition().x - horse.getWidth() / 2,
				body.getPosition().y - horse.getHeight() / 2);
		horse.draw(batch, parentAlpha);
	}

}
