package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.map.Map;

public class EvilNPC extends Npc {

    private float deadZoneRadius = 50f;
    private float deadZoneAngle = 90f;
    private Vector2[] vertices;
    private FixtureDef deadZoneFixture;


    public EvilNPC(String name, World world, Map map, Camera camera, String texturePath, String pathName) {
        super(name, world, map, camera, texturePath, pathName);
        initializeDeadZone();
    }

    private void initializeDeadZone() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);
        PolygonShape sector = new PolygonShape();
        createDeadZoneVertices();
        sector.set(vertices);
        deadZoneFixture = new FixtureDef();
        deadZoneFixture.shape = sector;
        deadZoneFixture.density = 1f;
        deadZoneFixture.isSensor = true;
        body.createFixture(deadZoneFixture).setUserData(this);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }


    public void updateDeadZonePoints() {
        System.out.println(xFactor + " " +  yFactor);
//        for (Vector2 vertex : vertices) {
//            vertex.x *= xFactor;
//            vertex.y *= yFactor;
//        }
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        body.resetMassData();
        body.createFixture(shape, 1f);
    }

    public void createDeadZoneVertices() {
        int STEPS = 5;
        vertices = new Vector2[STEPS + 2];
        vertices[0] = new Vector2(0, 0);
        int curVertex = 1;
        for (float angle = -deadZoneAngle / 2; angle <= deadZoneAngle / 2; angle += deadZoneAngle / STEPS) {
            vertices[curVertex] = new Vector2((float) Math.cos(angle) * deadZoneRadius, (float) Math.sin(angle) * deadZoneRadius);
            curVertex++;
        }
    }

    @Override
    public void update() {
        super.update();
        updateDeadZonePoints();
    }
}
