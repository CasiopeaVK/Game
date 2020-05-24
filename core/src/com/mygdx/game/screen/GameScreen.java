package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.npc.TestNPC;
import com.mygdx.game.map.Map;
import com.mygdx.game.quest.GenerateQuests;

import com.mygdx.game.quest.QuestTable;
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
    QuestTable questTable;
    Player player;

    public GameScreen(final GameContext context) {
        super(context);
        world = context.getWorld();
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, world);
        camera = context.getCamera();
        stage = new SmartStage();
        player = new Player(world, map, camera,"hero/durislav.png");
        TestNPC testNPC = new TestNPC(world, map, camera,"hero/durislav.png");
        stage.addEntity(player);
        stage.addEntity(testNPC);
        gameRenderer = context.getGameRenderer();
        gameRenderer.addEntity(player);
        gameRenderer.addEntity(testNPC);
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
        allUiRender();


    }

    @Override
    public void render(float delta) {
        camera.update();
        gameRenderer.render(1f);
        stage.update();
        questTestListener();
    }

    //Method for render all UI-elements
    private void allUiRender(){
        addQuestTable();
    }

    //TODO remove
    private void questTestListener() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            questTable.updateQuest();
        }
    }

    //Render quests table in UI
    private void addQuestTable() {
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
