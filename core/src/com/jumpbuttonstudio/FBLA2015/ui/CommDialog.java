package com.jumpbuttonstudio.FBLA2015.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jumpbuttonstudio.FBLA2015.util.Textures;

/**
 * HUD component for comms
 */
public class CommDialog extends Dialog {
	Label text;

	public CommDialog(Skin skin) {
		super("", skin);

		Image panel = new Image(Textures.getTex("ui/comm/commbg.png"));
		setBackground(panel.getDrawable());
		
		Image subject = new Image(Textures.getTex("ui/comm/antivirusicon.png"));
		
		LabelStyle labelStyle = new LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		labelStyle.font = font;
		text = new Label("", labelStyle);
		text.setWrap(true);

		getContentTable().add(subject).padLeft(7f).padRight(7f);
		getContentTable().add(text).width(400).height(200);
		getContentTable().row();
	
	}
	
	public void setText(String content){
		text.setText(content);
	}
}
