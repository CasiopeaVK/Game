package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.map.Map;

import java.util.EnumMap;

public class GameScreen extends AbstractScreen {

    private final IsometricTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Entity player;
    private World world;
    private Map map;

    public GameScreen(final GameContext context) {
        super(context);
        mapRenderer = new IsometricTiledMapRenderer(null, 1/4f, context.getSpriteBatch());
        camera = context.getCamera();
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.SHAPE_STATIC.set(0, 0, 0, 1);
        player = new Entity(world);
    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        mapRenderer.setMap(this.assetManager.get("Water.tmx", TiledMap.class));

        map = new Map(this.assetManager.get("Water.tmx", TiledMap.class), world);
        map.parseCollisionLayer();
        System.out.println(map.getStartPoint());
        camera.setToOrtho(false, w, h);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //viewport.apply(true);
        //camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.F)){
            camera.translate(0,-10);
        }
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        player.update(camera);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
