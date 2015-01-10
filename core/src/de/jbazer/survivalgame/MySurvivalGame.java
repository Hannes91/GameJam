package de.jbazer.survivalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private Music music;
    private static Sound pickupSound;
    private SoundManager sound;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);
        res = new ResourceManager();
        res.loadImg("Sprites/catSprite.png", "player");
        res.loadImg("Sprites/mouseSprite.png", "mouse");
        setScreen(new GameScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont();
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music_garden.ogg"));
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sound/pickup.ogg"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
        sound = new SoundManager();
        sound.playMiau();
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
