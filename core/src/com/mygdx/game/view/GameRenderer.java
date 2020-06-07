package com.mygdx.game.view;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.map.IsometricOrderRenderer;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapListener;

import static com.mygdx.game.Constants.UNIT_SCALE;

public class GameRenderer implements Disposable, MapListener {
    public static final String TAG = GameRenderer.class.getSimpleName();
    private FitViewport viewport;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private final IsometricOrderRenderer mapRenderer;
    public final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private final RayHandler rayHandler;

    public GameRenderer(GameContext context) {
        assetManager = context.getAssetManager();
        viewport = context.getViewport();
        camera = context.getCamera();
        batch = context.getBatch();
        mapRenderer = new IsometricOrderRenderer(null, UNIT_SCALE, batch);
        context.getMapManager().addMapListener(this);
        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.SHAPE_STATIC.set(0, 0, 0, 1);
        world = context.getWorld();
        rayHandler = context.getRayHandler();
    }

    public void render(final float alpha) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (mapRenderer.getMap() != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        }

        box2DDebugRenderer.render(world, camera.combined);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    @Override
    public void dispose() {
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
        mapRenderer.dispose();
    }

    @Override
    public void mapChange(Map map) {
        mapRenderer.setMap(map.getTiledMap());
    }

    public void addEntity(Entity entity) {
        mapRenderer.addEntity(entity);
    }

    public void removeEntity(Entity entity) {
        mapRenderer.removeEntity(entity);
    }

    public Entity getPlayerEntity() {
        return mapRenderer.getPlayer();
    }
}
