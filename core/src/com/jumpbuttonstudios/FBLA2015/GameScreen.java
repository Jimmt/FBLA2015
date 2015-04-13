package com.jumpbuttonstudios.FBLA2015;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends BaseScreen {
	Rectangle trigger;
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
	boolean hitComm, gameOver, finished, completeDialogShown, hasBoss = true, musicStopped;
	float vol, lastChange, changeCap = 0.1f, dv;
	Image black;
	int byteCoins, levelNum, objectiveCount, enemiesKilled;
	AStar astar;
	ShapeRenderer sr;
	FBLA2015 game;
	ParticleEffectPool pool;
	Array<ParticleEffectActor> effects;
	BossHealthDialog hpDialog;
	String explanationText;

	public GameScreen(FBLA2015 game, String levelName) {
		super(game);

		vol = FBLA2015.soundManager.musics.get("menu").getVolume();
		dv = vol / 10;

		this.game = game;

		ItemStats.makeCache();

		stage = new Stage(viewport);

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

		ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal(""));
		ParticleEmitter emitter = effect.getEmitters().get(0);
		emitter.getScale().setHigh(emitter.getScale().getHighMax() * Constants.SCALE);
		emitter.getVelocity().setHigh(emitter.getVelocity().getHighMin() * Constants.SCALE,
				emitter.getVelocity().getHighMax() * Constants.SCALE);
		pool = new ParticleEffectPool(effect, 3, 3);
		effects = new Array<ParticleEffectActor>();

	}

	public void createBoss(Boss boss) {
		this.boss = boss;
		boss.setPlayer(player);
		stage.addActor(boss);
	}

	public void createEnemy(ItemStats stats, float range, String path, float x, float y,
			float health, boolean objective) {

		Enemy enemy = new Enemy(path, stats, x, y, health, astar, world);
		enemy.objective = objective;
		enemy.setPlayer(player);
		enemy.setRange(range);
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
			dialog.setPosition(Constants.WIDTH / 2 - dialog.getWidth() / 2, Constants.HEIGHT / 2
					- dialog.getHeight() / 2);
			dialogStage.addActor(dialog);
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
		
		if(trigger != null && player.hitbox.overlaps(trigger)){
			boss.aggro = true;
		}

		if (!musicStopped) {
			if (lastChange > changeCap) {
				vol -= dv;
				FBLA2015.soundManager.musics.get("menu").setVolume(vol);
				lastChange = 0;

				if (vol <= 0) {
					musicStopped = true;
					FBLA2015.soundManager.stopMusic("menu");
				}
			} else {
				lastChange += delta;
			}
		}

		stage.getActors().sort(comparator);
		stage.draw();
		stage.act(delta);
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

		
		if (boss != null) {
			if (boss.aggro) {
				if (hpDialog == null) {
					hpDialog = new BossHealthDialog(boss, skin);
					hpDialog.show(hudStage);
					hpDialog.addAction(Actions.sequence(Actions.moveBy(0, Constants.HEIGHT / 2),
							Actions.moveBy(0, Constants.HEIGHT / 2, 1f)));
				}
			}
			if (boss.health <= 0) {
				if (hpDialog != null) {
					hpDialog.addAction(Actions.fadeOut(1f, Interpolation.pow2Out));
				}

			}
		}

		objectiveCount = 0;

		for (int i = 0; i < effects.size; i++) {
			if (effects.get(i).effect.isComplete()) {
				stage.getActors().removeValue(effects.get(i), false);
				effects.removeIndex(i);
			}
		}

		for (int i = 0; i < enemies.size; i++) {
			if (enemies.get(i).objective) {
				objectiveCount++;
			}

			if (enemies.get(i).health <= 0) {
				ParticleEffectActor effect = new ParticleEffectActor(pool.obtain());
				effects.add(effect);
				stage.addActor(effect);
				effect.effect.start();
				effect.effect.setPosition(enemies.get(i).getX(), enemies.get(i).getY());
				enemies.get(i).destroy();

				stage.getActors().removeValue(enemies.get(i), false);
				world.destroyBody(enemies.get(i).body);

				FBLA2015.soundManager.play("enemydeath");

				for (int j = 0; j < enemies.get(i).gun.bullets.size; j++) {
					world.destroyBody(enemies.get(i).gun.bullets.get(j).body);
				}

				enemies.removeIndex(i);
				byteCoins++;
				enemiesKilled++;

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

		if (FBLA2015.DEBUG) {
			debugRenderer.render(world, stage.getCamera().combined);
		}
	}

}
