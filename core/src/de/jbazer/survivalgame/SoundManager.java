package de.jbazer.survivalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    /** TAG for logging.*/
    private static final String LOG = SoundManager.class.getSimpleName();
    private Sound pickupSound;
    private static SoundManager instance;
    
    public SoundManager() {
        System.out.println("init=");
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sound/pickup.ogg"));
        instance = this;
    }
    
    public static SoundManager getInstance() {
        return instance;
    }
    
    public void pickup() {
        System.out.println("play sound");
        pickupSound.play();
    }
    
}
