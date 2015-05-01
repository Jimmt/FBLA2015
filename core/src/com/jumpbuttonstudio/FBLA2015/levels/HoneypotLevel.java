package com.jumpbuttonstudio.FBLA2015.levels;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;
import com.jumpbuttonstudio.FBLA2015.ui.EnemiesKilledDialog;
import com.jumpbuttonstudio.FBLA2015.util.Constants;
import com.jumpbuttonstudio.FBLA2015.weapon.ItemStats;

/**
 * Honeypot Level (level 5)
 *
 */
public class HoneypotLevel extends GameScreen {
	Array<Vector2> spawnPoints = new Array<Vector2>();
	float lastSpawnTime = 999f, spawnCap = 3f;
	int enemyCap = 15;
	EnemiesKilledDialog dialog;

	public HoneypotLevel(FBLA2015 game) {
		super(game, "honeypot", false);

		parseCustoms(map.getCustomsLayer());
		this.explanationText = "";

		dialog = new EnemiesKilledDialog(enemyCap, skin);
		dialog.show(hudStage);
		dialog.addAction(Actions.sequence(Actions.moveBy(0, Constants.HEIGHT / 2),
				Actions.moveBy(0, Constants.HEIGHT / 2, 1f)));

		explanationText = "Honeypots can help fight malware by baiting infection from different types of malicious programs. Once the honeypot is infected, it can be inspected to dissect and fight against malware. Honeypots are isolated and quarantined, protecting the rest of the network. Honeypots operate with the intent of resembling an actual computer system as closely as possible, so any intrusion can be analyzed like an attack was performed on a real computer system.";
	}

	public void parseCustoms(MapLayer customsLayer) {

		Iterator<MapObject> customsIterator = customsLayer.getObjects().iterator();

		while (customsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) customsIterator.next();

			if (object.getName().contains("spawn")) {
				Vector2 position = new Vector2(object.getRectangle().x * Constants.SCALE
						+ object.getRectangle().width / 2 * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE + object.getRectangle().height
								/ 2 * Constants.SCALE);
				spawnPoints.add(position);
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		dialog.killed = enemiesKilled;

		if (lastSpawnTime > spawnCap && !gameOver) {
			lastSpawnTime = 0;

			if (spawnCap > 2f) {
				spawnCap -= 0.25f;
			}

			int index = MathUtils.random(spawnPoints.size - 1);

			createEnemy(ItemStats.LEVEL5_GUN, 100,
					"level5/enemy" + MathUtils.random(1, 4) + ".png", spawnPoints.get(index).x,
					spawnPoints.get(index).y, 40, true);
			

		} else {
			lastSpawnTime += delta;
		}

		if (enemiesKilled >= enemyCap) {
			completeLevel();
		}

	}

}
