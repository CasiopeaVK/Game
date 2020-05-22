package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.quest.Quest;
import com.mygdx.game.quest.QuestLine;
import com.mygdx.game.quest.QuestTable;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.ScreenType;
import java.util.EnumMap;

public class GameContext extends Game {
    private static final String TAG = GameContext.class.getSimpleName();
    private EnumMap<ScreenType, AbstractScreen> screenCache;

    private FitViewport viewport;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;



    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Set first screen
        camera = new OrthographicCamera();
        viewport = new FitViewport(16, 9, camera);
        screenCache = new EnumMap<ScreenType, AbstractScreen>(ScreenType.class);
        batch = new SpriteBatch();

        //Initialize asset manager
        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));

        setScreen(ScreenType.MENU);
    }

    public FitViewport getScreenViewport() {
        return viewport;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getSpriteBatch() {
        return this.batch;
    }

    public OrthographicCamera getCamera(){
        return camera;
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


}

