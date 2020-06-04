package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.ObjectType;
import com.mygdx.game.map.Map;
import lombok.Getter;


public class Door extends Actor {

    @Getter
    Body door;
    Body doorTrigger;
    Map map;
    World world;
    Camera camera;

    private boolean isOpen = false;
    private TiledMapTile openDoorTile;
    private TiledMapTile closedDoorTile;


    public Door(Map map, World world, Camera camera) {
        this.map = map;
        this.world = world;
        this.camera = camera;
        door = initializeObject(map.getObject("Doors", "door"), BodyDef.BodyType.StaticBody, false);
        doorTrigger = initializeObject(map.getObject("Doors", "trigger"), BodyDef.BodyType.KinematicBody, true);
        initializeAnimation();
    }

    private void initializeAnimation() {
        TiledMapTileSet tileSet = this.map.getTiledMap().getTileSets().getTileSet("Water");
        for (TiledMapTile tile : tileSet) {

            Object properties = tile.getProperties().get("isOpen");
            if (properties != null) {

            }
        }

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

    public boolean isOpen() {
        return door.getFixtureList().get(0).isSensor();
    }

    public void open() {
        System.out.println("OPEN");
        door.getFixtureList().get(0).setSensor(true);
    }

    public void close() {
        System.out.println("CLOSE");
        door.getFixtureList().get(0).setSensor(false);
    }
}
