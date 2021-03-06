package com.mygdx.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.npc.Npc;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapManager;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.ScreenType;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class GameContext extends Game {
    private static final String TAG = GameContext.class.getSimpleName();
    private EnumMap<ScreenType, AbstractScreen> screenCache;

    @Getter
    private FitViewport viewport;
    @Getter
    private AssetManager assetManager;
    @Getter
    private OrthographicCamera camera;
    @Getter @Setter
    private SpriteBatch batch;
    private Skin skin;
    @Getter
    private World world;
    @Getter
    private MapManager mapManager;
    @Getter
    private GameRenderer gameRenderer;
    @Getter
    private RayHandler rayHandler;
    @Getter @Setter
    private ItemBuilder itemBuilder;
    @Getter @Setter
    private Player player;
    @Getter @Setter
    private SmartStage stage;
    @Getter @Setter
    private Map map;
    @Getter @Setter
    PickUpSensor sensor;
    @Getter @Setter
    GameUI gameUI;
    @Getter @Setter
    List<Npc> npcList;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Initialize asset manager
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));


        //Set first screen
        camera = new OrthographicCamera();
        camera.far = 550;
        viewport = new FitViewport(16, 9, camera);
        screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        mapManager = new MapManager(this);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0, 0, 0, 0.8f);
        gameRenderer = new GameRenderer(this);


        initializeSkin();
        setScreen(ScreenType.MENU);
    }

    public void setScreen(final ScreenType screenType) {
        final AbstractScreen screen = screenCache.get(screenType);
        if (screen == null) {
            try {
                Gdx.app.debug(TAG, "Creating new screen...");
                final AbstractScreen newScreen = (AbstractScreen) ClassReflection.getConstructor(screenType.getScreenClass(), GameContext.class)
                        .newInstance(this);
                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
            } catch (ReflectionException ex) {
                throw new GdxRuntimeException("Screen " + screenType + " could not be created!");
            }
        } else {
            Gdx.app.debug(TAG, "Switching screen...");
            setScreen(screen);
        }
    }

    public void initializeSkin() {
        final ObjectMap<String, Object> resources = new ObjectMap<String, Object>();
        final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font.ttf"));
        final FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        final int[] sizesToCreate = {16, 20, 26, 32};
        for (int size : sizesToCreate) {
            fontParameter.size = size;
            resources.put("font_" + size, fontGenerator.generateFont(fontParameter));

        }
        fontGenerator.dispose();

        final SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter("ui/ui.atlas", resources);
        assetManager.load("ui/hud.json", Skin.class, skinParameter);
        assetManager.finishLoading();
        skin = assetManager.get("ui/hud.json", Skin.class);
    }
}

