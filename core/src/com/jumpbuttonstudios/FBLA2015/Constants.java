package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;

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
}
