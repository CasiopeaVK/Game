package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
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
    private final IsometricTiledMapRenderer mapRenderer;
    private final GLProfiler profiler;
    public final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private Player player;

    public GameRenderer(GameContext context) {
        assetManager = context.getAssetManager();
        viewport = context.getScreenViewport();
        camera = context.getCamera();
        batch = context.getSpriteBatch();
        mapRenderer = new IsometricTiledMapRenderer(null, UNIT_SCALE, batch);
        context.getMapManager().addMapListener(this);
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        if (profiler.isEnabled()) {
            box2DDebugRenderer = new Box2DDebugRenderer();
            box2DDebugRenderer.SHAPE_STATIC.set(0, 0, 0, 1);
            world = context.getWorld();
        } else {
            box2DDebugRenderer = null;
            world = null;
        }
    }

    public void render(final float alpha) {
        Gdx.gl.glClearColor(0, 1, 0, alpha);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println(camera.position);
        viewport.apply(false);
        if (mapRenderer.getMap() != null) {
            camera.update();
            mapRenderer.setView(camera);
            player.update(camera);
            mapRenderer.render();
        }

        if (profiler.isEnabled()) {
            Gdx.app.debug(TAG, "Bindings: " + profiler.getTextureBindings());
            Gdx.app.debug(TAG, "Drawcalls: " + profiler.getDrawCalls());
            profiler.reset();

            box2DDebugRenderer.render(world, camera.combined);
        }
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
