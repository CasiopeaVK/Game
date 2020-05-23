package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameContext;
import com.mygdx.game.screenUI.LoadingUI;


public class LoadingScreen extends AbstractScreen {

    LoadingUI loadingUI;
    Stage stage;

    public LoadingScreen(final GameContext context) {
        super(context);
        this.assetManager.load("Water.tmx", TiledMap.class);
    }

    @Override
    public void show() {
        loadingUI = new LoadingUI();
        stage = new Stage();
        stage.addActor(loadingUI);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assetManager.getProgress();

//        Check while map will load
        if (this.assetManager.update()) {
            context.setScreen(ScreenType.GAME);
        }
        stage.act();
        stage.draw();
        loadingUI.setProgressBar(assetManager.getProgress());
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
