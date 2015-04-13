package com.jumpbuttonstudios.FBLA2015.desktop;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jumpbuttonstudios.FBLA2015.FBLA2015;

public class FBLADesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

// if(d.getWidth() < 1024){
		config.width = (int) d.getWidth();
		config.x = 0;
// } else {
// config.width = 1024;
// }
// if(d.getHeight() < 768){
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(
				new JFrame().getGraphicsConfiguration());
		int taskbarSize = scnMax.bottom;

		config.height = (int) d.getHeight() - taskbarSize * 2;
		config.y = 0;
// } else {
// config.height = 768;
// }

		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new FBLA2015(), config);
	}
}
