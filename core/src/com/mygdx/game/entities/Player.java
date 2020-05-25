package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

import static com.mygdx.game.Constants.PLAYER_SPEED;

public class Player extends AnimatedEntity {

    public Player(World world, Map map, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        initialize(map);
    }

    private void initialize(Map map) {
        sprite.setScale(spriteScale);
        calculateSpawnPosition(map, "spawn");
        initCharacterBody(BodyDef.BodyType.DynamicBody);
    }

    public void update() {
        update(this::handleClickedButtons);
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
}