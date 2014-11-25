package com.jumpbuttonstudios.FBLA2015;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Map map;
	Player player;
	PointLight light;

	public GameScreen(FBLA2015 game) {
		super(game);

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		stage = new Stage(viewport);
		
		

		map = new Map("map.tmx", camera);

		player = new Player("still.png", world);
		sprites.add(player);
		stage.addActor(player);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);

		light = new PointLight(rayHandler, 32, new Color(0, 0, 0.1f, 1), 1600, 812, 0);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		batch.begin();

		map.draw(batch);

		batch.end();

		light.setPosition(camera.position.x, camera.position.y);
		rayHandler.setCombinedMatrix(camera.combined);

// rayHandler.updateAndRender();

	}

}
