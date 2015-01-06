package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Boss extends Actor {
	float healthMax, health, damage;
	protected Player player;
	boolean aggro;
	String name;

	public Boss(float health, float damage, String name) {
		this.health = health;
		this.healthMax = health;
		this.name = name;
		this.damage = damage;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
}
