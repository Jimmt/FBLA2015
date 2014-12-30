package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class LevelSelectScreen extends BaseScreen {
	Array<ImageButton> buttons;
	Array<Label> labels;
	String[] levelNames = { "tutorial", "level2", "level3", "level4", "level5" };
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

		buttons = new Array<ImageButton>();

		Image tablebg = new Image(new Texture(Gdx.files.internal("ui/levelselectbg.png")));
		table.setBackground(tablebg.getDrawable());
		
		
		buttonTable = new Table(skin);
		
		labels = new Array<Label>();
		
		Image temp = new Image(new Texture(Gdx.files.internal("ui/tutorialbg.png")));
		for(int i = 0; i < num; i++){
			LabelStyle style = new LabelStyle();
			style.font = skin.getFont("default-font");
			style.fontColor = Color.WHITE;
			Label label = new Label(levelNames[i], style);
			label.setWrap(true);
			buttonTable.add(label).width(temp.getWidth());
			labels.add(label);
		}
		
		buttonTable.row();
		
		for (int i = 0; i < num; i++) {
			ImageButtonStyle style = new ImageButtonStyle();
			Image bg = new Image(new Texture(Gdx.files.internal("ui/" + levelNames[i] + "bg.png")));
			style.up = bg.getDrawable();
			ImageButton button = new ImageButton(style);

			final int index = i;
			button.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(new GameScreen(game, levelNames[index]));

				}
			});
			buttons.add(button);
			buttonTable.add(button);
		}
		
		scroller = new ScrollPane(buttonTable, skin);
//		scroller.setFadeScrollBars(false);
		scroller.setScrollingDisabled(false, true);
		table.add("Level Select").padBottom(100f);
		table.row();
		table.add(scroller);

	}
	
	@Override
	public void render(float delta){
		super.render(delta);
	}
	
	

}
