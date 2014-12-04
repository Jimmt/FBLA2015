package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends BaseScreen {

	public MenuScreen(FBLA2015 game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		table.setFillParent(true);
		table.add("Menu Screen");
		table.row();

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		TextImageButton start = new TextImageButton("Start", skin.getFont("default-font"), style);
		TextImageButton test = new TextImageButton("Test", skin.getFont("default-font"), style);

		table.add(start);
		table.row();
		table.add(test);
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));

			}
		});

	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
