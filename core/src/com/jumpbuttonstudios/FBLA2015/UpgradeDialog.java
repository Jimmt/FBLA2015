package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UpgradeDialog extends Dialog {

	public UpgradeDialog(Skin skin) {
		super("", skin);
		
		Image panel = new Image(Textures.getTex("ui/customsbg.png"));
		background(panel.getDrawable());
		
		
	}

}
