package com.jumpbuttonstudios.FBLA2015;

import java.util.Iterator;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Map extends Actor {
	private TiledMap map;
	private TmxMapLoader tmxL;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera oc;
	private Box2DMapObjectParser parser;
	private Vector2 playerSpawn;
	private GameScreen gs;
	private MapLayer positionsLayer;

	public Map(String path, GameScreen gs) {
		tmxL = new TmxMapLoader();
		map = tmxL.load(path);
		this.gs = gs;
		oc = (OrthographicCamera) gs.stage.getCamera();

		renderer = new OrthogonalTiledMapRenderer(map, Constants.SCALE);
		renderer.setMap(map);

		parser = new Box2DMapObjectParser();
		parser.getAliases().categoryBits = String.valueOf(Bits.MAP);
		parser.setUnitScale(Constants.SCALE);
		parser.load(gs.world, map);

		UserData userData = new UserData();
		userData.value = this;
		userData.tag = "map";

		Array<String> keys = parser.getBodies().keys().toArray();

		for (int i = 0; i < keys.size; i++) {
			parser.getBodies().get(keys.get(i)).setUserData(userData);
		}

		Iterator<MapLayer> layerIterator = map.getLayers().iterator();

		MapLayer lightsLayer = null;

		while (layerIterator.hasNext()) {
			MapLayer layer = layerIterator.next();
			if (layer.getName().equals("lights")) {
				lightsLayer = layer;

			}
			if (layer.getName().equals("positions")) {
				positionsLayer = layer;
			}
		}

		Iterator<MapObject> objectIterator = lightsLayer.getObjects().iterator();
		while (objectIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) objectIterator.next();
			if (object.getName().equals("cone")) {
				ConeLight light = new ConeLight(gs.rayHandler, 16, Color.WHITE, 800,
						object.getRectangle().x, object.getRectangle().y, 0, 45);
			}
			if (object.getName().equals("point")) {
				PointLight light = new PointLight(gs.rayHandler, 16, new Color(1, 1, 1, 0.75f),
						40f, object.getRectangle().x * Constants.SCALE, object.getRectangle().y
								* Constants.SCALE);
// light.setXray(true);
			}
		}

	}

	public void parsePositions() {
		Iterator<MapObject> positionsIterator = positionsLayer.getObjects().iterator();
		while (positionsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) positionsIterator.next();

			if (object.getName().equals("player spawn")) {
				playerSpawn = new Vector2(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE);
			}
			if (object.getName().equals("enemy spawn")) {
					gs.createEnemy(object.getRectangle().x * Constants.SCALE,
							object.getRectangle().y * Constants.SCALE);
				
			}
			if (object.getName().equals("boss spawn")) {

			}
		}
	}

	public Vector2 getPlayerSpawn() {
		return playerSpawn;
	}

	public TiledMap getMap() {
		return map;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		renderer.setView(oc);
		batch.end();
		renderer.render();
		batch.begin();

	}

}
