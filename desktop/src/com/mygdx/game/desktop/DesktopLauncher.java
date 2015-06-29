package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Aloksaj";
		config.width = 800;
		config.height = 400;
		config.addIcon("img/logo.png", FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
