package com.jumpbuttonstudios.FBLA2015;

import net.dermetfan.utils.libgdx.box2d.Box2DMapObjectParser;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Map extends Actor {
	private TiledMap map;
	private TmxMapLoader tmxL;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera oc;
	private Box2DMapObjectParser parser;

	public Map(String path, World world, OrthographicCamera oc) {
		tmxL = new TmxMapLoader();
		map = tmxL.load(path);

		this.oc = oc;

		renderer = new OrthogonalTiledMapRenderer(map, Constants.SCALE);
		renderer.setMap(map);

		parser = new Box2DMapObjectParser();
		parser.setUnitScale(Constants.SCALE);
		parser.load(world, map);

		UserData userData = new UserData();
		userData.value = this;
		userData.tag = "map";

		Array<String> keys = parser.getBodies().keys().toArray();
		
		for(int i = 0; i < keys.size; i++){
			parser.getBodies().get(keys.get(i)).setUserData(userData);
		}
		
		
//		parser.getBodies().entries().iterator().next();

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
