package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class GameSprite extends Sprite {
	Rectangle hitbox;

	public GameSprite(String path, float width, float height, World world) {
		super(new Texture(Gdx.files.internal(path)));
		setSize(width, height);
		hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public GameSprite(String path, World world) {
		super(new Texture(Gdx.files.internal(path)));
		hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	
	public void update(float delta){
		hitbox.set(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void draw(Batch batch){
		super.draw(batch);
		
	}
}
