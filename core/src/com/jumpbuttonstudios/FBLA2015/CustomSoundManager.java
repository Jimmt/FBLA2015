package com.jumpbuttonstudios.FBLA2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

public class CustomSoundManager {
	ObjectMap<String, Sound> sounds;

	private boolean play = true;

	public CustomSoundManager() {
		sounds = new ObjectMap<String, Sound>();
	}

	public void play(String name) {
		if (play)
			((Sound) sounds.get(name)).play();
	}

	public void play(String name, float volume) {
		if (play)
			((Sound) sounds.get(name)).play(volume);
	}

	public void loop(String name) {
		if (play)
			((Sound) sounds.get(name)).loop();
	}

	public void loadSound(String name, FileHandle file) {
		Sound sound = Gdx.audio.newSound(file);
		sounds.put(name, sound);
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean getPlay() {
		return play;
	}
}
