package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameOverDialog extends Dialog {
	
	public GameOverDialog(Skin skin){
		super("", skin);
		
		Image panel = new Image(new Texture(Gdx.files.internal("ui/commbg.png")));
		setBackground(panel.getDrawable());
		
		getContentTable().add("GAME OVER");
	}
}
