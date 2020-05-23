package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.IsometricOrderRenderer;
import com.mygdx.game.map.Map;
import com.mygdx.game.quest.GenerateQuests;
import com.mygdx.game.quest.Quest;
import com.mygdx.game.quest.QuestLine;
import com.mygdx.game.quest.QuestTable;

public class GameScreen extends AbstractScreen {

    private final IsometricOrderRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Player player;
    private World world;
    private TiledMap tiledMap;
    private Map map;

    Stage stage;
    QuestTable questTable;

    public GameScreen(final GameContext context) {
        super(context);
        world = new World(new Vector2(0, 0), true);
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, world);
        player = new Player(world, map, "hero/durislav.png");
        mapRenderer = new IsometricOrderRenderer(tiledMap, Constants.UNIT_SCALE, context.getSpriteBatch());
        mapRenderer.addSprite(player.getSprite());

        OrthogonalTiledMapRenderer render = new OrthogonalTiledMapRenderer(tiledMap);

        camera = context.getCamera();

        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.SHAPE_STATIC.set(0, 0, 0, 1);


    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        mapRenderer.setMap(this.assetManager.get("Water.tmx", TiledMap.class));

        map.parseCollisionLayer();
        camera.setToOrtho(false, w, h);
        camera.update();
        questTest();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            camera.translate(0, -10);
        }
        camera.update();

        mapRenderer.setView(camera);
        player.update(camera);
        mapRenderer.render();


        debugRenderer.render(world, camera.combined);

        //TODO remove
        stage.act();
        stage.draw();
    }

    //TODO remove
    private void questTestListener() {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            questTable.updateQuest();
        }
    }

    //TODO remove
    private void questTest() {
        stage = new Stage();

        questTable = GenerateQuests.generateQuests();
        questTable.left().top();
        stage.addActor(questTable);
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
