package com.jumpbuttonstudio.FBLA2015.util;

import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedImage extends Actor {
	AnimatedSprite animatedSprite;

	public AnimatedImage(String path, float frameTime, int width, int height, int...removeFrames) {
		super();

		Animation animation = AnimationBuilder.fillAnimation(frameTime, width, height,
				removeFrames, path);
		animation.setPlayMode(PlayMode.LOOP);

		animatedSprite = new AnimatedSprite(animation);
		animatedSprite.play();
		animatedSprite.setSize(width * Constants.SCALE, height * Constants.SCALE);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		animatedSprite.update(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		animatedSprite.draw(batch);

	}

}