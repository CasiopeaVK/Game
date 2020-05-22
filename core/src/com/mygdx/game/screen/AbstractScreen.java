package com.mygdx.game.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Game;

public abstract class AbstractScreen implements Screen {

    protected final Game context;
    protected final FitViewport viewport;
    protected AssetManager assetManager;

    public AbstractScreen(final Game context) {
        this.context = context;
        viewport = context.getScreenViewport();
        assetManager = context.getAssetManager();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}