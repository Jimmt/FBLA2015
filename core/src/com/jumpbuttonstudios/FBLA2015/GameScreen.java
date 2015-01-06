package com.jumpbuttonstudios.FBLA2015;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends BaseScreen {
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Array<RectangleMapObject> comms = new Array<RectangleMapObject>();
	Array<Enemy> enemies = new Array<Enemy>();
	Boss boss;
	Map map;
	Player player;
	ConeLight coneLight;
	PointLight pointLight;
	GameContactListener contactListener;
	StageComparator comparator;
	CommDialog commDialog;
	ShopDialog shopDialog;
	boolean hitComm, gameOver, finished, completeDialogShown, hasBoss;
	Image black;
	int byteCoins, levelNum, objectiveCount;
	AStar astar;
	ShapeRenderer sr;
	FBLA2015 game;

	public GameScreen(FBLA2015 game, String levelName, int levelNum) {
		super(game);

		this.game = game;
		this.levelNum = levelNum;
		
		if(levelNum == 0){
			hasBoss = false;
		} else {
			hasBoss = true;
		}
		
		ItemStats.makeCache();

		FitViewport viewport = new FitViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		stage = new Stage(viewport);
		FitViewport hudViewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		player = new Player("ship.png", world);
		map = new Map("maps/" + levelName + ".tmx", this);
		astar = new AStar(map.getMap().getProperties().get("width", Integer.class), map.getMap()
				.getProperties().get("height", Integer.class)) {
			protected boolean isValid(int x, int y) {
				return !map.collisions[x][y];
			}
		};
		stage.addActor(map);
		map.parseObstacles();
		stage.addActor(player);
		stage.addActor(player.gun);
		map.parsePositions();
		player.body.setTransform(map.getPlayerSpawn(), 0);

		hudStage = new Stage(hudViewport);
		dialogStage = new Stage(hudViewport);
		hudTable = new HudTable(player, this, skin);
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

		InputMultiplexer multiplexer = new InputMultiplexer(dialogStage, player.controller,
				hudStage, stage);
		Gdx.input.setInputProcessor(multiplexer);

// shopDialog = new ShopDialog("", hudStage, player, skin);
// shopDialog.show(hudStage);
// shopDialog.setPosition(Constants.WIDTH / 2 - shopDialog.getWidth() / 2,
// Constants.HEIGHT
// / 2 - shopDialog.getHeight() / 2);

		sr = new ShapeRenderer();

	}
	
	public void createBoss(Boss boss){
		this.boss = boss;
		boss.setPlayer(player);
		stage.addActor(boss);
	}

	public void createEnemy(ItemStats stats, float x, float y, float health, boolean objective) {

		Enemy enemy = new Enemy("enemy/enemy.png", stats, x, y, health, astar, world);
		enemy.objective = objective;
		enemy.setPlayer(player);
		stage.addActor(enemy);
		enemies.add(enemy);

	}

	public void gameOver() {
		if (!gameOver) {
			gameOver = true;
			GameOverDialog dialog = new GameOverDialog(this, game, skin);
			dialog.show(dialogStage);
		}
	}

	public void completeLevel() {
		if (!completeDialogShown) {
			LevelCompleteDialog dialog = new LevelCompleteDialog(game, this, skin);
			dialog.show(dialogStage);
			completeDialogShown = true;
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

// sr.setProjectionMatrix(stage.getCamera().combined);
// sr.begin(ShapeType.Filled);
// sr.setColor(Color.WHITE);
// if (enemies.get(0).path.size >= 2) {
// for (int i = 0; i < enemies.get(0).path.size; i++) {
// if (i + 1 < enemies.get(0).path.size - 1) {
// sr.box(enemies.get(0).path.get(i) * Constants.TILE_SIZE * Constants.SCALE,
// enemies.get(0).path.get(i + 1) * Constants.TILE_SIZE * Constants.SCALE,
// 0, 0.05f, 0.05f, 0);
// }
// }
// }
// sr.setColor(Color.CYAN);
// sr.end();

		stage.getActors().sort(comparator);

		hudStage.draw();
		hudStage.act(delta);
		dialogStage.draw();
		dialogStage.act(delta);

		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		hitComm = false;
		for (int i = 0; i < comms.size; i++) {

			if (comms.get(i).getRectangle().overlaps(player.commHitbox)) {
				commDialog.setText(comms.get(i).getProperties().get("text", String.class));
				hitComm = true;
			}

		}

		objectiveCount = 0;
		for (int i = 0; i < enemies.size; i++) {
			if (enemies.get(i).objective) {
				objectiveCount++;
			}

			if (enemies.get(i).health <= 0) {
				enemies.get(i).destroy();
				stage.getActors().removeValue(enemies.get(i), false);
				world.destroyBody(enemies.get(i).body);

				for (int j = 0; j < enemies.get(i).gun.bullets.size; j++) {
					world.destroyBody(enemies.get(i).gun.bullets.get(j).body);
				}

				enemies.removeIndex(i);
				byteCoins++;
			}
		}
		if (objectiveCount == 0 && ((boss != null && boss.health <= 0) || (!hasBoss))) {
			completeLevel();
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
