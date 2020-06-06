package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameContext;
import com.mygdx.game.Time.TimeLoop;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.npc.EvilNPC;
import com.mygdx.game.entities.npc.MovementDelayManager;
import com.mygdx.game.entities.npc.Npc;
import com.mygdx.game.entities.npc.NpcBuilder;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;

import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.map.ObjectsRenderer;
import com.mygdx.game.quest.GenerateQuests;
import com.mygdx.game.quest.QuestTable;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.screenUI.NoticedUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;

    private World world;
    private TiledMap tiledMap;
    private Map map;
    private GameRenderer gameRenderer;
    SmartStage stage;
    Player player;
    List<Npc> npcList;
    GameUI gameUI;
    PickUpSensor sensor;
    EvilNPC evilNPC;
    TimeLoop timeLoop;

    public GameScreen(final GameContext context) {
        super(context);
        sensor = new PickUpSensor();
        context.setSensor(sensor);
        world = context.getWorld();
        world.setContactListener(sensor);
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, context);
        context.setMap(map);
        camera = context.getCamera();
        stage = new SmartStage();
        QuestTable questTable = GenerateQuests.generateQuests(context);
        context.setStage(stage);
        gameUI = new GameUI(questTable);
        context.setGameUI(gameUI);
        stage.setGameUI(gameUI);

        gameRenderer = context.getGameRenderer();
        ItemBuilder itemBuilder = new ItemBuilder(world, camera, gameRenderer);
        context.setItemBuilder(itemBuilder);

        player = new Player(context, map, "hero/hero.png", gameUI, sensor);
        player.getInventory().setItemBuilder(itemBuilder);
        context.setPlayer(player);
        stage.setPlayer(player);
        evilNPC = new EvilNPC("testEvilNpc", context, map, "hero/hero.png");
        npcList = Arrays.asList(
                new Npc("englishNeighbour", world, map, camera, "hero/hero.png"),
                new Npc("jibaNeighbour", world, map, camera, "hero/hero.png"),
                new Npc("napNeighbour", world, map, camera, "hero/hero.png"),
                new Npc("nurse", world, map, camera, "hero/hero.png"),
                new Npc("cooker", world, map, camera, "hero/hero.png", new MovementDelayManager() {
                    @Override
                    public boolean preMovePredicate() {
                        return false;
                    }

                    @Override
                    public boolean postMovePredicate() {
                        return false;
                    }
                }),
                NpcBuilder.setEndStartDelay(evilNPC, 5000, 5000));
        gameRenderer = context.getGameRenderer();

        addEntity(player);

        player.addItem(itemBuilder.createItem(GameItems.SPOON));
        npcList.stream().forEach(this::addEntity);
        ObjectsRenderer.renderEnvironment(map, stage, player, context, questTable.getQuestLine());


        timeLoop = new TimeLoop(npcList, context);
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
        evilNPC.initializeNoticedUI();
    }

    @Override
    public void render(float delta) {
        camera.update();
        timeLoop.processTimeChange();
        gameRenderer.render(1f);
        stage.update();
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
        this.dispose();
    }
}