package com.mygdx.game.entities.npc;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

public class CustomEvilNpc extends EvilNPC {

    private Map map;

    public CustomEvilNpc(String name, GameContext context, Map map, String texturePath) {
        super(name, texturePath, context, map);
        this.map = map;
        this.movementDelayManager = new MovementDelayManager() {
            @Override
            public boolean preMovePredicate() {
                return false;
            }

            @Override
            public boolean postMovePredicate() {
                return false;
            }
        };

        initializeDeadZone();
    }

    public void initializeDeadZone() {
        MapObject customTrigger = map.getObject("TriggerArea", getName() + "Area");
        if (customTrigger instanceof PolygonMapObject) {
            initializeDeadZone(createDeadZoneVertices((PolygonMapObject) customTrigger));
        }
    }

    public Vector2[] createDeadZoneVertices(PolygonMapObject object) {
        float[] verticesInRow = object.getPolygon().getTransformedVertices();
        Vector2[] vertices = new Vector2[verticesInRow.length / 2];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = IsoUtils.IsoTo2d(new Vector2(verticesInRow[2 * i], verticesInRow[2 * i + 1]));
            System.out.println(vertices[i].toString());
        }
        return vertices;
    }

    @Override
    public void initializeDeadZone(Vector2[] vertices) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;

        this.deadZoneBody = world.createBody(bodyDef);
        PolygonShape sector = new PolygonShape();
        sector.set(vertices);
        deadZoneFixture = new FixtureDef();
        deadZoneFixture.shape = sector;
        deadZoneFixture.isSensor = true;
        deadZoneBody.createFixture(deadZoneFixture).setUserData(this);
    }

    @Override
    public void update() {
        update(Constants.PLAYER_LOW_SPEED);
        checkTrigger();
    }
}
