package com.jumpbuttonstudios.FBLA2015;

import java.util.Iterator;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;

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
	private MapLayer positionsLayer, commsLayer, obstaclesLayer;
	private TiledMapTileLayer tilesLayer;
	boolean[][] collisions;

	public Map(String path, GameScreen gs) {
		tmxL = new TmxMapLoader();
		map = tmxL.load(path);
		collisions = new boolean[map.getProperties().get("width", Integer.class)][map
				.getProperties().get("height", Integer.class)];
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
// if (layer.getName().equals("lights")) {
// lightsLayer = layer;
//
// }
			if (layer.getName().equals("positions")) {
				positionsLayer = layer;
			}
			if (layer.getName().equals("comms")) {
				commsLayer = layer;
			}
			if (layer.getName().equals("obstacles")) {
				obstaclesLayer = layer;
			}
			if (layer.getName().equals("tiles")) {
				tilesLayer = (TiledMapTileLayer) layer;
			}
		}

		for (int i = 0; i < map.getProperties().get("width", Integer.class); i++) {
			for (int j = 0; j < map.getProperties().get("height", Integer.class); j++) {
				TiledMapTileLayer.Cell cell = tilesLayer.getCell(i, j);

				if (cell != null) {
					if (tilesLayer.getCell(i, j).getTile().getProperties()
							.get("type", String.class).equals("floor")) {
						collisions[i][j] = false;
					} else {
						collisions[i][j] = true;
					}
				} else {
					collisions[i][j] = true;
				}
			}
		}

		parseComms();

// Iterator<MapObject> objectIterator = lightsLayer.getObjects().iterator();
// while (objectIterator.hasNext()) {
// RectangleMapObject object = (RectangleMapObject) objectIterator.next();
// if (object.getName().equals("cone")) {
// ConeLight light = new ConeLight(gs.rayHandler, 16, Color.WHITE, 800,
// object.getRectangle().x, object.getRectangle().y, 0, 45);
// }
// if (object.getName().equals("point")) {
//
// PointLight light = new PointLight(gs.rayHandler, Integer.valueOf(object
// .getProperties().get("rays", String.class)),
// new Color(0.6f, 0.6f, 0.6f, 1f), Integer.valueOf(object.getProperties()
// .get("distance", String.class)), object.getRectangle().x
// * Constants.SCALE, object.getRectangle().y * Constants.SCALE);
//
// light.setXray(true);
// }
//
// }

	}

	public void parseObstacles() {
		Iterator<MapObject> obstaclesIterator = obstaclesLayer.getObjects().iterator();

		while (obstaclesIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) obstaclesIterator.next();

			if (object.getName().equals("laser")) {
				Laser laser = new Laser(gs.player, object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, object.getRectangle().width
								* Constants.SCALE, object.getRectangle().height * Constants.SCALE,
						Float.valueOf(object.getProperties().get("repeat", String.class)));
				gs.stage.addActor(laser);

			}
			if (object.getName().equals("target")) {
				Target target = new Target(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, 0, gs.world);
				gs.stage.addActor(target);
			}
		}
	}

	public void parseComms() {
		Iterator<MapObject> commsIterator = commsLayer.getObjects().iterator();
		while (commsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) commsIterator.next();
			gs.comms.add(object);

		}
	}

	public void parsePositions() {
		Iterator<MapObject> positionsIterator = positionsLayer.getObjects().iterator();
		while (positionsIterator.hasNext()) {
			RectangleMapObject object = (RectangleMapObject) positionsIterator.next();

			if (object.getName().equals("player_spawn")) {
				playerSpawn = new Vector2(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE);
			}
			if (object.getName().equals("enemy_spawn")) {
				if (!object.getProperties().containsKey("stats")) {
					gs.createEnemy(ItemStats.TUTORIAL_GUN,
							"enemy/enemy.png",
							object.getRectangle().x * Constants.SCALE,
							object.getRectangle().y * Constants.SCALE,
							Float.parseFloat(object.getProperties().get("health", String.class)),
							Boolean.parseBoolean(object.getProperties().get("objective",
									String.class)));
				} else {

				}

			}
			if(object.getName().equals("2_enemy")){
				gs.createEnemy(ItemStats.LEVEL2_GUN,
						"enemy/level2enemy.png",
						object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE,
						Float.parseFloat(object.getProperties().get("health", String.class)),
						Boolean.parseBoolean(object.getProperties().get("objective",
								String.class)));
			}
			if (object.getName().equals("worm")) {
				Worm worm = new Worm(object.getRectangle().x * Constants.SCALE,
						object.getRectangle().y * Constants.SCALE, gs.world);
				gs.createBoss(worm);
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
