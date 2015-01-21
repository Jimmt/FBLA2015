package com.jumpbuttonstudios.FBLA2015;

public class TutorialLevel extends GameScreen {

	public TutorialLevel(FBLA2015 game) {
		super(game, "tutorial");

		hasBoss = false;
		explanationText = "";
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (objectiveCount == 0) {
			completeLevel();
		}
	}

}
