package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor {
    protected SpriteBatch batch;
    protected Camera camera;
    protected Sprite sprite;
    protected Texture img;
    protected Body body;
    protected World world;

    public Entity(World world, Camera camera, String texturePath) {
        this.world = world;
        this.camera = camera;
        initialize(texturePath);
    }

    private void initialize(String texturePath) {
        batch = new SpriteBatch();
        img = new Texture(texturePath);
        sprite = new Sprite(img);

    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public Sprite getSprite() {
        return sprite;
    }

    abstract public void update(Camera camera);
}

