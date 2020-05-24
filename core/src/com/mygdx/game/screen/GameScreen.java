package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.npc.TestNPC;
import com.mygdx.game.map.Map;
import com.mygdx.game.quest.GenerateQuests;

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
    QuestTable questTable;
    TimeTable timeTable;
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
        player = new Player(world, map, camera,"hero/durislav.png");
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

        /*stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector3 v3 = camera.unproject(new Vector3(x,y,0));
                System.out.println("click"+v3);
                testNPC.getSprite().setPosition(v3.x,-v3.y);
                super.clicked(event, x, y);
            }
        });*/
//        allUiRender();
        stage.addActor(gameUI);
    }

    @Override
    public void render(float delta) {
        TimeManager.getTime();
        camera.update();
        gameRenderer.render(1f);
        gameUI.updateTime();
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

    }
}
