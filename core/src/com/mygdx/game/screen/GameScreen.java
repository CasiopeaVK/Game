package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.npc.TestNPC;
import com.mygdx.game.input.GameKeys;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.PickUpItem;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;

import com.mygdx.game.quest.QuestTable;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.Time.TimeTable;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;

    private World world;
    private TiledMap tiledMap;
    private Map map;
    private GameRenderer gameRenderer;
    SmartStage stage;
    Player player;
    TestNPC testNPC;
    GameUI gameUI;
    PickUpItem item;
    PickUpSensor sensor;

    public GameScreen(final GameContext context) {
        super(context);
        sensor = new PickUpSensor();
        world = context.getWorld();
        world.setContactListener(sensor);
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, context);
        camera = context.getCamera();
        stage = new SmartStage();
        gameUI = new GameUI();

        player = new Player(context, map,"hero/hero.png", gameUI, sensor);
        testNPC = new TestNPC(world, map, camera,"hero/hero.png");
        item = new PickUpItem(world, camera, "dirt.png", GameItems.DIRT.getItem());
        stage.addEntity(player);
        stage.addEntity(testNPC);
        stage.addEntity(item);

        gameRenderer = context.getGameRenderer();
        gameRenderer.addEntity(player);
        gameRenderer.addEntity(testNPC);
        gameRenderer.addEntity(item);
        Gdx.input.setInputProcessor(stage);

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
        //testItems();
        stage.update();
        gameUI.renderSelectedItem(stage);
    }

    //TODO remove
    private void testItems(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.E))
            gameUI.addItem(GameItems.DIRT.getItem());
        if(Gdx.input.isKeyJustPressed(Input.Keys.R))
            gameUI.addItem(GameItems.SPOON.getItem());
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q))
            gameUI.showInventory(!gameUI.isInventoryShow());

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
