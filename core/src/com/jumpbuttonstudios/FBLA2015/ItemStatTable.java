package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ItemStatTable extends Table {
	Image item;

	public ItemStatTable(Skin skin) {
		super(skin);
		item = new Image();
		add(item);
	}
	
	public void setItem(Image image){
		item.setDrawable(image.getDrawable());
	}
}
