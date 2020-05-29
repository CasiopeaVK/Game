package com.mygdx.game.entities;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.GameContext;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.utils.IsoUtils;

import static com.mygdx.game.Constants.*;

public class Player extends AnimatedEntity {
    public Light light;
    public GameUI gameUI;
    public PickUpSensor sensor;

    public Player(GameContext context, Map map, String texturePath, GameUI gameUI, PickUpSensor sensor) {
        super(context.getWorld(), context.getCamera(), texturePath);
        initialize(map, context);
        this.gameUI = gameUI;
        this.sensor = sensor;
    }

    private void initialize(Map map, GameContext context) {
        sprite.setScale(spriteScale);
        calculateSpawnPosition(map, "spawn");
        initCharacterBody(BodyDef.BodyType.DynamicBody);
    }

    public void update() {
        update(this::handleClickedButtons);
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.TwoDToIso(new Vector2(xFactor * PLAYER_SPEED * Gdx.graphics.getDeltaTime(), -yFactor * PLAYER_SPEED * Gdx.graphics.getDeltaTime())));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2 - 2, body.getPosition().y - sprite.getWidth() / 2 + 10);
        camera.position.set(body.getPosition().x, body.getPosition().y, 0);
    }


    private void handleClickedButtons() {
        if (xFactor == 0 && yFactor == 0) {
            firstStep = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            xFactor = -COSPI4;
            yFactor = SINPI4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
            xFactor = COSPI4;
            yFactor = SINPI4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            xFactor = COSPI4;
            yFactor = -SINPI4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
            xFactor = -COSPI4;
            yFactor = -SINPI4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            xFactor = 0;
            yFactor = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xFactor = -1;
            yFactor = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            xFactor = 0;
            yFactor = -1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xFactor = 1;
            yFactor = 0;
        } else {
            yFactor = 0;
            xFactor = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if (sensor.isTriggered()) {
                gameUI.getInventory().addItem(sensor.getItem());
                sensor.getItem().hideItem();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if (!gameUI.getInventory().isSelectedEmpty()) {
                gameUI.getInventory().getSellectedCell().getItem().createPickUpItem();
                gameUI.getInventory().getSellectedCell().setItem(null);
            }
        }

    }

    private void addLightAroundPlayer(GameContext context) {
        light = new PointLight(context.getRayHandler(), 64, new Color(1, 1, 1, 1f), 120, body.getPosition().x, body.getPosition().y);
        light.attachToBody(body);
        light.setIgnoreAttachedBody(true);
    }
}