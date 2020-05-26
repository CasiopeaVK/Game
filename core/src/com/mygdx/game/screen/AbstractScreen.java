package com.mygdx.game.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameContext;


public abstract class AbstractScreen implements Screen {

    protected final GameContext context;
    protected final FitViewport viewport;
    protected AssetManager assetManager;
    protected final RayHandler rayhandler;

    public AbstractScreen(final GameContext context) {
        this.context = context;
        viewport = context.getScreenViewport();
        assetManager = context.getAssetManager();
        rayhandler = context.getRayHandler();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        rayhandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }
}