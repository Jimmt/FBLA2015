package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Enemy extends GameSprite {
	float healthMax, health;
	AnimatedImage walk;
	Image still;
	Player player;
	Gun gun;
	Jetpack jetpack;
	boolean drawStill, facingLeft = true, clearShot;
	Vector2 direction;
	World world;
	EnemyRaycastCallback callback;

	public Enemy(String path, String animPath, float x, float y, float healthMax,
			World world) {
		super(path, x, y, 0, 2, world);

		this.world = world;
		direction = new Vector2();
		gun = new Gun(0.2f, world, this);
		jetpack = new Jetpack("jetpack/jetpack.png", this, 999, -5, 2, 1 / 300f);
		this.healthMax = healthMax;
		health = healthMax;
		UserData userData = new UserData();
		userData.value = this;
		userData.tag = "enemy";

		body.setUserData(userData);
// walk = new AnimatedImage(animPath, 1 / 8f, 96, 96);
		walk = new AnimatedImage(animPath, 1 / 8f, 64, 64);
// System.out.println(walk.animatedSprite.getAnimation().getKeyFrames().length);
		walk.animatedSprite.setSize(walk.animatedSprite.getWidth() * 2,
				walk.animatedSprite.getHeight() * 2);

		still = new Image(getDrawable());
		callback = new EnemyRaycastCallback();
		
		Filter f = body.getFixtureList().get(0).getFilterData();
		f.categoryBits = Bits.ENEMY;
		f.maskBits = (short) (Bits.PLAYER | Bits.MAP);
		body.getFixtureList().get(0).setFilterData(f);
	}
	
	public void setPlayer(Player player){
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

		if (!callback.isHit()) {
			gun.fire(1f, 5f, 0.1f, direction);
		}

		gun.updateFire(delta);
		gun.act(delta);
		follow(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		gun.draw(batch, parentAlpha);

		if (drawStill) {
			setDrawable(still.getDrawable());
		} else {
			setDrawable(null);
			walk.draw(batch, parentAlpha);
			walk.animatedSprite.setPosition(getX(), getY());
		}

		
		
	}
	


	public void follow(float delta) {
		drawStill = false;
		

		if (player.getX() < getX()) {
			setScale(1);
			if (walk.animatedSprite.isFlipX()) {
				walk.animatedSprite.flipFrames(true, false);
			}
			body.setLinearVelocity(-1, body.getLinearVelocity().y);
		}
		if (player.getX() > getX()) {
			setScale(-1);
			if (!walk.animatedSprite.isFlipX()) {
				walk.animatedSprite.flipFrames(true, false);
			}
			body.setLinearVelocity(1, body.getLinearVelocity().y);
		}
	}

}
