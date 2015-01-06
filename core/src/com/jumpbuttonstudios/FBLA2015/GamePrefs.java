package com.jumpbuttonstudios.FBLA2015;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePrefs {
	public static Preferences prefs;

	public static void initialize(int levelNum) {
		prefs = Gdx.app.getPreferences("settings");

		if (!prefs.contains("1")) {

			for (int i = 0; i < levelNum; i++) {
				prefs.putBoolean(String.valueOf(i), false);
			}
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
