package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class MenuScreen extends BaseScreen {
	TextImageButton start;
	Label title;
	Array<LiveTile> tiles = new Array<LiveTile>();
	float lastAdd = 0, addCap = 0.6f;
	Texture tex = new Texture(Gdx.files.internal("mainmenu/livetile.png"));
	Image circle;
	Image[] arcs = new Image[4];

	public MenuScreen(FBLA2015 game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		if (!FBLA2015.DEBUG) {
			FBLA2015.soundManager.playMusic("menu", 0.09f);
		}

		Prefs.load();

		Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				Prefs.prefs.getBoolean("fullscreen"));
		
		FBLA2015.soundManager.setPlay(Prefs.prefs.getBoolean("sound"));

		table.setFillParent(true);
		title = new Label("Infection", new LabelStyle(skin.getFont("default-font"), Color.WHITE));
		table.add(title).padBottom(10f);
		table.row();

		Image background = new Image(new Texture(Gdx.files.internal("mainmenu/menubg.png")));
		background.setSize(Constants.WIDTH, Constants.HEIGHT);
		Texture circTex = new Texture(Gdx.files.internal("mainmenu/circle.png"));
		circTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		circle = new Image(circTex);

		stage.addActor(background);
		for (int i = 0; i < 4; i++) {
			Image arc = new Image(new Texture(Gdx.files.internal("mainmenu/arc.png")));
			stage.addActor(arc);
			arcs[i] = arc;
			arc.setPosition(Constants.WIDTH / 2, Constants.HEIGHT / 2);
			arc.setOrigin(0, 0);
		}

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		start = new TextImageButton("Start", skin.getFont("default-font"), style);

		TextImageButton credits = new TextImageButton("Credits", skin.getFont("default-font"),
				style);

		TextImageButton controls = new TextImageButton("Controls", skin.getFont("default-font"),
				style);

		TextImageButton options = new TextImageButton("Options", skin.getFont("default-font"),
				style);

		table.add(start).padBottom(5f).width(controls.textLabel.getWidth());
		table.row();
		start.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelSelectScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		table.add(credits).padBottom(5f).width(controls.textLabel.getWidth());
		credits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new CreditsScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		table.row();
		table.add(controls).width(controls.textLabel.getWidth()).padBottom(5f);
		controls.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new ControlsScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		table.row();
		table.add(options).width(controls.textLabel.getWidth());
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new OptionsScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		circle.setOrigin(circle.getWidth() / 2, circle.getHeight() / 2);

		arcs[1].rotateBy(90);
		arcs[2].rotateBy(180);
		arcs[3].rotateBy(270);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		table.toFront();

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
