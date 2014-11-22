package com.jumpbuttonstudios.FBLA2015;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends GameSprite {
	float speed = 5f;
	float stateTime = 0;
	Animation current, walkForward, walkBack, walkRight, walkLeft;
	Sprite temp, forward, back, right, left;
	int facing = 3;
	boolean drawStill;

	public Player(String path, World world) {
		super(path, world);

		temp = new Sprite();

		forward = new Sprite(new Texture(Gdx.files.internal("playerback.png")));
		forward.setSize(forward.getWidth() * 6f, forward.getHeight() * 6f);

		back = new Sprite(new Texture(Gdx.files.internal("player.png")));
		back.setSize(back.getWidth() * 6f, back.getHeight() * 6f);

		right = new Sprite(new Texture(Gdx.files.internal("playerright.png")));
		right.setSize(right.getWidth() * 6f, right.getHeight() * 6f);

		left = new Sprite(new Texture(Gdx.files.internal("playerright.png")));
		left.setSize(left.getWidth() * 6f, left.getHeight() * 6f);
		left.flip(true, false);

		walkBack = fillAnimation(1 / 3f, false, "playerwalk1.png", "playerwalk2.png");
		walkBack.setPlayMode(PlayMode.LOOP);

		walkForward = fillAnimation(1 / 3f, false, "playerbackwalk1.png", "playerbackwalk2.png");
		walkForward.setPlayMode(PlayMode.LOOP);

		walkRight = fillAnimation(1 / 3f, false, "playerwalkright.png", "playerright.png");
		walkRight.setPlayMode(PlayMode.LOOP);

		walkLeft = fillAnimation(1 / 3f, true, "playerwalkright.png", "playerright.png");
		walkLeft.setPlayMode(PlayMode.LOOP);
		
	}

	public Animation fillAnimation(float frameTime, boolean flipX, String... paths) {
		TextureRegion[] regions = new TextureRegion[paths.length];

		for (int i = 0; i < regions.length; i++) {
			TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal(paths[i])));
			region.flip(flipX, false);
			regions[i] = region;
		}

		return new Animation(frameTime, regions);
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);

		if (current != null && !drawStill) {
			TextureRegion frame = current.getKeyFrame(stateTime);
			temp = new Sprite(frame.getTexture());
			temp.setFlip(frame.isFlipX(), frame.isFlipY());
			temp.setSize(temp.getWidth() * 6, temp.getHeight() * 6);
			temp.setPosition(getX(), getY());
			set(temp);
		}
		

		if (drawStill) {
			right.setPosition(getX(), getY());
			forward.setPosition(getX(), getY());
			back.setPosition(getX(), getY());
			left.setPosition(getX(), getY());

			switch (facing) {
			case 0:
				set(right);
				break;
			case 1:
				set(forward);
				break;
			case 2:
				set(left);
				break;
			case 3:
				set(back);
				break;
			}
		}

	}

	@Override
	public void update(float delta) {
		super.update(delta);

		stateTime += delta;

		if (Gdx.input.isKeyPressed(Keys.W)) {
			setY(getY() + speed);
			facing = 1;
			current = walkForward;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			setX(getX() - speed);
			facing = 2;
			current = walkLeft;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			setY(getY() - speed);
			current = walkBack;
			facing = 3;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			setX(getX() + speed);
			current = walkRight;
			facing = 0;
		}

		if (!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.A)
				&& !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D)) {
			drawStill = true;
		} else {
			drawStill = false;
		}
	}

}
