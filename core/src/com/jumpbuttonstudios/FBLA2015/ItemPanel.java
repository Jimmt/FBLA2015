package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Scaling;

public class ItemPanel extends ImageButton {
	ItemStats is;

	public ItemPanel(ItemStats is, Skin skin) {
		super(new ImageButtonStyle());
		ImageButtonStyle style = new ImageButtonStyle();
		style.up = new Image(new Texture(Gdx.files.internal("ui/itembg.png"))).getDrawable();
		setStyle(style);
		this.is = is;
		is.getIcon().setPosition(getX() + getWidth() / 2 - is.getIcon().getWidth() / 2, getY() + getHeight() / 2
				- is.getIcon().getHeight() / 2);
		
		is.getIcon().setScaling(Scaling.fit);
		if(is.getIcon().getWidth() > 100){
			is.getIcon().setWidth(100);
		}
		if(is.getIcon().getHeight() > 100){
			is.getIcon().setHeight(100);
		}
		

	}
	
	public ItemStats getItemStats(){
		return is;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		is.getIcon().setPosition(getX() + getWidth() / 2 - is.getIcon().getWidth() / 2, getY() + getHeight() / 2
				- is.getIcon().getHeight() / 2);
		is.getIcon().draw(batch, parentAlpha);
	}
}
