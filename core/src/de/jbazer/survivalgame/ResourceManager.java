package de.jbazer.survivalgame;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {

    /** TAG for logging.*/
    private static final String LOG = ResourceManager.class.getSimpleName();
    private HashMap<String, Texture> textures;
    
    public ResourceManager() {
        super();
        textures = new HashMap<String, Texture>();
    }
    
    public void loadImg(String path, String name) {
        textures.put(name, new Texture(Gdx.files.internal(path)));
    }
    
    public Texture getTexture(String name) {
        return textures.get(name);
    }
}
