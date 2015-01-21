package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelCompleteDialog extends Dialog {
	Label explanation;

	public LevelCompleteDialog(final FBLA2015 fbla, final GameScreen game, Skin skin) {
		super("", skin);

		game.pauseGame();

		Image panel = new Image(new Texture(Gdx.files.internal("ui/levelcompletebg.png")));
		background(panel.getDrawable());

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/button.png"))).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				fbla.setScreen(new LevelSelectScreen(fbla));

			}
		});

		LabelStyle lstyle = new LabelStyle();
		lstyle.font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		lstyle.fontColor = Color.WHITE;
		explanation = new Label(game.explanationText, lstyle);
		explanation.setWrap(true);

		getContentTable().add("Level Complete");
		getContentTable().row();
		getContentTable().add(explanation).fillX().center();
		getContentTable().row();
		getContentTable().add(back);
	}

}