package com.mygdx.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.utils.IsoUtils;

import java.util.ArrayList;

public class Map {
    private final TiledMap tiledMap;
    private ArrayList<Body> bodies;
    private final float ppt = 4f;
    private World world;

    public Map(final TiledMap tiledMap, World world) {
        this.tiledMap = tiledMap;
        this.world = world;
        this.bodies = new ArrayList<>();
    }

    public MapObject getObject(String layerName, String objectName) {
        MapLayer layer = tiledMap.getLayers().get(layerName);
        MapObjects objects = layer.getObjects();
        return objects.get(objectName);
    }

    public MapProperties getProperties(){
        return tiledMap.getProperties();
    }

    public ArrayList<Body> parseCollisionLayer() {

        final MapLayer collisionLayer = tiledMap.getLayers().get("Collision");
        if (collisionLayer == null) {
            return null;
        }

        final MapObjects mapObjects = collisionLayer.getObjects();
        for (MapObject mapObj : mapObjects) {
            if (mapObj instanceof PolygonMapObject) {
                Shape shape = getPolygon((PolygonMapObject) mapObj);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                Body body = world.createBody(bodyDef);
                body.createFixture(shape, 10);

                bodies.add(body);
                shape.dispose();
            }
        }
        return bodies;
    }

    private ChainShape getPolygon(PolygonMapObject polygonObject) {
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = -vertices[i * 2 + 1] / ppt;

            // Set 2d vertices to Iso + deal with the offset
            Vector2 vel = IsoUtils.TwoDToIso(new Vector2(worldVertices[i].x, worldVertices[i].y));
            worldVertices[i].x = vel.x;
            worldVertices[i].y = vel.y + 20;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}