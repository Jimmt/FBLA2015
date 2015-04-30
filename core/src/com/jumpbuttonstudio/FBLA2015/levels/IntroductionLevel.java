package com.jumpbuttonstudio.FBLA2015.levels;

import com.jumpbuttonstudio.FBLA2015.FBLA2015;
import com.jumpbuttonstudio.FBLA2015.screens.GameScreen;

public class IntroductionLevel extends GameScreen {

	public IntroductionLevel(FBLA2015 game) {
		super(game, "introduction", false);

		hasBoss = false;
		explanationText = "Training complete. You can re-customize your ship, or upgrade your ship's abilities in the level select screen, using the bytecoins that you have earned by killing enemies.";
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (objectiveCount == 0) {
			completeLevel();
		}
	}

}
