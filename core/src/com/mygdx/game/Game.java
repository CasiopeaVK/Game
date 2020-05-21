package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Game extends ApplicationAdapter{
    SpriteBatch batch;
    Texture img;
    CameraViewProcessor cameraViewProcessor;

    @Override
    public void create() {
        TiledMap tiledMap = new TmxMapLoader().load("Water.tmx");
        cameraViewProcessor = new CameraViewProcessor(tiledMap);
    }

    @Override
    public void render() {
        cameraViewProcessor.process();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

}
