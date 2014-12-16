package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum ItemStats {
	PISTOL("pistolIcon.png", 0.2f, 10f), RIFLE("rifleIcon.png", 0.1f, 15f);

	private float rof, damage;
	private Image icon;
	public static ItemStats[] cache;
	private String path;

	ItemStats(String path, float rof, float damage) {
		this.path = path;
		this.rof = rof;
		this.damage = damage;
		icon = new Image(new Texture(Gdx.files.internal(path)));
	}

	public static void makeCache() {
		cache = ItemStats.values();
	}
	
	public String getPath(){
		return path;
	}

	public float getROF() {
		return rof;
	}

	public float getDamage() {
		return damage;
	}

	public Image getIcon() {
		return icon;
	}
}
