package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class CameraViewProcessor {
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private IsometricTiledMapRenderer tiledMapRenderer;
    private Consumer<Void> processFunction = new Consumer<Void>() {
        @Override
        public void accept(Void aVoid) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                camera.translate(-10,0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                camera.translate(10,0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                camera.translate(0,10);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                camera.translate(0,-10);
            }
        }
    };

    public CameraViewProcessor(TiledMap tiledMap){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        this.tiledMap = tiledMap;
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap, 1/4F);
    }

    public void process(){
        processFunction.accept(null);
    }
}
