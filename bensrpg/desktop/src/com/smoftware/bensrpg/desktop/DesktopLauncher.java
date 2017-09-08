package com.smoftware.bensrpg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smoftware.bensrpg.BensRPG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.width = 1280;
		cfg.height = 720;

		// fullscreen
		//cfg.fullscreen = true;
		// vSync
		//cfg.vSyncEnabled = true;

		new LwjglApplication(new BensRPG(), cfg);
	}
}
