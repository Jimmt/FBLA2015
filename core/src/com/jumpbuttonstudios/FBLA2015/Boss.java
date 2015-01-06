package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Boss extends Actor {
	float healthMax, health;
	protected Player player;

	public Boss(float health) {
		this.health = health;
		this.healthMax = health;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
}
