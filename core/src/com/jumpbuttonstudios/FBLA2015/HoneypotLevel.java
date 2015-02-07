package com.jumpbuttonstudios.FBLA2015;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class HoneypotLevel extends GameScreen {

	public HoneypotLevel(FBLA2015 game) {
		super(game, "Honeypot");

		parseCustoms(map.getCustomsLayer());
		this.explanationText = "";
	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();

		}
	}

}
