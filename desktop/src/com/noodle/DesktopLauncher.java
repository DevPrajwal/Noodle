package com.noodle;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noodle.MainGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Noodle";
		config.width = 1920;
		config.height = 1080;
		config.addIcon("ic_launcher128.png", Files.FileType.Internal);
		config.addIcon("ic_launcher32.png", Files.FileType.Internal);
		config.addIcon("ic_launcher16.png", Files.FileType.Internal);

		new LwjglApplication(new MainGame(), config);
	}
}
