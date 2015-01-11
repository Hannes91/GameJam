package de.jbazer.survivalgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class LoadManager extends AssetManager {

    /** TAG for logging. */
    private static final String LOG = LoadManager.class.getSimpleName();
    private static LoadManager instance;

    public void prepareAssets() {
        this.load("text/text1.png", Texture.class);
        this.load("text/text2.png", Texture.class);
        this.load("text/text3.png", Texture.class);
        this.load("text/text4.png", Texture.class);
        this.load("text/text5.png", Texture.class);
        this.load("text/text6.png", Texture.class);
        this.load("text/text7.png", Texture.class);
        this.load("text/text8.png", Texture.class);
        this.load("text/text9.png", Texture.class);
        this.load("text/text10.png", Texture.class);
        this.load("text/text11.png", Texture.class);
        this.load("text/text12.png", Texture.class);
        this.load("text/text13.png", Texture.class);
        this.load("text/text14.png", Texture.class);
    }

    public static LoadManager getInstance() {
        if (instance == null) {
            instance = new LoadManager();
        }
        return instance;
    }
}
