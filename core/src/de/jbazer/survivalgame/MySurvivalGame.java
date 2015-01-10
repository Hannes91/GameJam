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
    private int highscore;
    private static MySurvivalGame instance;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);
        res = new ResourceManager();
        res.loadImg("Sprites/catSprite.png", "player");
        setScreen(new GameScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont();
        instance = this;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void restart() {
        System.out.println("restart game");
        setScreen(new GameScreen(this));
    }
    
    
    /**
     * @return the highscore
     */
    public final int getHighscore() {
        return highscore;
    }

    /**
     * @param highscore the highscore to set
     */
    public final void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public static MySurvivalGame getInstance() {
        return instance;
    }
}
