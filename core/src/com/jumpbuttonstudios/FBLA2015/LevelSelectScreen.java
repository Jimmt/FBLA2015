package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	String[] levelNames = { "tutorial", "worms", "horses", "macro", "honeypot" };
	ScrollPane scroller;
	Table buttonTable;

	public LevelSelectScreen(FBLA2015 game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();

		FileHandle levelFile = Gdx.files.internal("levels.txt");

		int num = Integer.valueOf(levelFile.readString());
		GamePrefs.initialize(num);

		buttons = new Array<ImageButton>();

		Image tablebg = new Image(new Texture(Gdx.files.internal("mainmenu/menubg.png")));
		table.setBackground(tablebg.getDrawable());

		buttonTable = new Table(skin);

		labels = new Array<Label>();

		Image temp = new Image(new Texture(Gdx.files.internal("ui/levelbgs/tutorialbg.png")));
		for (int i = 0; i < num; i++) {
			LabelStyle style = new LabelStyle();
			style.font = skin.getFont("default-font");
			style.fontColor = Color.WHITE;
			Label label = new Label(levelNames[i], style);
			label.setWrap(true);
			buttonTable.add(levelNames[i]);
			labels.add(label);
		}

		buttonTable.row();

		for (int i = 0; i < num; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			Image bg = new Image(new Texture(Gdx.files.internal("ui/levelbgs/" + levelNames[i] + "bg.png")));
			style.up = bg.getDrawable();
			ImageButton button = new ImageButton(style);

			final int index = i;
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					GameScreen screen = null;

					if (index == 0) {
						screen = new TutorialLevel(game);
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
					game.setScreen(screen);
					FBLA2015.soundManager.play("button", 0.5f);
				}
			});
			buttons.add(button);
			buttonTable.add(button).padLeft(15f).padBottom(30f);
		}

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				FBLA2015.soundManager.play("button", 0.5f);
			}
		});

		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.hScroll = new Image(new Texture(Gdx.files.internal("ui/scrollbar/scrollbar.png")))
				.getDrawable();
		scrollStyle.hScrollKnob = new Image(new Texture(Gdx.files.internal("ui/scrollbar/scrollknob.png")))
				.getDrawable();
		scroller = new ScrollPane(buttonTable, scrollStyle);
		scroller.setFadeScrollBars(false);
		scroller.setScrollingDisabled(false, true);
		table.add("Level Select").padBottom(100f);
		table.row();
		table.add(scroller);
		table.row();
		table.add(back).padTop(100f);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

	}

}
