package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ItemStatTable extends Table {
	Image item;
	ItemStats stats;
	Label damage, rof;
	

	public ItemStatTable(final ShopDialog dialog, final ItemStats is, final Gun gun, Skin skin) {
		super(skin);
		this.stats = is;
		item = new Image(stats.getIcon().getDrawable());
		add(item);
		row();
		LabelStyle style = new LabelStyle();
		BitmapFont font = new BitmapFont(Gdx.files.internal("skin/orbitron.fnt"));
		style.font = font;
		style.fontColor = Color.WHITE;
		damage = new Label("Damage: " + stats.getDamage(), style);
		add(damage).left();
		row();
		rof = new Label("Rate Of Fire: " + stats.getROF(), style);
		add(rof).left();

		ImageButtonStyle ibstyle = new ImageButtonStyle();
		ibstyle.up = new Image(new Texture(Gdx.files.internal("ui/buybutton.png"))).getDrawable();
		TextImageButton buy = new TextImageButton("BUY", font, ibstyle);
		row();
		add(buy).width(150).height(50).left().padTop(5);
		buy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gun.stats = stats;
				dialog.setVisible(false);
			}
		});

	}

	public void setItem(ItemStats is) {
		this.stats = is;
	
		item.setDrawable(is.getIcon().getDrawable());
		damage.setText("Damage: " + is.getDamage());
		rof.setText("Rate of Fire: " + is.getROF());
		
	}
}
