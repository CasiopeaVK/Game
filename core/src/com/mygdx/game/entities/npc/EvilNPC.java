package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.map.Map;

public class EvilNPC extends Npc {

    private float deadZoneRadius = 200f;
    private float deadZoneAngle = (float) (Math.PI / 2);
    private Vector2[] vertices;
    private FixtureDef deadZoneFixture;
    private Body deadZoneBody;


    public EvilNPC(String name, World world, Map map, Camera camera, String texturePath, String pathName) {
        super(name, world, map, camera, texturePath, pathName);
        initializeDeadZone();
    }

    private void initializeDeadZone() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        deadZoneBody = world.createBody(bodyDef);
        PolygonShape sector = new PolygonShape();
        createDeadZoneVertices();
        sector.set(vertices);
        deadZoneFixture = new FixtureDef();
        deadZoneFixture.shape = sector;
        deadZoneFixture.isSensor = true;
        deadZoneBody.createFixture(deadZoneFixture).setUserData(this);
    }


    public void updateDeadZone() {
        deadZoneBody.setTransform(body.getPosition(), (float) rotateAngle);
    }

    public void createDeadZoneVertices() {
        int STEPS = 5;
        vertices = new Vector2[STEPS + 3];
        vertices[0] = new Vector2(0f, 0f);
        float angle = -deadZoneAngle / 2;
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new Vector2((float) Math.cos(angle) * deadZoneRadius, (float) Math.sin(angle) * deadZoneRadius);
            angle += deadZoneAngle / STEPS;
        }
        vertices[STEPS + 2] = new Vector2(0f, 0f);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    @Override
    public void update() {
        super.update();
        updateDeadZone();
    }

    public void triggerNpc() {
        System.out.println("You dead");
    }
}
