package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Laser extends Image {
	Image proj1, proj2;
	float repeat, lastRepeat = 999f;
	Rectangle hitbox;
	Player player;

	public Laser(Player player, float x, float y, float width, float height, float repeat,
			String path) {
		super(new Texture(Gdx.files.internal(path)));

		String projPath = "obstacle/projector.png";
		if (path.contains("black")) {
			projPath = "obstacle/blackprojector.png";
		}

		proj1 = new Image(new Texture(Gdx.files.internal(projPath)));
		proj2 = new Image(new Texture(Gdx.files.internal(projPath)));
		proj1.setSize(proj1.getWidth() * Constants.SCALE, proj1.getHeight() * Constants.SCALE);
		proj2.setSize(proj2.getWidth() * Constants.SCALE, proj2.getHeight() * Constants.SCALE);
		proj2.setOrigin(proj2.getWidth() / 2, proj2.getHeight() / 2);
		proj2.setRotation(180);

		this.player = player;
		this.repeat = repeat;

		setPosition(x, y);
		setSize(width, height);

		hitbox = new Rectangle(x, y, width, height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		proj1.setPosition(getX(), getY() + getHeight());
		proj2.setPosition(getX(), getY() - proj2.getHeight());
		proj1.draw(batch, parentAlpha);
		proj2.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		proj1.act(delta);
		proj2.act(delta);

		hitbox.set(getX(), getY(), getWidth(), getHeight());

		if (hitbox.overlaps(player.hitbox) && getColor().a >= 0.2f) {
			player.hurt(1);

		}

		if (getActions().size == 0) {
			addAction(Actions.sequence(Actions.alpha(0, 0.1f), Actions.delay(1f),
					Actions.alpha(1, 0.1f), Actions.delay(1f)));
		}

	}
}
