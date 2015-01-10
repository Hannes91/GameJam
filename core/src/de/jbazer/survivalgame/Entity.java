package de.jbazer.survivalgame;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Entity {

    /** TAG for logging. */
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
    protected boolean stopped;
    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;
    protected int moveSpeed;
    protected TiledMapTileLayer l1;
    protected TiledMapTileLayer l2;
    protected int tileSize;
    protected Animation animation;
    private HashMap<String, TiledMapTile> tiles;
    private ArrayList<TiledMapTile> tilesGround;
    private ArrayList<TiledMapTile> tilesObjects;
    /** Is set when player gets extra time through powerUps etc. */
    public int heal;
    private boolean mouseAlive;
    protected boolean tOn;
    private boolean isP;

    public Entity(TiledMap map) {
        super();
        l1 = (TiledMapTileLayer) map.getLayers().get(0);
        l2 = (TiledMapTileLayer) map.getLayers().get(1);
        tileSize = map.getProperties().get("tilewidth", Integer.class);
        animation = new Animation();
        // save tiles
        tiles = new HashMap<String, TiledMapTile>();
        tiles.put("fenceHorizontal", map.getTileSets().getTile(1));
        tiles.put("fenceVertical", map.getTileSets().getTile(2));
        tiles.put("grass", map.getTileSets().getTile(3));
        tiles.put("stone", map.getTileSets().getTile(4));
        tiles.put("goal", map.getTileSets().getTile(5));
        tiles.put("cross", map.getTileSets().getTile(6));
        tiles.put("shoe", map.getTileSets().getTile(7));
        tiles.put("flower", map.getTileSets().getTile(8));
        tiles.put("antifreeze", map.getTileSets().getTile(9));
        if (this.equals(Mouse.class)) {
            System.out.println("is Mouse");
        }
    }

    /**
     * Shall only be called when Entity is the Player.
     */
    public void initStuff() {
        this.isP = true;
        placeObstacleRandom();
        placeObstacleRandom();
        placeObstacleRandom();
        placeObstacleRandom();
        createNew("antifreeze");
    }

    /**
     * Place an obstacle of siz 2x3 at a random position.
     */
    private void placeObstacleRandom() {
        int x, y;
        // do {
        x = (int) (Math.random() * (l1.getWidth() - 6));
        y = (int) (Math.random() * (l1.getHeight() - 6));
        // } while (l1.getCell(x, y).getTile().getProperties()
        // .containsKey("blocked")
        // || l2.getCell(x, y) != null);
        Cell cell = new Cell();
        cell.setTile(tiles.get("stone"));
        l1.setCell(x + 2, y + 2, cell);
        l2.setCell(x + 2, y + 2, null);
        l1.setCell(x + 3, y + 2, cell);
        l2.setCell(x + 3, y + 2, null);
        l1.setCell(x + 2, y + 3, cell);
        l2.setCell(x + 2, y + 3, null);
        l1.setCell(x + 3, y + 3, cell);
        l2.setCell(x + 3, y + 3, null);
        l1.setCell(x + 2, y + 4, cell);
        l2.setCell(x + 2, y + 4, null);
        l1.setCell(x + 3, y + 4, cell);
        l2.setCell(x + 3, y + 4, null);

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
            if (l1.getCell(coltile, rowtile + 1).getTile().getProperties()
                    .containsKey("blocked")) {
                this.stopped = true;
                return false;
            } else {
                ydest = y + tileSize;
            }
        }
        if (down) {
            if (l1.getCell(coltile, rowtile - 1).getTile().getProperties()
                    .containsKey("blocked")) {
                this.stopped = true;
                return false;
            } else {
                ydest = y - tileSize;
            }
        }
        if (left) {
            if (l1.getCell(coltile - 1, rowtile).getTile().getProperties()
                    .containsKey("blocked")) {
                this.stopped = true;
                return false;
            } else {
                xdest = x - tileSize;
            }
        }
        if (right) {
            if (l1.getCell(coltile + 1, rowtile).getTile().getProperties()
                    .containsKey("blocked")) {
                this.stopped = true;
                return false;
            } else {
                xdest = x + tileSize;
            }
        }
        // If is an collectable
        if (l2 != null && l2.getCell(coltile, rowtile) != null) {
            // if is a heal, heal stronger
            if (l2.getCell(coltile, rowtile).getTile().getProperties()
                    .containsKey("heal")) {
                if (isP) {
                    heal += 6;
                    SoundManager.getInstance().playMiau();
                    // MySurvivalGame.getInstance().pickup();
                }
                createNew("goal");
                createNew("flower");
                createNew("cross");
            }
            // Only when this is the player and not a mouse
            if (isP) {
                if (l2.getCell(coltile, rowtile).getTile().getProperties()
                        .containsKey("small_heal")) {
                    SoundManager.getInstance().playSound("pickup");
                    heal += 1;
                }
                if (l2.getCell(coltile, rowtile).getTile().getProperties()
                        .containsKey("shoe")) {
                    SoundManager.getInstance().playSound("pickup");
                    this.moveSpeed++;
                }
                if (l2.getCell(coltile, rowtile).getTile().getProperties()
                        .containsKey("decrease")) {
                    SoundManager.getInstance().playSound("dec");
                    heal -= 3;
                }
                if (l2.getCell(coltile, rowtile).getTile().getProperties()
                        .containsKey("die")) {
                    SoundManager.getInstance().playSound("dec");
                    startDying();
                }
            }
            l2.setCell(coltile, rowtile, null);
        }
        return true;
    }

    private void startDying() {
        tOn = true;
        new Thread(new Runnable() {
            public void run() {
                while (tOn == true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    heal--;
                }
            }
        }).start();
    }

    public void mouse() {
        if (!mouseAlive) {
            mouseAlive = true;
            int ran = (int) Math.random() * l1.getWidth();
            boolean horizontal = ran % 2 == 0;
            boolean fromTop = (int) Math.random() * 2 % 2 == 0;
            Cell cell = new Cell();
            cell.setTile(tiles.get("mouse"));
            // random rand position
            if (horizontal) {
                if (fromTop) {
                    l2.setCell(2, ran, cell);
                } else {
                    l2.setCell(40, ran, cell);
                }
            } else {
                if (fromTop) {
                    l2.setCell(ran, 2, cell);
                } else {
                    l2.setCell(ran, 40, cell);
                }
            }
        }
    }

    public void createNew(String type) {
        int ranX, ranY;
        if (mapFull()) {
            // TODO: check if map is full, else you get nullPointer in the Case
            // some gets all Fields full
        }
        do {
            ranX = (int) (Math.random() * l1.getWidth());
            ranY = (int) (Math.random() * l1.getHeight());
        } while (l1.getCell(ranX, ranY).getTile().getProperties()
                .containsKey("blocked")
                || l2.getCell(ranX, ranY) != null);
        // System.out.println("Place new Stuff on " + ranX + ", " + ranY);
        Cell cell = new Cell();
        cell.setTile(tiles.get(type));
        l2.setCell(ranX, ranY, cell);
        ;
    }

    private boolean mapFull() {
        // TODO Auto-generated method stub
        return false;
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
