package com.jumpbuttonstudios.FBLA2015;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class WormLevel extends GameScreen {
	Hole hole;
	Worm worm;
	float waitTime;
	boolean done;

	public WormLevel(FBLA2015 game) {
		super(game, "level2", false);
		parseCustoms(map.getCustomsLayer());
		explanationText = "You just killed a computer worm. This type of malware exploits bugs in operating system software to enter the computer, and then installs a backdoor to allow an external user to control the computer. Worms will also attempt to spread themselves to other computers, creating networks of computers all under the control of the original creator of the worm. However, this won't happen here, because you patched up the backdoor in the wall. Looks like this data center will survive another day.";
// completeLevel();
	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();
			if (object.getName().equals("hole")) {
				hole = new Hole();
				stage.addActor(hole);
				hole.setPosition(object.getRectangle().x * Constants.SCALE
						+ object.getRectangle().width * Constants.SCALE / 2 - hole.getWidth() / 2,
						object.getRectangle().y * Constants.SCALE + object.getRectangle().height
								* Constants.SCALE / 2 - hole.getHeight() / 2);
			}

			if (object.getName().equals("worm")) {
				worm = new Worm(object.getRectangle().x * Constants.SCALE, object.getRectangle().y
						* Constants.SCALE, world);
				createBoss(worm);
			}

			if (object.getName().equals("trigger")) {
				trigger = object.getRectangle();
				trigger.set(trigger.x * Constants.SCALE, trigger.y * Constants.SCALE, trigger.width
						* Constants.SCALE, trigger.height * Constants.SCALE);
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (worm.health <= 0) {
			for (int i = 0; i < worm.segments.size; i++) {
				world.destroyBody(worm.segments.get(i).body);
				worm.segments.removeIndex(i);
			}
		}

		if (player.hitbox.overlaps(hole.hitbox) && worm.health <= 0) {
			stage.getActors().removeValue(hole, false);
			done = true;
		}

		if (done) {
			if (waitTime > 1f) {
				completeLevel();
			} else {
				waitTime += delta;
			}
		}

		if (worm.health <= 0) {
			commDialog.setColor(1, 1, 1, 1);
			commDialog.setText("Move to the hole in the wall to fix it.");
		}

	}

}
