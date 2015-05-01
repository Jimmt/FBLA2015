package com.jumpbuttonstudio.FBLA2015.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Utility class for caching fonts
 */
public class Fonts {
	static HashMap<String, BitmapFont> cache = new HashMap<String, BitmapFont>();

	public static BitmapFont getFont(String path) {

		if (cache.containsKey(path)) {
			return cache.get(path);
		} else {
			BitmapFont font = new BitmapFont(Gdx.files.internal(path));
			cache.put(path, font);
			return font;
		}
	}
}
