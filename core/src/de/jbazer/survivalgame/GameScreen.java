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
    private int time = 10;
    private boolean tOn = true;
    private final Stage stage;
    public BitmapFont font;
    private Label timeLabel;
    private Skin skin;
    private Label endLabel;
    private int gameState = this.GAME_RUNNING;
    private static final int GAME_RUNNING = 0;
    private static final int GAME_OVER = 1;

    public GameScreen(MySurvivalGame game) {
        super();
        this.stage = new Stage(new ExtendViewport(480, 320,
                new OrthographicCamera()));
        font = new BitmapFont();
        this.game = game;
        cam = this.game.getCamera();
        map = new TmxMapLoader().load("map/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        tilemapwidth = map.getProperties().get("width", Integer.class);
        tilemapHeight = map.getProperties().get("height", Integer.class);
        batch = new SpriteBatch();
        player = new Player(map);
        player.setTilePostion(1, 25);
        this.createUI();
        this.startTimer();
        Gdx.input.setInputProcessor(this);
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
                    if (time == 0) {
                        gameOver();
                        tOn = false;
                    } else {
                        time = time - 1;
                        timeLabel.setText("Time left: " + time);
                    }
                }
            }
        }).start();
    }

    protected void gameOver() {
        System.out.println("Game Over");
        this.endLabel = new Label("Game Over\n Click for restart", skin);
        this.endLabel.setColor(Color.RED);
        this.endLabel.setScale(10);
        this.endLabel.setPosition(230, 160);
        this.stage.addActor(endLabel);
        this.gameState = this.GAME_OVER;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.x = player.getX() + 480 / 4;
        cam.position.y = player.getY() + 320 / 4;
        if (cam.position.x < cam.viewportWidth / 2) {
            cam.position.x = cam.viewportWidth / 2;
        }
        if (cam.position.x > tilemapwidth * 16 - cam.viewportWidth / 2) {
            cam.position.x = tilemapwidth * 16 - cam.viewportWidth / 2;
        }
        if (cam.position.y < cam.viewportHeight / 2) {
            cam.position.y = cam.viewportHeight / 2;
        }
        if (cam.position.y > tilemapHeight * 16 - cam.viewportHeight / 2) {
            cam.position.y = tilemapHeight * 16 - cam.viewportHeight / 2;
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderer.setView(cam);
        renderer.render();
        player.update(delta);
        player.draw(batch);
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
