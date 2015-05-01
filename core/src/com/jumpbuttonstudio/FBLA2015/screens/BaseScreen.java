package com.jumpbuttonstudio.FBLA2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.ui.EscapeDialog;
import com.jumpbuttonstudio.FBLA2015.ui.HudTable;
import com.jumpbuttonstudio.FBLA2015.util.Constants;

/**
 * Base screen class - stages, ui
 */
public class BaseScreen implements Screen {
	public Stage stage;
	public World world;
	protected Stage hudStage;
	protected Stage dialogStage;
	protected Table table;
	protected HudTable hudTable;
	protected Skin skin;
	protected Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	protected Batch batch;
	protected FBLA2015 game;
	protected EscapeDialog dialog;
	protected StretchViewport hudViewport, viewport;
	boolean paused;

	public BaseScreen(FBLA2015 game) {
		this.game = game;

		stage = new Stage();
		camera = (OrthographicCamera) stage.getCamera();

		world = new World(new Vector2(0, 0f), false);

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);

		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		hudViewport = new StretchViewport(Constants.WIDTH, Constants.HEIGHT);
		viewport = new StretchViewport(Constants.SCLWIDTH, Constants.SCLHEIGHT);
		dialogStage = new Stage(hudViewport);

		InputMultiplexer multiplexer = new InputMultiplexer(dialogStage, stage);
		Gdx.input.setInputProcessor(multiplexer);

		dialog = new EscapeDialog(game, this, skin);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		if (!paused) {
			stage.act(delta);
			world.step(1 / 60f, 5, 5);
			FBLA2015.soundManager.setPlay(true);
		} else {
			FBLA2015.soundManager.setPlay(false);
		}
		stage.draw();

		dialogStage.draw();
		dialogStage.act(delta);

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			dialog.show(dialogStage);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		hudViewport.update(width, height);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {

	}

	public void pauseGame() {
		paused = !paused;
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
