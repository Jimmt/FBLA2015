package com.jumpbuttonstudio.FBLA2015.util;

//import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Actor wrapper around dermetfan's AnimatedSprite
 */
public class AnimatedImage extends Actor {
	net.dermetfan.gdx.graphics.g2d.AnimatedSprite animatedSprite;

	public AnimatedImage(String path, float frameTime, int width, int height, int...removeFrames) {
		super();

		Animation animation = AnimationBuilder.fillAnimation(frameTime, width, height,
				removeFrames, path);
		animation.setPlayMode(PlayMode.LOOP);

		animatedSprite = new net.dermetfan.gdx.graphics.g2d.AnimatedSprite(animation);
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
