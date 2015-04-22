package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UpgradeDialog extends Dialog {
	String[] names = { "Damage", "Health", "Speed" };
	RejectDialog rejectDialog;
	int[] increments = { 1, 25, 1 };
	int[] costs = { 1, 10, 8 };
	CoinsBar coinsBar;

	public UpgradeDialog(Skin skin, final LevelSelectScreen screen) {
		super("", skin);

		rejectDialog = new RejectDialog(skin);
		coinsBar = new CoinsBar(GamePrefs.prefs.getInteger("credits"), skin);
		Image panel = new Image(Textures.getTex("ui/customsbg.png"));
		background(panel.getDrawable());

		LabelStyle style = new LabelStyle();
		style.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		LabelStyle colorStyle = new LabelStyle();
		colorStyle.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		colorStyle.fontColor = new Color(0, 1.0f, 0, 1.0f);

		getContentTable().add("Upgrades").colspan(6).padBottom(40f).row();
		getContentTable().add(coinsBar).colspan(6).padBottom(20f).row();

		for (int i = 0; i < 3; i++) {
			Image icon = new Image(Textures.getTex("ships/" + names[i].toLowerCase() + "Icon.png"));
			getContentTable().add(icon);
			Label label = new Label(names[i], style);

			Label baseStat = null;
			switch (i) {
			case 0:
				baseStat = new Label(String.valueOf(PlayerProfile.model.stats.getDamage()), style);
				break;
			case 1:
				baseStat = new Label(String.valueOf(PlayerProfile.model.getHealth()), style);
				break;
			case 2:
				baseStat = new Label(String.valueOf(PlayerProfile.model.stats.getMoveSpeed()),
						style);
				break;
			}
			final Label bonusStat = new Label(" + " + GamePrefs.prefs.getInteger("bonus" + names[i]),
					colorStyle);
			Table statTable = new Table();
			statTable.add(baseStat);
			statTable.add(bonusStat);
			Image plus = new Image(Textures.getTex("ui/plus.png"));
			final int index = i;
			plus.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (GamePrefs.prefs.getInteger("credits") >= costs[index]) {
						GamePrefs.putInteger("credits", GamePrefs.prefs.getInteger("credits")
								- costs[index]);
						coinsBar.amount = GamePrefs.prefs.getInteger("credits");
						GamePrefs.putInteger("bonus" + names[index],
								GamePrefs.prefs.getInteger("bonus" + names[index])
										+ increments[index]);

						bonusStat.setText(" + " + GamePrefs.prefs.getInteger("bonus" + names[index]));
					} else {
						rejectDialog.label.setText("Not enough bytecoins");
						rejectDialog.show(getStage());
					}
					FBLA2015.soundManager.play("button", 0.5f);
				}
			});
			Image minus = new Image(Textures.getTex("ui/minus.png"));
			minus.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (GamePrefs.prefs.getInteger("bonus" + names[index]) > 0) {
						GamePrefs.putInteger("credits", GamePrefs.prefs.getInteger("credits")
								+ costs[index]);
						coinsBar.amount = GamePrefs.prefs.getInteger("credits");
						GamePrefs.putInteger("bonus" + names[index],
								GamePrefs.prefs.getInteger("bonus" + names[index])
										- increments[index]);

						bonusStat.setText(" + " + GamePrefs.prefs.getInteger("bonus" + names[index]));
					} else {
						rejectDialog.label.setText("Nothing to refund");
						rejectDialog.show(getStage());
					}
					FBLA2015.soundManager.play("button", 0.5f);
				}
			});
			Table costTable = new Table();
			Image coin = new Image(Textures.getTex("ui/coinicon.png"));
			Label costLabel = new Label(String.valueOf(costs[i]), style);
			costTable.add(coin);
			costTable.add(costLabel);

			getContentTable().add(label).padLeft(10f);
			getContentTable().add(statTable).padLeft(20f).padRight(20f);
			getContentTable().add(minus);
			getContentTable().add(plus).padRight(20f);
			getContentTable().add(costTable);
			getContentTable().row().padTop(20f);
		}

		ImageButtonStyle istyle = new ImageButtonStyle();
		istyle.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton ok = new TextImageButton("OK", skin.getFont("default-font"), istyle);
		ok.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
				FBLA2015.soundManager.play("button", 0.5f);
				screen.coinsBar.amount = GamePrefs.prefs.getInteger("credits");
			}
		});
		getContentTable().add(ok).colspan(6).padTop(40f);

	}

	class RejectDialog extends Dialog {
		String displayText = "Not enough bytecoins";
		Label label;

		public RejectDialog(Skin skin) {
			super("", skin);

			Image panel = new Image(Textures.getTex("ui/customsbg.png"));
			panel.setSize(panel.getWidth() / 2, panel.getHeight() / 2);
			background(panel.getDrawable());

			ImageButtonStyle style = new ImageButtonStyle();
			style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
			TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
			back.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					hide();
					FBLA2015.soundManager.play("button", 0.5f);
				}
			});

			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
			label = new Label(displayText, labelStyle);
			label.setAlignment(Align.center);
			getContentTable().add(label).padBottom(20f).row();
			getContentTable().add(back);
		}

	}

}
