package com.jumpbuttonstudio.FBLA2015.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jumpbuttonstudio.FBLA2015.box2d.UserData;
import com.jumpbuttonstudio.FBLA2015.sprite.GameSprite;

public class Bullet extends GameSprite {
	public boolean delete;
	public boolean friendly;
	public boolean bounce;
	public float damage;

	public Bullet(String path, boolean friendly, float x, float y, float angle, float damage, World world) {
		super(path, x, y, angle, BodyType.DynamicBody, world);
		
		this.friendly = friendly;
		
		UserData userData = new UserData();
		userData.setValue(this);
		userData.setTag("bullet");
		body.setUserData(userData);
		body.getFixtureList().get(0).setSensor(true);
		this.damage = damage;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

}
