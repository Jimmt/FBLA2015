package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends GameSprite {

	public Bullet(String path, float x, float y, float angle, World world) {
		super(path, x, y, angle, world);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
	}

}
