package de.jbazer.survivalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    /** TAG for logging.*/
    private static final String LOG = SoundManager.class.getSimpleName();
    private Sound pickupSound;
    private Sound miau1;
    private static SoundManager instance;
    
    public SoundManager() {
        System.out.println("init=");
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sound/pickup.ogg"));
        miau1 = Gdx.audio.newSound(Gdx.files.internal("sound/miau1.ogg"));
        instance = this;
    }
    
    public static SoundManager getInstance() {
        return instance;
    }
    
    public void playPickUp() {
        pickupSound.play();
    }
    
    public void playMiau() {
        miau1.play();
    }
    
}
