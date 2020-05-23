package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.Map;
import com.mygdx.game.quest.GenerateQuests;
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
    Player player;

    public GameScreen(final GameContext context) {
        super(context);
        world = context.getWorld();
        tiledMap = this.assetManager.get("Water.tmx", TiledMap.class);
        map = new Map(tiledMap, world);

        player = new Player(world, map, "hero/durislav.png");
        gameRenderer = context.getGameRenderer();
        gameRenderer.addSprite(player.getSprite());
        camera = context.getCamera();


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
        player.update(camera);


        stage.act();
        stage.draw();
    }

    //Method for render all UI-elements
    private void allUiRender(){
        stage = new Stage();
        addQuestTable();
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
        questLine.addQuest(new Quest("Sample quest", "Something big description.\n All is usual, Stas soset", false));
        questLine.addQuest(new Quest("Sample quest2", "Something big description.\n All is usual, Stas soset*2", true));

        questTable = new QuestTable(questLine);
        questTable.setX(stage.getWidth() - 170);
        questTable.setY(stage.getHeight() - 100);
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
