package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelCompleteDialog extends Dialog {

	public LevelCompleteDialog(final FBLA2015 fbla, final GameScreen game, Skin skin) {
		super("", skin);

		game.pauseGame();

		Image panel = new Image(new Texture(Gdx.files.internal("ui/commbg.png")));
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
		
		getContentTable().add("Level Complete");
		getContentTable().row();
		getContentTable().add(back);
	}

}
