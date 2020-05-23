package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
    protected SpriteBatch batch;
    protected Sprite sprite;
    protected Texture img;
    protected Body body;
    protected World world;

    public Entity(World world, String texturePath) {
        this.world = world;
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

    abstract public void update(Camera camera); /*{

        float speedX;
        float speedY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -200;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 200;
        } else {
            speedX = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = -200;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = 200;
        } else {
            speedY = 0;
        }
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.TwoDToIso(new Vector2(speedX, speedY)));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 2);
        camera.position.set(body.getPosition().x, body.getPosition().y, 0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }*/
}

