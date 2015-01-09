package de.jbazer.survivalgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Entity {

    /** TAG for logging.*/
    private static final String LOG = Entity.class.getSimpleName();
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int xdest;
    protected int ydest;
    protected int rowtile;
    protected int coltile;
    protected boolean moving;
    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;
    protected int moveSpeed;
    protected TiledMapTileLayer layer;
    protected int tileSize;
    protected Animation animation;

    public Entity(TiledMap map) {
        super();
        layer = (TiledMapTileLayer) map.getLayers().get(0);
        tileSize = map.getProperties().get("tilewidth", Integer.class);
        animation = new Animation();
    }

    public void setTilePostion(int i1, int i2) {
        x = i1 * tileSize;
        y = i2 * tileSize;
        xdest = x;
        ydest = y;
    }

    public void setUp() {
        if (moving)
            return;
        up = true;
        moving = validateNextPostion();
    }

    public void setDown() {
        if (moving)
            return;
        down = true;
        moving = validateNextPostion();
    }

    public void setLeft() {
        if (moving)
            return;
        left = true;
        moving = validateNextPostion();
    }

    public void setRight() {
        if (moving)
            return;
        right = true;
        moving = validateNextPostion();
    }

    public boolean validateNextPostion() {
        rowtile = y / tileSize;
        coltile = x / tileSize;
        if (up) {
            if (layer.getCell(coltile, rowtile + 1).getTile().getProperties()
                    .containsKey("blocked")) {
                return false;
            } else {
                ydest = y + tileSize;
            }
        }
        if (down) {
            if (layer.getCell(coltile, rowtile - 1).getTile().getProperties()
                    .containsKey("blocked")) {
                return false;
            } else {
                ydest = y - tileSize;
            }
        }
        if (left) {
            if (layer.getCell(coltile - 1, rowtile).getTile().getProperties()
                    .containsKey("blocked")) {
                return false;
            } else {
                xdest = x - tileSize;
            }
        }
        if (right) {
            if (layer.getCell(coltile + 1, rowtile).getTile().getProperties()
                    .containsKey("blocked")) {
                return false;
            } else {
                xdest = x + tileSize;
            }
        }
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void getNextPostion() {
        if (left && x > xdest) {
            x -= moveSpeed;
        } else {
            left = false;
        }
        if (left && x < xdest) {
            x = xdest;
        }
        if (right && x < xdest) {
            x += moveSpeed;
        } else {
            right = false;
        }
        if (right && x > xdest) {
            x = xdest;
        }
        if (up && y < ydest) {
            y += moveSpeed;
        } else {
            up = false;
        }
        if (up && y > ydest) {
            y = ydest;
        }
        if (down && y > ydest) {
            y -= moveSpeed;
        } else {
            down = false;
        }
        if (down && y < ydest) {
            y = ydest;
        }
    }

    public void update(float dt) {
        if (moving) {
            getNextPostion();
        }
        if (x == xdest && y == ydest) {
            up = down = left = right = moving = false;
            rowtile = y / tileSize;
            coltile = x / tileSize;
        }
        animation.update(dt);
    }

    public void draw(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getFrames(), x, y, width, height);
        sb.end();
    }

}
