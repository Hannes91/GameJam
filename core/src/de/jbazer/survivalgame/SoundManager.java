package de.jbazer.survivalgame;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    /** TAG for logging. */
    private static final String LOG = SoundManager.class.getSimpleName();
    private HashMap<String, Sound> sounds;
    private static SoundManager instance;

    public SoundManager() {
        System.out.println("init=");
        sounds = new HashMap<String, Sound>();
        sounds.put("pickup",
                Gdx.audio.newSound(Gdx.files.internal("sound/pickup.ogg")));
        sounds.put("miau1",
                Gdx.audio.newSound(Gdx.files.internal("sound/miau1.ogg")));
        sounds.put("miau2",
                Gdx.audio.newSound(Gdx.files.internal("sound/miau2.ogg")));
        sounds.put("miau3",
                Gdx.audio.newSound(Gdx.files.internal("sound/miau3.ogg")));
        sounds.put("miau4",
                Gdx.audio.newSound(Gdx.files.internal("sound/miau4.ogg")));
        sounds.put("miau5",
                Gdx.audio.newSound(Gdx.files.internal("sound/miau5.ogg")));
        sounds.put("dec",
                Gdx.audio.newSound(Gdx.files.internal("sound/dec.ogg")));
        instance = this;
    }

    public static SoundManager getInstance() {
        return instance;
    }

    public void playSound(String name) {
        sounds.get(name).play();
    }

    public void playMiau() {
        int ran = (int) (Math.random() * 5);
        System.out.println("Ran: " + ran);
        String name = "miau" + (ran + 1);
        System.out.println("Play " + name);
        sounds.get(name).play();
    }

}
