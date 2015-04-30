package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.levels.IntroductionLevel;
import com.jumpbuttonstudio.FBLA2015.screens.StoryScreen;
import com.jumpbuttonstudio.FBLA2015.sprite.PlayerProfile;
import com.jumpbuttonstudio.FBLA2015.sprite.PlayerProfile.ShipModel;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.util.ParticleEffectActor;
import com.jumpbuttonstudio.FBLA2015.weapon.Textures;
import com.jumpbuttonstudios.FBLA2015.FBLA2015;

public class CustomizationDialog extends Dialog {
	Array<Group> groups = new Array<Group>();
	Array<Group> groups2 = new Array<Group>();
	Array<ParticleEffectActor> actors = new Array<ParticleEffectActor>();
	Array<Image> ships = new Array<Image>();
	Image themeBorder = new Image(Textures.getTex("ui/selectionborder.png")),
			classBorder = new Image(Textures.getTex("ui/selectionborder.png"));
	int themeIndex = GamePrefs.prefs.getInteger("streak"), classIndex = GamePrefs.prefs.getInteger("ship");
	float fadeTime = 0.3f;
	Table shipsTable;
	Sprite sprite;
	LabelStyle labelStyle;
	BitmapFont geosans, orbitron;

	public CustomizationDialog(Skin skin, final FBLA2015 game) {
		super("", skin);

		Image background = new Image(Textures.getTex("ui/customsbg.png"));
		background(background.getDrawable());

		geosans = new BitmapFont(Gdx.files.internal("skin/geosanslight_white.fnt"));
		orbitron = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton ok = new TextImageButton("Ok", skin.getFont("default-font"), style);
		ok.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getScreen() instanceof StoryScreen) {
					game.setScreen(new IntroductionLevel(game));
				} else {
					hide();
				}
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		getContentTable().add("Customize").colspan(6).padBottom(20f).row();

		labelStyle = new LabelStyle();
		labelStyle.font = orbitron;
		Label theme = new Label("Theme", labelStyle);
		getContentTable().add(theme).colspan(6).padBottom(20f).row();
		labelStyle.font = geosans;
		labelStyle.fontColor = Color.WHITE;

		setupThemes();

		for (int i = 0; i < PlayerProfile.streaks.length; i++) {
			Label description = new Label(PlayerProfile.streaks[i], labelStyle);
			getContentTable().add(description).padBottom(20f);
		}
		getContentTable().row();

		labelStyle.font = orbitron;
		Label shipClass = new Label("Class", labelStyle);
		getContentTable().add(shipClass).colspan(6).padBottom(10f).row();

		setupClasses();
		getContentTable().row();

		getContentTable().add(ok).width(ok.getWidth() / 3 * 2).height(ok.getHeight() / 3 * 2)
				.colspan(6).padTop(25f);
	}

	public void setupClasses() {
		shipsTable = new Table();

		for (int i = 0; i < PlayerProfile.ships.length; i++) {
			Group previewGroup = new Group();
			Image ship = new Image(Textures.getTex("ships/" + PlayerProfile.ships[i] + ".png"));
			Image black = new Image(Textures.getTex("ui/black.png"));
			black.setSize(80, 80);
			previewGroup.addActor(black);
			ship.setPosition(black.getX() + black.getWidth() / 2 - ship.getWidth() / 2,
					black.getY() + black.getHeight() / 2 - ship.getHeight() / 2);
			previewGroup.addActor(ship);
			shipsTable.add(previewGroup).width(80).height(80);

			final int index = i;

			previewGroup.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					FBLA2015.soundManager.play("button", 0.5f);
					PlayerProfile.ship = "ships/" + PlayerProfile.ships[index] + ".png";
					classIndex = index;
					Texture tex = Textures.getTex(PlayerProfile.ship);
					sprite = new Sprite(tex);
					Image image = new Image(tex);
					for (int i = 0; i < actors.size; i++) {
						actors.get(i).effect.getEmitters().get(0).setSprite(sprite);
					}
					for (int i = 0; i < ships.size; i++) {
						ships.get(i).setDrawable(image.getDrawable());
					}
					GamePrefs.putInteger("ship", index);
					PlayerProfile.updateModel();
				}
			});
			groups2.add(previewGroup);
		}
		shipsTable.row();
		labelStyle.font = geosans;

		for (int i = 0; i < PlayerProfile.ships.length; i++) {
			Label description = new Label(PlayerProfile.ships[i], labelStyle);
			shipsTable.add(description).padLeft(10f).padRight(10f).padBottom(20f);
		}
		shipsTable.row();

		PlayerProfile.ShipModel[] models = PlayerProfile.ShipModel.values();

		for (int i = 0; i < 3; i++) {
			Table subtable = new Table();
			Image image = new Image(Textures.getTex("ships/damageIcon.png"));
			Label damage = new Label(String.valueOf((int) models[i].getStats().getDamage()), labelStyle);
			subtable.add(image).padRight(5f).padBottom(2f);
			subtable.add(damage).padRight(5f).padBottom(2f).row();
			Image image1 = new Image(Textures.getTex("ships/healthIcon.png"));
			Label health = new Label(String.valueOf((int) models[i].getHealth()), labelStyle);
			subtable.add(image1).padRight(5f).padBottom(2f);
			subtable.add(health).padRight(5f).padBottom(2f).row();
			Image image2 = new Image(Textures.getTex("ships/speedIcon.png"));
			Label speed = new Label(String.valueOf((int) (models[i].getStats().getMoveSpeed() * 10)),
					labelStyle);
			subtable.add(image2).padRight(5f).padBottom(2f);
			subtable.add(speed).padRight(5f).padBottom(2f);
			shipsTable.add(subtable).padBottom(20f);
		}

		getContentTable().add(shipsTable).colspan(6);
	}

	public void setupThemes() {
		int streaks = PlayerProfile.streaks.length;
		for (int i = 0; i < streaks; i++) {
			Group previewGroup = new Group();
			final ParticleEffectActor actor = new ParticleEffectActor("effects/streaks/"
					+ PlayerProfile.streaks[i] + ".p", "ships/", false);
			Image ship = new Image(Textures.getTex(PlayerProfile.ship));
			ships.add(ship);
			Image black = new Image(Textures.getTex("ui/black.png"));
			black.setSize(80, 80);

			previewGroup.addActor(black);
			previewGroup.addActor(ship);
			ship.setPosition(black.getX() + black.getWidth() / 2 - ship.getWidth() / 2,
					black.getY() + black.getHeight() / 3 * 2 - ship.getHeight() / 2);
			previewGroup.addActor(actor);
			actors.add(actor);
			actor.setPosition(black.getX() + black.getWidth() / 2 - 16,
					black.getY() + black.getHeight() / 3 * 2 - 16);
			getContentTable().add(previewGroup).width(80).height(80);

			final int index = i;

			previewGroup.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					PlayerProfile.streak = "effects/streaks/" + PlayerProfile.streaks[index] + ".p";
					themeIndex = index;
					GamePrefs.putInteger("streak", index);
					FBLA2015.soundManager.play("button", 0.5f);
				}
			});
			groups.add(previewGroup);
		}
		getContentTable().row();
	}

	@Override
	public Dialog show(Stage stage) {
		super.show(stage);

		themeBorder.addAction(Actions.fadeIn(fadeTime));
		classBorder.addAction(Actions.fadeIn(fadeTime));
		return this;
	}

	@Override
	public void hide() {
		super.hide();

		themeBorder.addAction(Actions.fadeOut(fadeTime));
		classBorder.addAction(Actions.fadeOut(fadeTime));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		themeBorder.act(delta);
		classBorder.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		themeBorder.setPosition(getX() + groups.get(themeIndex).getX() - 3,
				getY() + groups.get(themeIndex).getY() + 3);
		classBorder.setPosition(getX() + shipsTable.getX() + groups2.get(classIndex).getX() - 3,
				getY() + shipsTable.getY() + groups2.get(classIndex).getY() + 3);

		themeBorder.draw(batch, parentAlpha);
		classBorder.draw(batch, parentAlpha);

	}

}
