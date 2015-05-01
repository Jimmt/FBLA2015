package com.jumpbuttonstudio.FBLA2015.sprite;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpbuttonstudio.FBLA2015.util.GamePrefs;
import com.jumpbuttonstudio.FBLA2015.weapon.ItemStats;

/**
 * Save/array data for ship models and streaks.
 */
public class PlayerProfile {
	public static String[] streaks = { "Flood", "Incendiary", "Lime", "Uncle Sam", "Sith Lord",
			"Blood Dragon" };
	public static String streak;

	public static String[] ships = { "Light", "Medium", "Heavy" };
	public static String ship;
	public static ShipModel model;

	public static void initialize() {
		streak = "effects/streaks/" + streaks[GamePrefs.prefs.getInteger("streak")] + ".p";
		ship = "ships/" + ships[GamePrefs.prefs.getInteger("ship")] + ".png";

		switch (GamePrefs.prefs.getInteger("ship")) {
		case 0:
			model = ShipModel.LIGHT;
			break;
		case 1:
			model = ShipModel.MEDIUM;
			break;
		case 2:
			model = ShipModel.HEAVY;
			break;
		}
		
	}

	public static void updateModel() {
		switch (GamePrefs.prefs.getInteger("ship")) {
		case 0:
			model = ShipModel.LIGHT;
			break;
		case 1:
			model = ShipModel.MEDIUM;
			break;
		case 2:
			model = ShipModel.HEAVY;
			break;
		}
	}

	public enum ShipModel {
		LIGHT("Light", 100, ItemStats.LIGHT), MEDIUM("Medium", 150, ItemStats.MEDIUM), HEAVY(
				"Heavy", 200, ItemStats.HEAVY);

		String name;
		ItemStats stats;
		float health;

		ShipModel(String name, float health, ItemStats stats) {
			this.name = name;
			this.stats = stats;
			this.health = health;
		}

		public float getHealth() {
			return health;
		}

		public ItemStats getStats() {
			return stats;
		}

		public String getName() {
			return name;
		}

	}

}
