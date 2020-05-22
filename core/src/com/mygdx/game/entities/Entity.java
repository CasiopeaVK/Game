package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.utils.IsoUtils;

public class Entity extends BodyDef {
    protected SpriteBatch batch;
    protected Sprite sprite;
    protected Texture img;
    protected Body body;
    protected World world;

    public Entity(World world) {
        this.world = world;
        initialize();
    }

    private void initialize() {
        batch = new SpriteBatch();
        img = new Texture("hero/durislav.png");
        sprite = new Sprite(img);
        sprite.setScale(0.6f, 0.6f);
        sprite.setPosition(300, 300);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());


        body = world.createBody(bodyDef);

        Vector2[] vertices = {
                new Vector2(-sprite.getWidth() / 2, 0),
                new Vector2(0, sprite.getWidth() / 4),
                new Vector2(sprite.getWidth() / 2, 0),
                new Vector2(0, -sprite.getWidth() / 4),
                new Vector2(-sprite.getWidth() / 2, 0),
        };


        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
    }

    public void update(Camera camera) {

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
    }
}

