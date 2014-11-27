package com.jumpbuttonstudios.FBLA2015;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Map map;
	Player player;
	PointLight light;
	GameContactListener contactListener;

	public GameScreen(FBLA2015 game) {
		super(game);

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		stage = new Stage(viewport);

		map = new Map("map.tmx", world, (OrthographicCamera) stage.getCamera());

		player = new Player("still.png", world);
		player.body.setTransform(50 * 32 * Constants.SCALE, 90 * 32 * Constants.SCALE, 0);
		stage.addActor(map);
		stage.addActor(player);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);

		light = new PointLight(rayHandler, 32, new Color(0, 0, 0.1f, 1), 1600, 812, 0);

		contactListener = new GameContactListener();
		world.setContactListener(contactListener);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		light.setPosition(camera.position.x, camera.position.y);
		rayHandler.setCombinedMatrix(camera.combined);

// rayHandler.updateAndRender();

	}

}
