package de.jbazer.survivalgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.jbazer.survivalgame.MySurvivalGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cat's starvation";
        config.width = 1000;
        config.height = 600;
		new LwjglApplication(new MySurvivalGame(), config);
	}
}
