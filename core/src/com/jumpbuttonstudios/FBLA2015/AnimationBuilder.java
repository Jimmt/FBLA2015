package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationBuilder {

	public static Animation fillAnimation(float frameTime, int width, int height, int[] removes, String path) {
		Texture sheet = Textures.getTex(path);

		Array<TextureRegion> regionList = new Array<TextureRegion>();
		TextureRegion[][] regions = TextureRegion.split(sheet, width, height);
		
		for (int i = 0; i < regions.length; i++) {
			for (int j = 0; j < regions[0].length; j++) {
				regionList.add(regions[i][j]);
			}
		}
		
		for(int i = 0; i < removes.length; i++){
			regionList.removeIndex(removes[i]);
		}

		return new Animation(frameTime, regionList);
	}
}
