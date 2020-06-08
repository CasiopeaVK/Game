package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.GameContext;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Game");
		config.setWindowedMode(1600, 900);
		config.setWindowPosition(150, 70);
		config.setResizable(false);
		new Lwjgl3Application(new GameContext(), config);
	}
}
