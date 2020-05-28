package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.GameContext;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Game");
		config.setWindowedMode(1280, 720);
		config.setWindowPosition(100, 50);
//		config.width = 1280;
//		config.height = 720;
//		config.title = "Game";
//		config.x = 100;
//		config.y = 50;
//		config.forceExit = false;
		new Lwjgl3Application(new GameContext(), config);
	}
}
