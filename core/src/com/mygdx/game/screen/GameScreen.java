package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.npc.TestNPC;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.map.Map;

import com.mygdx.game.quest.QuestTable;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.Time.TimeTable;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;

public class GameScreen extends AbstractScreen {

   // private final IsometricOrderRenderer mapRenderer;
    private OrthographicCamera camera;

    private World world;
    private TiledMap tiledMap;
    private Map map;
    private GameRenderer gameRenderer;
    SmartStage stage;
    Player player;
    TestNPC testNPC;
    GameUI gameUI;
    public GameScreen(final GameContext context) {
        super(context);
        world = context.getWorld();
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, world);
        camera = context.getCamera();
        stage = new SmartStage();
        player = new Player(world, map, camera,"hero/hero.png");
        testNPC = new TestNPC(world, map, camera,"hero/durislav.png");
        stage.addEntity(player);
        stage.addEntity(testNPC);

        gameRenderer = context.getGameRenderer();
        gameRenderer.addEntity(player);
        gameRenderer.addEntity(testNPC);
        Gdx.input.setInputProcessor(stage);

        gameUI = new GameUI();
    }

    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        map.parseCollisionLayer();
        gameRenderer.mapChange(map);
        camera.setToOrtho(false, w, h);
        camera.update();

        stage.addActor(gameUI);
    }

    @Override
    public void render(float delta) {
        TimeManager.getTime();
        camera.update();
        gameRenderer.render(1f);
        gameUI.updateTime();
        gameUI.setCurrentCell();
        testItems();
        stage.update();
    }

    //TODO remove
    private void testItems(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.E))
            gameUI.addItem(GameItems.DIRT.getItem());
        if(Gdx.input.isKeyJustPressed(Input.Keys.R))
            gameUI.addItem(GameItems.SPOON.getItem());

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
