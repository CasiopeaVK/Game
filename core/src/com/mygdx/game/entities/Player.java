package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;
import sun.java2d.pipe.SpanClipRenderer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mygdx.game.Constants.PLAYER_SPEED;

public class Player extends AnimatedEntity {

    public Player(World world, Map map, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        initialize(map);
    }

    private void initialize(Map map) {
        calculateSpawnPosition(map, "spawn");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);


        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(generatePlayerVerticles());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);
    }


    public void update() {
        if (framesCounter == 10) {
            handleClickedButtons();
            handleMovement();
            firstStep = false;
            framesCounter = 0;
        } else
            framesCounter++;
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.TwoDToIso(new Vector2(xFactor * PLAYER_SPEED, yFactor * PLAYER_SPEED)));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 2);
        camera.position.set(body.getPosition().x, body.getPosition().y, 0);
    }

    private void handleClickedButtons() {
        if (xFactor == 0 && yFactor == 0) {
            firstStep = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xFactor = -1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xFactor = 1;
        } else {
            xFactor = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yFactor = -1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yFactor = 1;
        } else {
            yFactor = 0;
        }
    }

    private Vector2[] generatePlayerVerticles() {
        int STEPS = 7;
        Vector2[] vertices = new Vector2[STEPS + 1];
        for (int i = 0; i < STEPS; i++) {
            float t = (float) (i * 2 * Math.PI) / STEPS;
            vertices[i] = new Vector2(sprite.getWidth() / 3 * (float) Math.cos(t), sprite.getWidth() / 6 * (float) Math.sin(t));
        }
        vertices[STEPS] = new Vector2(sprite.getWidth() / 3 * (float) Math.cos(0), 0);
        return vertices;
    }
}
