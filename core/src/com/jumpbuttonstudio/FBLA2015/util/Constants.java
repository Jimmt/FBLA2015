package com.jumpbuttonstudio.FBLA2015.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * Utility class
 */
public class Constants {
	public static float WIDTH = Gdx.graphics.getWidth();
	public static float HEIGHT = Gdx.graphics.getHeight();
	public static float SCALE = 1 / 100f;
	public static float SCLWIDTH = WIDTH * SCALE;
	public static float SCLHEIGHT = HEIGHT * SCALE;
	public static float TILE_SIZE = 32f;
	private static float screenRadius = WIDTH;
	public static float ENEMY_AGGRO_RANGE = screenRadius * Constants.SCALE;
	public static float ENEMY_FIRE_RANGE = screenRadius / 4 * 3f * Constants.SCALE;

	public static void update() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		SCLWIDTH = WIDTH * SCALE;
		SCLHEIGHT = HEIGHT * SCALE;
		screenRadius = WIDTH;
		ENEMY_AGGRO_RANGE = screenRadius * Constants.SCALE;
		ENEMY_FIRE_RANGE = screenRadius / 4 * 3f * Constants.SCALE;
	}

}
