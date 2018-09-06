package com.soltrix.stargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.soltrix.stargame.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// задаем пропорции экрана
		float aspect = 3f/4f;
		config.width = 480;
		config.height = (int) (config.width / aspect);
		// запрещаем изменять размер экрана
		//config.resizable = false;
		new LwjglApplication(new Star2DGame(), config);
	}
}
