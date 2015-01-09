package de.jbazer.survivalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

    /** TAG for logging. */
    private static final String LOG = GameScreen.class.getSimpleName();
    private MySurvivalGame game;
    private OrthographicCamera cam;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private Player player;
    private int tilemapwidth, tilemapHeight;

    public GameScreen(MySurvivalGame game) {
        super();
        this.game = game;
        cam = this.game.getCamera();
        map = new TmxMapLoader().load("map/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        tilemapwidth = map.getProperties().get("width", Integer.class);
        tilemapHeight = map.getProperties().get("height", Integer.class);
        batch = new SpriteBatch();
        player = new Player(map);
        player.setTilePostion(1, 25);
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
}
