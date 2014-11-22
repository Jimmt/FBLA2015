package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends BaseScreen {

	public MenuScreen(FBLA2015 game){
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		table.setFillParent(true);
		table.add("Menu Screen");
		table.row();
		
		TextButton start = new TextButton("Start", skin);
		table.add(start);
		start.addListener(new ClickListener(){
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
