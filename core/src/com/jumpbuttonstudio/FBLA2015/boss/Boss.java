package com.jumpbuttonstudio.FBLA2015.boss;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jumpbuttonstudio.FBLA2015.sprite.Player;

/**
 * Base boss class
 *
 */
public class Boss extends Actor {
	public float healthMax;
	public float health;
	public float damage;
	protected Player player;
	public boolean aggro;
	public String name;

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
