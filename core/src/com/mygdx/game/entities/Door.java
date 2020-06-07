package com.mygdx.game.entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.map.Map;
import lombok.Getter;


public class Door {

    @Getter
    private Body door;
    @Getter
    private Body doorTrigger;
    @Getter
    private boolean isOpen = false;

    private SingleDoor leftDoor;
    private SingleDoor rightDoor;
    private Map map;
    private World world;

    public Door(Map map, SingleDoor leftDoor, SingleDoor rightDoor, MapObject doorObj, MapObject triggerObj) {

        this.leftDoor = leftDoor;
        this.rightDoor = rightDoor;
        this.map = map;
        this.world = leftDoor.getWorld();
        door = initializeObject(doorObj, BodyDef.BodyType.StaticBody, false);
        doorTrigger = initializeObject(triggerObj, BodyDef.BodyType.KinematicBody, true);
    }

    private Body initializeObject(MapObject mapObject, BodyDef.BodyType type, boolean isTrigger) {

        if (mapObject instanceof PolygonMapObject) {
            Shape shape = map.getPolygon((PolygonMapObject) mapObject);

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = type;
            Body body = world.createBody(bodyDef);
            FixtureDef fixture = new FixtureDef();
            fixture.shape = shape;
            fixture.isSensor = isTrigger;
            body.createFixture(fixture).setUserData(this);

            shape.dispose();
            return body;
        }
        return null;
    }

    public void open() {
        door.getFixtureList().get(0).setSensor(true);
        leftDoor.open();
        rightDoor.open();
        isOpen = true;
    }

    public void close() {
        door.getFixtureList().get(0).setSensor(false);
        leftDoor.close();
        rightDoor.close();
        isOpen = false;
    }
}