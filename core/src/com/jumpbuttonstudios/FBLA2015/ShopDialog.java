package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ShopDialog extends Dialog {	
	ItemStatTable statTable;

	public ShopDialog(String title, Skin skin) {
		super(title, skin);
		
		Image panel = new Image(new Texture(Gdx.files.internal("ui/commbg.png")));
		setBackground(panel.getDrawable());
		
		statTable = new ItemStatTable(skin);
		
		getContentTable().add("Shop").expandX().center();
		ItemPanel ip = new ItemPanel(Gun.icon, skin);
		getContentTable().row();
		getContentTable().add(ip).width(100).height(100);
		getContentTable().add(statTable).expandX().right();
		
		statTable.setItem(ip.item);
		
	}

}
