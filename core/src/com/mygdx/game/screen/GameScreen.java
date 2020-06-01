package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Tunel;
import com.mygdx.game.entities.npc.EvilNPC;
import com.mygdx.game.entities.npc.MovementDelayManager;
import com.mygdx.game.entities.npc.Npc;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;

import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.screenUI.NoticedUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;

    private World world;
    private TiledMap tiledMap;
    private Map map;
    private GameRenderer gameRenderer;
    SmartStage stage;
    Player player;
    Npc npc;
    GameUI gameUI;
    Item item;
    PickUpSensor sensor;
    EvilNPC evilNPC;

    //TODO remove
    NoticedUI noticedUI;

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
        //
        noticedUI = new NoticedUI();
        gameRenderer = context.getGameRenderer();
        ItemBuilder itemBuilder = new ItemBuilder(world, camera, gameRenderer);

        player = new Player(context, map, "hero/hero.png", gameUI, sensor);
        npc = new Npc("testNpc", world, map, camera, "hero/hero.png", "testNpc");
        evilNPC = new EvilNPC("testEvilNpc", context, map,  "hero/hero.png", "testEvilNpc");
        evilNPC.setMovementDelayManager(new MovementDelayManager() {
            int delay = 5 * 1000;
            long limit = System.currentTimeMillis() + delay;

            @Override
            public boolean preMovePredicate() {
                if ((evilNPC.getPath().isFirst() || evilNPC.getPath().isLast()) && System.currentTimeMillis() < limit) {
                    return false;
                }
                limit = System.currentTimeMillis() + delay;
                return true;

            }

            @Override
            public boolean postMovePredicate() {
                return false;
            }
        });
        item = itemBuilder.createItem(GameItems.DIRT);

        Tunel tunel = new Tunel(world, camera, "dirt.png", gameUI.getInventory(), itemBuilder);
        gameRenderer = context.getGameRenderer();

        addEntity(player);
        addEntity(npc);
        addEntity(item);
        addEntity(evilNPC);
        addEntity(tunel);
        Gdx.input.setInputProcessor(stage);
    }

    private void addEntity(Entity entity) {
        stage.addEntity(entity);
        gameRenderer.addEntity(entity);
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
        //TODO remove
        stage.addActor(noticedUI);
        evilNPC.initializeNoticedUI();
    }

    @Override
    public void render(float delta) {
        TimeManager.getTime();
        camera.update();
        gameRenderer.render(1f);
        gameUI.updateTime();
        gameUI.setCurrentCell();
        stage.update();
        gameUI.renderSelectedItem(stage);
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