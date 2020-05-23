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
import com.mygdx.game.quest.Quest;
import com.mygdx.game.quest.QuestLine;
import com.mygdx.game.quest.QuestTable;
import com.mygdx.game.view.GameRenderer;

public class GameScreen extends AbstractScreen {

   // private final IsometricOrderRenderer mapRenderer;
    private OrthographicCamera camera;

    private World world;
    private TiledMap tiledMap;
    private Map map;
    private GameRenderer gameRenderer;
    Stage stage;
    QuestTable questTable;

    public GameScreen(final GameContext context) {
        super(context);
        world = context.getWorld();
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, world);
        gameRenderer.setPlayer(new Player(world, map, "hero/durislav.png"));
//        mapRenderer = new IsometricOrderRenderer(tiledMap, Constants.UNIT_SCALE, context.getSpriteBatch());
//        mapRenderer.addSprite(player.getSprite());
        gameRenderer = context.getGameRenderer();

//        OrthogonalTiledMapRenderer render = new OrthogonalTiledMapRenderer(tiledMap);

        camera = context.getCamera();
    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        gameRenderer.mapChange(map);
//        mapRenderer.setMap(this.assetManager.get("Water.tmx", TiledMap.class));
        map.parseCollisionLayer();
        camera.setToOrtho(false, w, h);
        camera.update();
        questTest();

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 1, 0, 1);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
//            camera.translate(0, -10);
//        }
//        mapRenderer.setView(camera);

//        mapRenderer.render();
        gameRenderer.render(1f);
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
        QuestLine questLine = new QuestLine("Sample quest");
        questLine.addQuest(new Quest("Sample quest", "Something big description.\n All is usual, Vlad soset", false));
        questLine.addQuest(new Quest("Sample quest2", "Something big description.\n All is usual, Vlad soset*2", true));

        questTable = new QuestTable(questLine);
//        questTable.setX(stage.getWidth() - 170);
//        questTable.setY(stage.getHeight() - 100);
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
