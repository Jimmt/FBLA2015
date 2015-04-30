package com.jumpbuttonstudio.FBLA2015.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.ui.LiveTile;
import com.jumpbuttonstudio.FBLA2015.ui.TextImageButton;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;

public class MenuScreen extends BaseScreen {
	TextImageButton start;
	Label title;
	Array<LiveTile> tiles = new Array<LiveTile>();
	float lastAdd = 0, addCap = 0.6f;
	Texture tex = Textures.getTex("mainmenu/livetile.png");
	Image circle;
	Image[] arcs = new Image[4];
	Image exit = new Image(Textures.getTex("ui/exit.png"));

	public MenuScreen(FBLA2015 game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		if (!FBLA2015.DEBUG) {
			FBLA2015.soundManager.playMusic("menu", 0.09f);
		}


		Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				GamePrefs.prefs.getBoolean("fullscreen"));
		
		FBLA2015.soundManager.setPlay(GamePrefs.prefs.getBoolean("sound"));

		table.setFillParent(true);
		title = new Label("Infection", new LabelStyle(skin.getFont("default-font"), Color.WHITE));
		table.add(title).padBottom(10f);
		table.row();

		Image background = new Image(Textures.getTex("mainmenu/menubg.png"));
		background.setSize(Constants.WIDTH, Constants.HEIGHT);
		Texture circTex = Textures.getTex("mainmenu/circle.png");
		circTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		circle = new Image(circTex);

		stage.addActor(background);
		for (int i = 0; i < 4; i++) {
			Image arc = new Image(Textures.getTex("mainmenu/arc.png"));
			stage.addActor(arc);
			arcs[i] = arc;
			arc.setPosition(Constants.WIDTH / 2, Constants.HEIGHT / 2);
			arc.setOrigin(0, 0);
		}

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		start = new TextImageButton("Start", skin.getFont("default-font"), style);

		TextImageButton credits = new TextImageButton("Credits", skin.getFont("default-font"),
				style);

		TextImageButton controls = new TextImageButton("Controls", skin.getFont("default-font"),
				style);

		TextImageButton options = new TextImageButton("Options", skin.getFont("default-font"),
				style);

		table.add(start).padBottom(5f).width(controls.textLabel.getWidth() + 10);
		table.row();
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelSelectScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		table.add(credits).padBottom(5f).width(controls.textLabel.getWidth() + 10);
		credits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new CreditsScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		table.row();
		table.add(controls).width(controls.textLabel.getWidth() + 10).padBottom(5f);
		controls.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FBLA2015.soundManager.play("button", 0.5f);
				game.setScreen(new ControlsScreen(game));
			}
		});

		table.row();
		table.add(options).width(controls.textLabel.getWidth() + 10);
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FBLA2015.soundManager.play("button", 0.5f);
				game.setScreen(new OptionsScreen(game));
			}
		});

		circle.setOrigin(circle.getWidth() / 2, circle.getHeight() / 2);

		arcs[1].rotateBy(90);
		arcs[2].rotateBy(180);
		arcs[3].rotateBy(270);

		hudStage = new Stage(hudViewport);

		InputMultiplexer multiplexer = new InputMultiplexer(dialogStage, hudStage, stage);
		Gdx.input.setInputProcessor(multiplexer);

		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FBLA2015.soundManager.play("button", 0.5f);
				dialog.show(dialogStage);
			}
		});

		hudStage.addActor(exit);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		table.toFront();

		hudStage.act(delta);
		exit.setPosition(Constants.WIDTH - exit.getWidth() - 50, Constants.HEIGHT - exit.getHeight() - 50);
		hudStage.draw();

		circle.rotateBy(delta * 12f);

		for (int i = 0; i < arcs.length; i++) {
			if (arcs[i].getActions().size == 0) {
				arcs[i].addAction(Actions.sequence(Actions.delay(2f),
						Actions.moveBy(100, 100, 1.0f), Actions.delay(2f),
						Actions.moveBy(-100, -100, 1.0f), Actions.rotateBy(360, 1.3f),
						Actions.delay(1.5f), Actions.moveBy(i % 2 * 100, i % 2 * 100, 1.5f),
						Actions.rotateBy(i % 2 * 90, 0.5f), Actions.delay(2f),
						Actions.moveBy(i % 2 * -100, i % 2 * -100, 1f),
						Actions.rotateBy(i % 2 * 270, 1f),
						Actions.alpha(0, 1f, Interpolation.bounce), Actions.delay(1f),
						Actions.alpha(1f, 1f, Interpolation.exp5In),
						Actions.moveBy(200, 200, i + 1, Interpolation.exp5In),
						Actions.moveBy(-150, -150, 4 - i, Interpolation.exp5Out),
						Actions.delay(0.5f), Actions.moveBy(-50, -50, 1.0f, Interpolation.pow2In)));
			}

			arcs[i].rotateBy(delta * -30f);
			arcs[i].setOrigin(Constants.WIDTH / 2 - arcs[i].getX(),
					Constants.HEIGHT / 2 - arcs[i].getY());
		}

		if (lastAdd > addCap) {
			lastAdd = 0;
			LiveTile image = new LiveTile(tex, MathUtils.random(Constants.WIDTH),
					MathUtils.random(Constants.HEIGHT), 0, 0);
			tiles.add(image);
			stage.addActor(image);

		} else {
			lastAdd += delta;
		}

		for (int i = 0; i < tiles.size; i++) {
			if (tiles.get(i).horizontal) {
				if ((tiles.get(i).direction.x < 0 && tiles.get(i).getX() + tiles.get(i).getWidth() < 0)
						|| (tiles.get(i).direction.x > 0 && tiles.get(i).getX() > Constants.WIDTH)) {
					stage.getActors().removeValue(tiles.get(i), false);
					tiles.removeIndex(i);
					break;
				}
			} else {
				if ((tiles.get(i).direction.y < 0 && tiles.get(i).getY() + tiles.get(i).getHeight() < 0)
						|| (tiles.get(i).direction.y > 0 && tiles.get(i).getY() > Constants.HEIGHT)) {
					stage.getActors().removeValue(tiles.get(i), false);
					tiles.removeIndex(i);
					break;
				}
			}
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
