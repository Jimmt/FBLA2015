package com.jumpbuttonstudios.FBLA2015;

public class IntroductionLevel extends GameScreen {

	public IntroductionLevel(FBLA2015 game) {
		super(game, "introduction", false);

		hasBoss = false;
		explanationText = "Training complete.\n ";
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (objectiveCount == 0) {
			completeLevel();
		}
	}

}
