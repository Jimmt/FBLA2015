package com.jumpbuttonstudios.FBLA2015;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Map map;
	Player player;
	ConeLight coneLight;
	PointLight pointLight;
	GameContactListener contactListener;
	StageComparator comparator;

	public GameScreen(FBLA2015 game) {
		super(game);

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		stage = new Stage(viewport);
		FitViewport hudViewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		player = new Player("still.png", world);
		map = new Map("maps/map.tmx", this);
		stage.addActor(map);
		stage.addActor(player.jetpack);
		stage.addActor(player.jetpack.flame);
		stage.addActor(player);
		stage.addActor(player.arm);
		map.parsePositions();
		player.body.setTransform(map.getPlayerSpawn(), 0);

		hudStage = new Stage(hudViewport);
		hudTable = new HudTable(player, skin);
		hudTable.setFillParent(true);
		hudStage.addActor(hudTable);

		contactListener = new GameContactListener();
		world.setContactListener(contactListener);

		comparator = new StageComparator();
	}

	public void createEnemy(float x, float y) {
		Enemy enemy = new Enemy("robotstill.png", "robotwalk.png", x, y, 100, world);
		enemy.setPlayer(player);
		stage.addActor(enemy);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		stage.getActors().sort(comparator);

		hudStage.draw();
		hudStage.act(delta);

		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		if (FBLA2015.DEBUG) {
			hudTable.debug();
			Table.drawDebug(hudStage);
		}
// rayHandler.updateAndRender();

	}

}
