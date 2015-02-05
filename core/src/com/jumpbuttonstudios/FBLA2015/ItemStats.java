package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum ItemStats {
	TUTORIAL_GUN("pistolIcon.png", "bullet.png", 2f, 5f, 5f), LEVEL2_GUN("pistolIcon.png",
			"bullet.png", 0.3f, 10f, 5f), LEVEL3_GUN("pistolIcon.png", "bullet.png", 1.5f, 12f, 6f), LEVEL3_GUN2(
			"pistolIcon.png", "bullet.png", 0.7f, 20f, 3f), PISTOL("pistolIcon.png", "bullet.png",
			0.2f, 10f, 7f), HORSE("rifleIcon.png", "level3/benign.png", 2f, 15f, 5f), LEVEL4_GUN(
			"pistolIcon.png", "bullet.png", 3f, 25f, 10f), LEVEL4_GUN2(
					"pistolIcon.png", "bullet.png", 2f, 20f, 7f), MACRO_CANNON(
					"pistolIcon.png", "level4/cannonshell.png", 4f, 50f, 2.5f);

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

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public String getBulletPath() {
		return bulletPath;
	}

	public String getPath() {
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
