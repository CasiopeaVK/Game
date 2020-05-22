package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class Game extends ApplicationAdapter implements InputProcessor{
    SpriteBatch batch;
    Texture img;
    float x = 0;
    float y = 0;
    TiledMap tiledMap;
    OrthographicCamera camera;
    IsometricTiledMapRenderer tiledMapRenderer;

    @Override
    public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		tiledMap = new TmxMapLoader().load("Water.tmx");
		tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap, 1/4F);
		Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
    	return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
