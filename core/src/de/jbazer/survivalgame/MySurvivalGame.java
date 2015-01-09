package de.jbazer.survivalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MySurvivalGame extends Game {

    SpriteBatch batch;
    Texture img;
    private OrthographicCamera camera;
    public static ResourceManager res;
    public BitmapFont font;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 320);
        res = new ResourceManager();
        res.loadImg("Sprites/playerSprite.png", "player");
        setScreen(new GameScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void restart() {
        System.out.println("restart game");
        setScreen(new GameScreen(this));
    }
}
