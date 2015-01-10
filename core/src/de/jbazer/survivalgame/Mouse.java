package de.jbazer.survivalgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Mouse extends Entity {

    /** TAG for logging. */
    private static final String LOG = Mouse.class.getSimpleName();
    private TextureRegion[] spritesDown, spritesRight, spritesUp, spritesLeft;

    public Mouse(TiledMap map) {
        super(map);
        width = 32;
        height = 32;
        moveSpeed = 3;
        Texture tex = MySurvivalGame.res.getTexture("mouse");
        spritesDown = new TextureRegion[1];
        for (int i = 0; i < spritesDown.length; i++) {
            spritesDown[i] = new TextureRegion(tex, i * width, 0, width, height);
        }
        spritesRight = new TextureRegion[1];
        for (int i = 0; i < spritesRight.length; i++) {
            spritesRight[i] = new TextureRegion(tex, i * width, 32, width,
                    height);
        }
        spritesUp = new TextureRegion[1];
        for (int i = 0; i < spritesUp.length; i++) {
            spritesUp[i] = new TextureRegion(tex, i * width, 64, width, height);
        }
        spritesLeft = new TextureRegion[1];
        for (int i = 0; i < spritesLeft.length; i++) {
            spritesLeft[i] = new TextureRegion(tex, i * width, 96, width,
                    height);
        }
        animation.setFrames(spritesDown, 1 / 5f);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (right) {
            animation.setFrames(spritesRight, 1 / 5f);
        }
        if (down) {
            animation.setFrames(spritesDown, 1 / 5f);
        }
        if (up) {
            animation.setFrames(spritesUp, 1 / 5f);
        }
        if (left) {
            animation.setFrames(spritesLeft, 1 / 5f);
        }
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        super.setUp();
    }

    @Override
    public void setDown() {
        // TODO Auto-generated method stub
        super.setDown();
    }

    @Override
    public void setLeft() {
        // TODO Auto-generated method stub
        super.setLeft();
    }

    @Override
    public void setRight() {
        // TODO Auto-generated method stub
        super.setRight();
    }

}
