package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum ItemStats {
	TUTORIAL_GUN("pistolIcon.png", "bullet.png", 0.5f, 5f, 5f), LEVEL2_GUN("pistolIcon.png", "bullet.png", 0.3f, 10f, 5f), PISTOL("pistolIcon.png", "bullet.png", 0.2f, 10f, 7f), RIFLE("rifleIcon.png", "bullet.png", 0.1f, 15f, 15f);

	private float rof, damage, bulletSpeed;
	private Image icon;
	public static ItemStats[] cache;
	private String path, bulletPath;

	ItemStats(String path, String bulletPath, float rof, float damage, float bulletSpeed) {
		this.path = path;
		this.rof = rof;
		this.bulletSpeed = bulletSpeed;
		this.bulletPath = bulletPath;
		this.damage = damage;
		icon = new Image(new Texture(Gdx.files.internal(path)));
	}

	public static void makeCache() {
		cache = ItemStats.values();
	}
	
	public float getBulletSpeed(){
		return bulletSpeed;
	}
	
	public String getBulletPath(){
		return bulletPath;
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
