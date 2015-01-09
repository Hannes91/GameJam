package de.jbazer.survivalgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    /** TAG for logging. */
    private static final String LOG = Animation.class.getSimpleName();
    private TextureRegion[] frames;
    private float time;
    private float delay;
    private int CurrentFrame;

    public Animation() {
        super();
    }

    public void setFrames(TextureRegion[] reg, float delay) {
        frames = reg;
        time = 0;
        CurrentFrame = 0;
        this.delay = delay;
    }

    public void update(float dt) {
        if (delay <= 0)
            return;
        time += dt;
        while (time >= delay) {
            time -= delay;
            CurrentFrame++;
            if (CurrentFrame == frames.length) {
                CurrentFrame = 0;
            }
        }
    }

    public TextureRegion getFrames() {
        return frames[CurrentFrame];
    }

}
