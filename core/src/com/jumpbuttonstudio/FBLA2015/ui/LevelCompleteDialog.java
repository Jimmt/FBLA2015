package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.screens.LevelSelectScreen;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.Fonts;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.util.Textures;

/**
 * Level complete dialog, sends user back to level select screen.
 */
public class LevelCompleteDialog extends Dialog {
	Label explanation;

	public LevelCompleteDialog(final FBLA2015 fbla, final GameScreen game, Skin skin) {
		super("", skin);

		game.pauseGame();

		Image panel = new Image(Textures.getTex("ui/levelcompletebg.png"));
		background(panel.getDrawable());
		setSize(Constants.WIDTH - 100, Constants.HEIGHT - 100);

		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(Textures.getTex("ui/button.png")).getDrawable();
		TextImageButton back = new TextImageButton("Back", skin.getFont("default-font"), style);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GamePrefs.putInteger("credits", GamePrefs.prefs.getInteger("credits") + game.byteCoins);
				fbla.setScreen(new LevelSelectScreen(fbla));

			}
		});

		LabelStyle lstyle = new LabelStyle();
		BitmapFont font = Fonts.getFont("skin/geosanslight_white.fnt");
		lstyle.font = font;
		lstyle.fontColor = Color.WHITE;
		explanation = new Label(game.explanationText, lstyle);
		explanation.setWrap(true);
		explanation.setAlignment(Align.center);

		getContentTable().add("Level Complete").expandX().padBottom(15f);
		getContentTable().row();
		getContentTable().add(explanation).fillX().center();
		getContentTable().row();
		getContentTable().add(back).expandX().padTop(15f);

	}

}
