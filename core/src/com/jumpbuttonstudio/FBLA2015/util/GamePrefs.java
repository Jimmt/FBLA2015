package com.jumpbuttonstudio.FBLA2015.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Utility class for libgdx's Preferences
 */
public class GamePrefs {
	public static Preferences prefs;

	public static void initialize() {
		prefs = Gdx.app.getPreferences("settings");

		if (!prefs.contains("credits")) {
			prefs.putInteger("credits", 0);
		}
		if (!prefs.contains("streak")) {
			prefs.putInteger("streak", 0);
		}
		if (!prefs.contains("ship")) {
			prefs.putInteger("ship", 0);
		}
		if(!prefs.contains("bonusDamage")){
			prefs.putInteger("bonusDamage", 0);
			prefs.putInteger("bonusHealth", 0);
			prefs.putInteger("bonusSpeed", 0);
		}
		if (!prefs.contains("sound")) {
			prefs.putBoolean("sound", true);
		}
		if(!prefs.contains("width")){
			prefs.putInteger("width", Gdx.graphics.getWidth());
			prefs.putInteger("height", Gdx.graphics.getHeight());
		}
		if(!prefs.contains("maxWidth")){
			prefs.putInteger("maxWidth", Gdx.graphics.getWidth());
			prefs.putInteger("maxHeight", Gdx.graphics.getHeight());
		}
		prefs.flush();
	}

	public static void putBoolean(String name, boolean value) {
		prefs.putBoolean(name, value);
		prefs.flush();
	}

	public static void putInteger(String name, int value) {
		prefs.putInteger(name, value);
		prefs.flush();
	}
}
