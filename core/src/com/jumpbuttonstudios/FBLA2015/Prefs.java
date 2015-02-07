package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
	static Preferences prefs;

	public static void load() {
		prefs = Gdx.app.getPreferences("options");

		if (!prefs.contains("sound")) {
			prefs.putBoolean("sound", true);
			prefs.putBoolean("fullscreen", false);
		}
	}
	
	public static void putBoolean(String key, boolean val){
		prefs.putBoolean(key, val);
		prefs.flush();
	}
}
