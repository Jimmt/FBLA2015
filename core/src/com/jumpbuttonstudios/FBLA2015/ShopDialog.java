package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopDialog extends Dialog {
	ItemStatTable statTable;

	public ShopDialog(String title, Stage hudStage, Player player, Skin skin) {
		super(title, skin);

		setFillParent(true);
		Image panel = new Image(new Texture(Gdx.files.internal("ui/commbg.png")));
		setBackground(panel.getDrawable());

		statTable = new ItemStatTable(this, player.stats, player.gun, skin);

		getContentTable().add("Shop").colspan(2);
		getContentTable().row();
		Table itemsTable = new Table(skin);
		for (int i = 0; i < ItemStats.cache.length; i++) {
			final ItemPanel ip = new ItemPanel(ItemStats.cache[i], skin);
			ip.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					statTable.setItem(ip.getItemStats());
				}
			});
			itemsTable.add(ip).width(100).height(100).padRight(5f);
			if ((i + 1) % 3 == 0) {
				itemsTable.row();
			}
		}
		getContentTable().add(itemsTable);
		getContentTable().add(statTable).right().top().padLeft(15f);

	}

}
