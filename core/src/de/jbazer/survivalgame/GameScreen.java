package de.jbazer.survivalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen, InputProcessor {

    /** TAG for logging. */
    private static final String LOG = GameScreen.class.getSimpleName();
    private MySurvivalGame game;
    private OrthographicCamera cam;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private Player player;
    private int tilemapwidth, tilemapHeight;
    private int time = 20;
    private boolean tOn = true;
    private final Stage stage;
    public BitmapFont font;
    private Label timeLabel;
    private Skin skin;
    private Label endLabel;
    private int gameState = this.GAME_RUNNING;
    protected int score;
    private int highscore;
    private boolean newHighscore;
    private Mouse mouse;
    private static final int GAME_RUNNING = 0;
    private static final int GAME_OVER = 1;

    public GameScreen(MySurvivalGame game) {
        super();
        this.stage = new Stage(new ExtendViewport(480, 320,
        // this.stage = new Stage(new ExtendViewport(960, 640,
                new OrthographicCamera()));
        font = new BitmapFont();
        this.game = game;
        cam = this.game.getCamera();
        map = new TmxMapLoader().load("map/garden.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        tilemapwidth = map.getProperties().get("width", Integer.class);
        tilemapHeight = map.getProperties().get("height", Integer.class);
        batch = new SpriteBatch();
        player = new Player(map);
        player.setTilePostion(1, 25);
        this.createUI();
        this.startTimer();
        Gdx.input.setInputProcessor(this);
        this.startMouseTimer();
        player.initStuff();
        // createNewMouse();
    }

    private void startMouseTimer() {
        int minWait = 3;
        int maxWait = 6;
        System.out.println("new mouse timer");
        final int ran = (int) (Math.random() * maxWait + minWait);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000 * ran);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createNewMouse();
            }
        }).start();
    }

    private void createNewMouse() {
        mouse = new Mouse(map);
        int ran = (int) (Math.random() * 42);
        boolean horizontal = ran % 2 == 0;
        boolean fromTop = (int) (Math.random() * 2) % 2 == 0;
        Cell cell = new Cell();
        // cell.setTile(tiles.get("mouse"));
        // random rand position
        if (horizontal) {
            if (fromTop) {
                mouse.setTilePostion(2, ran);
                mouse.setDirection(Mouse.RIGHT);
                System.out.println("mouse ro right");
                // l2.setCell(2, ran, cell);
            } else {
                mouse.setTilePostion(39, ran);
                mouse.setDirection(Mouse.LEFT);
                System.out.println("mouse ro left");
                // l2.setCell(40, ran, cell);
            }
        } else {
            if (fromTop) {
                mouse.setTilePostion(ran, 2);
                mouse.setDirection(Mouse.UP);
                System.out.println("mouse ro up");
                // l2.setCell(ran, 2, cell);
            } else {
                mouse.setTilePostion(ran, 39);
                mouse.setDirection(Mouse.DOWN);
                System.out.println("mouse ro down");
                // l2.setCell(ran, 40, cell);
            }
        }
    }

    private void createUI() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.timeLabel = new Label("Timer-----", skin);
        this.timeLabel.setColor(Color.RED);
        this.timeLabel.setScale(2);
        this.timeLabel.setPosition(20, 280);
        this.stage.addActor(timeLabel);
    }

    /**
     * Run dying countdown.
     */
    private void startTimer() {
        new Thread(new Runnable() {
            public void run() {
                timeLabel.setText("Time left: " + time);
                while (tOn == true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // if (time <= 0) {
                    // gameOver();
                    // tOn = false;
                    // }
                    // else {
                    time = time - 1;
                    score++;
                    if (time % 2 == 0 || score > 60) {
                        player.createNew("cross");
                    }
                    if (score > 120) {
                        player.createNew("cross");
                    }
                    timeLabel.setText("Time left: " + time);
                    // }
                }
            }
        }).start();
    }

    protected void gameOver() {
        System.out.println("Game Over");
        if (this.gameState != this.GAME_OVER) {

            if (score > MySurvivalGame.getInstance().getHighscore()) {
                MySurvivalGame.getInstance().setHighscore(score);
                this.endLabel = new Label(
                        "   Game Over\nClick for restart\n      Score: "
                                + score + "\nNew Highscore", skin);
            } else {
                this.endLabel = new Label(
                        "   Game Over\nClick for restart\n      Score: "
                                + score + "\nCurrent Highscore: "
                                + MySurvivalGame.getInstance().getHighscore(),
                        skin);
            }
            this.endLabel.setColor(Color.RED);
            this.endLabel.setScale(10);
            this.endLabel.setPosition(230, 150);
            this.stage.addActor(endLabel);
            this.gameState = this.GAME_OVER;
        }
    }

    @Override
    public void render(float delta) {
        if (time <= 0) {
            gameOver();
            tOn = false;
        }
        if (player.heal != 0) {
            this.time = this.time + player.heal;
            player.heal = 0;
            timeLabel.setText("Time left: " + time);
        }
        if (mouse != null)
            System.out.println("Check Mouse catch. PlayerPos: " + player.getX()
                    + ", " + player.getY() + "  Mouse: " + mouse.getX() + ", "
                    + mouse.getY());
        if (mouse != null
                && (int) player.getX() / 32 == (int) mouse.getX() / 32
                && (int) player.getY() / 32 == (int) mouse.getY() / 32) {
            // Mouse catched
            System.out.println("MOUSE CATCHED");
            mouse = null;
            startMouseTimer();
            player.heal += 10;
        }
        if (mouse != null && mouse.stopped) {
            mouse = null;
            startMouseTimer();
        }
        Gdx.gl.glClearColor(0, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.x = player.getX() + 960 / 4;
        cam.position.y = player.getY() + 640 / 4;
        if (cam.position.x < cam.viewportWidth / 2) {
            cam.position.x = cam.viewportWidth / 2;
        }
        if (cam.position.x > tilemapwidth * 32 - cam.viewportWidth / 2) {
            cam.position.x = tilemapwidth * 32 - cam.viewportWidth / 2;
        }
        if (cam.position.y < cam.viewportHeight / 2) {
            cam.position.y = cam.viewportHeight / 2;
        }
        if (cam.position.y > tilemapHeight * 32 - cam.viewportHeight / 2) {
            cam.position.y = tilemapHeight * 32 - cam.viewportHeight / 2;
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderer.setView(cam);
        renderer.render();
        player.update(delta);
        player.draw(batch);
        if (mouse != null) {
            mouse.update(delta);
            mouse.draw(batch);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            player.setUp();
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            player.setDown();
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            player.setLeft();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            player.setRight();
        }
        this.stage.act(delta);
        this.stage.draw();
        // game.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        // game.font.setScale(5);
        // batch.begin();
        // game.font.draw(batch, "Time left: ", 100, 100);
        // batch.end();
        // game.font.draw(game.batch, "Time left: " + time + "s", 100, 100);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameState == this.GAME_OVER) {
            game.restart();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
