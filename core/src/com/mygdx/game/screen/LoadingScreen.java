package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.GameContext;


public class LoadingScreen extends AbstractScreen {

    public LoadingScreen(final GameContext context){
        super(context);
    }
    @Override
    public void show() {
        this.assetManager.load("Water.tmx", TiledMap.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Check while map will load
        if(this.assetManager.update()){
            context.setScreen(ScreenType.GAME);
        }
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
