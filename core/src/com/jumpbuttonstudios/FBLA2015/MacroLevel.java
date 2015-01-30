package com.jumpbuttonstudios.FBLA2015;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class MacroLevel extends GameScreen {
	MacroVirus virus;
	Array<Image> pages = new Array<Image>();
	boolean loaded;

	public MacroLevel(FBLA2015 game) {
		super(game, "Macros");

		explanationText = "";
		parseCustoms(map.getCustomsLayer());

	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();

			if (object.getName().equals("macro")) {
				virus = new MacroVirus(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, world);
				createBoss(virus);
			}
			if (object.getName().contains("page")) {
				
				Image page = new Image(new Texture(Gdx.files.internal("maps/" + object.getName()
						+ ".png")));
				page.setSize(page.getWidth() * Constants.SCALE, page.getHeight() * Constants.SCALE);

				page.setPosition(object.getRectangle().x * Constants.SCALE, object.getRectangle().y
						* Constants.SCALE);
				pages.add(page);
				stage.addActor(page);
				page.toBack();

			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

}
