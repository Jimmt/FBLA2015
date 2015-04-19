package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum ItemStats {
	LIGHT("bullet.png", 0.3f, 6f, 7f, 2.5f), MEDIUM("bullet.png", 0.3f, 8f, 7f, 2f), HEAVY(
			"bullet.png", 0.3f, 10f, 7f, 1.5f), TUTORIAL_GUN("bullet.png", 2.5f, 5f, 5f, 0.75f), LEVEL2_GUN(
			"bullet.png", 0.7f, 5f, 5f, 0.75f), LEVEL3_GUN("bullet.png", 1.5f, 12f, 6f, 0.75f), LEVEL3_GUN2(
			"bullet.png", 0.7f, 20f, 3f, 0.75f), HORSE("level3/benign.png", 2f, 15f, 5f, 0.75f), LEVEL4_GUN(
			"bullet.png", 3f, 20f, 6f, 0.75f), LEVEL4_GUN2("bullet.png", 2f, 15f, 4f, 0.75f), MACRO_CANNON(
			"level4/cannonshell.png", 4f, 50f, 4f, 0.75f), LEVEL5_GUN("bullet.png", 2f, 10f, 7f,
			1.25f);

	private float rof, damage, bulletSpeed, moveSpeed;
	public static ItemStats[] cache;
	private String bulletPath;

	ItemStats(String bulletPath, float rof, float damage, float bulletSpeed, float moveSpeed) {
		this.rof = rof;
		this.bulletSpeed = bulletSpeed;
		this.bulletPath = bulletPath;
		this.damage = damage;
		this.moveSpeed = moveSpeed;
	}

	public static void makeCache() {
		cache = ItemStats.values();
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public float getBulletSpeed() {
		return bulletSpeed;
	}

	public String getBulletPath() {
		return bulletPath;
	}

	public float getROF() {
		return rof;
	}

	public float getDamage() {
		return damage;
	}
}
