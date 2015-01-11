package de.jbazer.survivalgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class LoadManager extends AssetManager {

    /** TAG for logging. */
    private static final String LOG = LoadManager.class.getSimpleName();
    private static LoadManager instance;

    public void prepareAssets() {
//        this.load("text/text1.png", Texture.class);
        System.out.println("prepare assets in manager");
        this.load("text/text1.png", Texture.class);
    }

    public static LoadManager getInstance() {
        if (instance == null) {
            instance = new LoadManager();
        }
        return instance;
    }
}
