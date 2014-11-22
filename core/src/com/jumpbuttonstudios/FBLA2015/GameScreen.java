package com.jumpbuttonstudios.FBLA2015;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Map map;
	Player player;
	PointLight light;

	public GameScreen(FBLA2015 game) {
		super(game);
		player = new Player("player.png", world);
		player.setPosition(812, 0);
		sprites.add(player);

		map = new Map("map.tmx", camera);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);

		light = new PointLight(rayHandler, 32, new Color(0, 0, 0.1f, 1), 1600, 812, 0);
		
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		batch.begin();

		map.draw(batch);

		camera.position.set(player.getX(), player.getY(), 0);

		for (int i = 0; i < sprites.size; i++) {
			sprites.get(i).draw(batch);
			sprites.get(i).update(delta);
		}

		batch.end();

		light.setPosition(camera.position.x, camera.position.y);
		rayHandler.setCombinedMatrix(camera.combined);
//		rayHandler.updateAndRender();

	}

}
