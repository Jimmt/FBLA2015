package com.jumpbuttonstudios.FBLA2015;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Array<RectangleMapObject> comms = new Array<RectangleMapObject>();
	Array<Enemy> enemies = new Array<Enemy>();
	Map map;
	Player player;
	ConeLight coneLight;
	PointLight pointLight;
	GameContactListener contactListener;
	StageComparator comparator;
	CommDialog commDialog;
	boolean hitComm, gameOver;
	Image black;

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
		Pixmap crosshairs = new Pixmap(Gdx.files.internal("ui/crosshairs.png"));
		Gdx.input.setCursorImage(crosshairs, 16, 16);

		commDialog = new CommDialog(skin);
		commDialog.show(hudStage);
		commDialog.setPosition(Constants.WIDTH - commDialog.getWidth(), 0);
		black = new Image(new Texture(Gdx.files.internal("ui/black.png")));

	}

	public void createEnemy(float x, float y, float health) {
		Enemy enemy = new Enemy("robotstill.png", "robotwalk.png", x, y, health, world);
		enemy.setPlayer(player);
		stage.addActor(enemy);
		enemies.add(enemy);
		
	}

	public void gameOver() {
		if (!gameOver) {
			gameOver = true;
			GameOverDialog dialog = new GameOverDialog(skin);
			dialog.show(hudStage);
			// go to level select
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		stage.getActors().sort(comparator);

		hudStage.draw();
		hudStage.act(delta);

		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		hitComm = false;
		for (int i = 0; i < comms.size; i++) {

			if (comms.get(i).getRectangle().overlaps(player.hitbox)) {
				commDialog.setText(comms.get(i).getProperties().get("text", String.class));
				hitComm = true;
			}

		}
		for (int i = 0; i < enemies.size; i++) {
			if (enemies.get(i).health <= 0) {
				enemies.get(i).destroy();
				stage.getActors().removeValue(enemies.get(i), false);
				world.destroyBody(enemies.get(i).body);
				enemies.removeIndex(i);
			}
		}

		if (player.health <= 0) {
			gameOver();
		}

		if (!hitComm) {
			commDialog.addAction(Actions.fadeOut(0.5f));
		} else {
			commDialog.addAction(Actions.fadeIn(0.5f));
		}

		if (FBLA2015.DEBUG) {
			hudTable.debug();
			Table.drawDebug(hudStage);
		}
// rayHandler.updateAndRender();

	}

}
