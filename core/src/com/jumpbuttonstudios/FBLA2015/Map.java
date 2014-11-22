package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Map {
	private TiledMap map;
	private TmxMapLoader tmxL;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera oc;
	private Rectangle[][] collisions;

	public Map(String path, OrthographicCamera oc) {
		tmxL = new TmxMapLoader();
		map = tmxL.load(path);

		this.oc = oc;

		renderer = new OrthogonalTiledMapRenderer(map, 6);
		renderer.setMap(map);

		collisions = new Rectangle[map.getProperties().get("width", Integer.class)][map
				.getProperties().get("height", Integer.class)];

		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

		for (int i = 0; i < collisions[0].length; i++) {
			for (int j = 0; j < collisions.length; j++) {
				if (layer.getCell(i, j) != null) {
					if (layer.getCell(i, j).getTile().getId() == 2) {
						collisions[i][j] = new Rectangle(i * 6, j * 6, 48, 48);
					} else {
						collisions[i][j] = null;
					}
				}
			}
		}
	}

	public Rectangle[][] getCollisions() {
		return collisions;
	}

	public TiledMap getMap() {
		return map;
	}

	public void draw(Batch batch) {

		renderer.setView(oc);
		batch.end();
		renderer.render();
		batch.begin();

	}

}
