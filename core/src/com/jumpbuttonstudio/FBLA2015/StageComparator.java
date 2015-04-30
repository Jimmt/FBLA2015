package com.jumpbuttonstudio.FBLA2015;

import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class StageComparator implements Comparator<Actor> {

	@Override
	public int compare(Actor a, Actor b) {

		if (a.getZIndex() < b.getZIndex()) {
			return -1;
		} else if (a.getZIndex() == b.getZIndex()) {
			return 0;
		} else {
			return 1;
		}
	}

}
