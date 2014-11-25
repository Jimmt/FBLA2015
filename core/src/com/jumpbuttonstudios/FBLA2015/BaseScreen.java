package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseScreen implements Screen {
	protected Stage stage;
	protected Table table;
	protected World world;
	protected Skin skin;
	protected Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	protected Batch batch;

	protected FBLA2015 game;

	public BaseScreen(FBLA2015 game) {
		this.game = game;
		
		stage = new Stage();
		camera = (OrthographicCamera) stage.getCamera();

		world = new World(Vector2.Zero, false);

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);

		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		world.step(1 / 60f, 5, 5);

		debugRenderer.render(world, stage.getCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
