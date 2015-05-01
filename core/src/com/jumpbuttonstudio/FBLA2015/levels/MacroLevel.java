package com.jumpbuttonstudio.FBLA2015.levels;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.boss.MacroVirus;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.util.Textures;

/**
 * Macro Level (level 4)
 *
 */
public class MacroLevel extends GameScreen {
	MacroVirus virus;
	Array<Image> pages = new Array<Image>();
	boolean loaded;

	public MacroLevel(FBLA2015 game) {
		super(game, "macros", false);

		explanationText = "You just defeated a macro virus. This type of malware resides in files like word documents (.docx) or powerpoints (.ppt), exploiting the ability to use macros. Macros are essentially small programs meant to automate repetitive tasks; however, some people create malicious macros that infect the computer once the document or powerpoint is opened. Once a macro virus infects a computer, it will often propagate itself to other files that support macros, ensuring that the malware is spread as much as possible. ";
		parseCustoms(map.getCustomsLayer());

	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();

			if (object.getName().equals("boss")) {
				virus = new MacroVirus(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, world);
				createBoss(virus);

			}
			if (object.getName().contains("page")) {

				Image page = new Image(Textures.getTex("maps/" + object.getName()
						+ ".png"));
				page.setSize(page.getWidth() * Constants.SCALE, page.getHeight() * Constants.SCALE);

				page.setPosition(object.getRectangle().x * Constants.SCALE, object.getRectangle().y
						* Constants.SCALE);
				pages.add(page);
				stage.addActor(page);
				page.toBack();

			}
			if(object.getName().equals("trigger")){
				trigger = object.getRectangle();
				trigger.set(trigger.x * Constants.SCALE, trigger.y * Constants.SCALE, trigger.width * Constants.SCALE, trigger.height * Constants.SCALE);
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		if(player.hitbox.overlaps(trigger)){
			virus.aggro = true;
		}

		if (virus.health <= 0 && stage.getActors().contains(virus, false)) {
			world.destroyBody(virus.body);
			stage.getActors().removeValue(virus, false);
			completeLevel();
		}

	}

}
