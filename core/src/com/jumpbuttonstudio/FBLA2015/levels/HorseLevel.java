package com.jumpbuttonstudio.FBLA2015.levels;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.boss.TrojanHorse;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.util.Constants;

public class HorseLevel extends GameScreen {
	TrojanHorse horse;

	public HorseLevel(FBLA2015 game) {
		super(game, "level3", false);

		parseCustoms(map.getCustomsLayer());
		explanationText = "You just killed a Trojan Horse. This type of malware disguises itself as a benign application, but when executed will often harm the system by granting unauthorized access to other computers and using CPU cycles and network bandwidth. Trojan Horses are only effective against the unwary user, and thus are often spread through mediums like email attachments or online torrents or downloads.";
//		completeLevel();
	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();

			if (object.getName().equals("horse")) {
				horse = new TrojanHorse(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, world);
				createBoss(horse);
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (horse.health <= 0 && stage.getActors().contains(horse, false)) {

			world.destroyBody(horse.body);
			stage.getActors().removeValue(horse, false);

			completeLevel();

		}
	}

}
