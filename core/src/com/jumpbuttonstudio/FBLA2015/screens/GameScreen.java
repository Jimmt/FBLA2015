package com.jumpbuttonstudio.FBLA2015.screens;

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
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.StageComparator;
import com.jumpbuttonstudio.FBLA2015.boss.Boss;
import com.jumpbuttonstudio.FBLA2015.box2d.GameContactListener;
import com.jumpbuttonstudio.FBLA2015.levels.Map;
import com.jumpbuttonstudio.FBLA2015.sprite.AStar;
import com.jumpbuttonstudio.FBLA2015.sprite.Enemy;
import com.jumpbuttonstudio.FBLA2015.sprite.GameSprite;
import com.jumpbuttonstudio.FBLA2015.sprite.Player;
import com.jumpbuttonstudio.FBLA2015.ui.BossHealthDialog;
import com.jumpbuttonstudio.FBLA2015.ui.CommDialog;
import com.jumpbuttonstudio.FBLA2015.ui.GameOverDialog;
import com.jumpbuttonstudio.FBLA2015.ui.HudTable;
import com.jumpbuttonstudio.FBLA2015.ui.LevelCompleteDialog;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.util.ParticleEffectActor;
import com.jumpbuttonstudio.FBLA2015.weapon.ItemStats;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class GameScreen extends BaseScreen {
	public Player player;
	public Array<RectangleMapObject> comms = new Array<RectangleMapObject>();
	public String explanationText;
	public int byteCoins = GamePrefs.prefs.getInteger("credits");
	protected Rectangle trigger;
	protected Boss boss;
	protected Map map;
	protected String levelName;
	protected CommDialog commDialog;
	protected BossHealthDialog hpDialog;
	protected boolean hasBoss = true;
	protected int objectiveCount;
	protected int enemiesKilled;
	protected boolean gameOver;
	RayHandler rayHandler;
	Array<GameSprite> sprites = new Array<GameSprite>();
	Array<Enemy> enemies = new Array<Enemy>();
	GameContactListener contactListener;
	StageComparator comparator;
	boolean hitComm;
	boolean finished;
	boolean completeDialogShown;
	boolean musicStopped;
	float vol, lastChange, changeCap = 0.1f, dv;
	Image black;
	int levelNum;
	AStar astar;
	ShapeRenderer sr;
	FBLA2015 game;
	ParticleEffectPool pool;
	Array<ParticleEffectActor> effects;

	public GameScreen(FBLA2015 game, String levelName, boolean showStory) {
		super(game);

		vol = FBLA2015.soundManager.musics.get("menu").getVolume();
		dv = vol / 10;
		this.levelName = levelName;
		this.game = game;
		ItemStats.makeCache();
		stage = new Stage(viewport);

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(stage.getCamera().combined);

		player = new Player(world);
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
		commDialog.setPosition(Constants.WIDTH - commDialog.getWidth() - 10, 10);
		black = new Image(Textures.getTex("ui/black.png"));

		InputMultiplexer multiplexer = new InputMultiplexer(dialogStage, player.controller,
				hudStage, stage);
		Gdx.input.setInputProcessor(multiplexer);

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
			GamePrefs.putInteger("credits", GamePrefs.prefs.getInteger("credits") + byteCoins);
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

		if (trigger != null && player.hitbox.overlaps(trigger)) {
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
				effect.scaled = true;
				effects.add(effect);
				stage.addActor(effect);
				effect.effect.start();
				effect.effect.setPosition(enemies.get(i).getX(), enemies.get(i).getY());

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
// storyDialog.debug();
			Table.drawDebug(hudStage);
			Table.drawDebug(dialogStage);
			debugRenderer.render(world, stage.getCamera().combined);
		}
// rayHandler.updateAndRender();

	}

}
