package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class LevelSelectScreen extends BaseScreen {
	Array<ImageButton> buttons;
	Array<Label> labels;
	String[] levelNames = { "introduction", "worms", "horses", "macro", "honeypot" };
	ScrollPane scroller;
	Table buttonTable;
	CustomizationDialog customsDialog;
	UpgradeDialog upgradeDialog;
	CoinsBar coinsBar;

	public LevelSelectScreen(FBLA2015 game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		hudStage = new Stage(hudViewport);
		
		coinsBar = new CoinsBar(GamePrefs.prefs.getInteger("credits"), skin);
		buttons = new Array<ImageButton>();

		Image tablebg = new Image(Textures.getTex("mainmenu/menubg.png"));
		table.setBackground(tablebg.getDrawable());
		buttonTable = new Table(skin);
		labels = new Array<Label>();

		Image temp = new Image(Textures.getTex("ui/levelbgs/introductionbg.png"));
		for (int i = 0; i < levelNames.length; i++) {
			LabelStyle style = new LabelStyle();
			style.font = skin.getFont("default-font");
			style.fontColor = Color.WHITE;
			Label label = new Label(levelNames[i], style);
			label.setWrap(true);
			buttonTable.add(levelNames[i]);
			labels.add(label);
		}

		buttonTable.row();

		for (int i = 0; i < levelNames.length; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			Image bg = new Image(Textures.getTex("ui/levelbgs/" + levelNames[i] + "bg.png"));
			style.up = bg.getDrawable();
			ImageButton button = new ImageButton(style);

			final int index = i;
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameScreen screen = null;
					FBLA2015.soundManager.play("button", 0.5f);

					if (index == 0) {
						game.setScreen(new StoryScreen(game));
					}
					if (index == 1) {
						screen = new WormLevel(game);
					}
					if (index == 2) {
						screen = new HorseLevel(game);
					}
					if (index == 3) {
						screen = new MacroLevel(game);
					}
					if (index == 4) {
						screen = new HoneypotLevel(game);
					}
					if (screen != null) {
						game.setScreen(screen);
					}

				}
			});
			buttons.add(button);
			buttonTable.add(button).padLeft(15f).padBottom(30f);
		}

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		customsDialog = new CustomizationDialog(skin, game);

		TextImageButton customize = new TextImageButton("Customize", skin.getFont("default-font"),
				style);
		customize.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				customsDialog.show(dialogStage);
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		upgradeDialog = new UpgradeDialog(skin, this);

		TextImageButton upgrade = new TextImageButton("Upgrade", skin.getFont("default-font"),
				style);
		upgrade.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				upgradeDialog.show(dialogStage);
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.hScroll = new Image(Textures.getTex("ui/scrollbar/scrollbar.png"))
				.getDrawable();
		scrollStyle.hScrollKnob = new Image(Textures.getTex("ui/scrollbar/scrollknob.png"))
				.getDrawable();
		scroller = new ScrollPane(buttonTable, scrollStyle);
		scroller.setFadeScrollBars(false);
		scroller.setScrollingDisabled(false, true);
		table.add("Level Select").padBottom(100f).colspan(3);
		table.row();
		table.add(scroller).colspan(3);
		table.row();
		table.add(customize).width(customize.textLabel.getWidth() + 20).padTop(25f);
		table.add(coinsBar).padTop(25f);
		table.add(upgrade).width(customize.textLabel.getWidth() + 20).padTop(25f);
		table.row();
		table.add(back).width(customize.textLabel.getWidth() + 20).padTop(25f).colspan(3);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		hudStage.act(delta);
		hudStage.draw();
		
		

	}

}
