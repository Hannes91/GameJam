package de.jbazer.survivalgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.jbazer.survivalgame.MySurvivalGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "My Survival Game";
        config.width = 800;
        config.height = 480;
		new LwjglApplication(new MySurvivalGame(), config);
	}
}
