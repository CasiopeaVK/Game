package com.mygdx.game.map;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameContext;
import com.mygdx.game.utils.IsoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map {
    private final TiledMap tiledMap;
    private ArrayList<Body> bodies;
    private final float ppt = 4f;
    private World world;
    private ArrayList<Light> lights = new ArrayList<>();
    private Color color = new Color(0, 0, 0, 0.7f);
    RayHandler rayHandler;

    public Map(final TiledMap tiledMap, GameContext context) {
        this.tiledMap = tiledMap;
        this.world = context.getWorld();
        this.bodies = new ArrayList<>();
        rayHandler = context.getRayHandler();
    }

    public MapObject getObject(String layerName, String objectName) {
        MapLayer layer = getLayer(layerName);
        MapObjects objects = layer.getObjects();
        return objects.get(objectName);
    }

    public MapLayer getLayer(String layerName) {
        return tiledMap.getLayers().get(layerName);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public MapProperties getProperties() {
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

    private Shape getPolygon(PolygonMapObject polygonObject) {
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        List<Vector2> worldVertices = Stream.generate(Vector2::new).limit(vertices.length / 2).collect(Collectors.toList());
        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices.set(i, new Vector2(vertices[i * 2] / ppt, -vertices[i * 2 + 1] / ppt));

            // Set 2d vertices to Iso + deal with the offset
            Vector2 vel = IsoUtils.TwoDToIso(worldVertices.get(i));
            vel.y += 20;
            worldVertices.set(i, vel);
        }
        //hack to fix libgdx bug when polygon is not closed
        Vector2 last = worldVertices.get(0);
        //Avoiding error when last point cannot be the same as first in chain
        last.x -= 1;
        last.y -= 1;
        worldVertices.add(last);
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices.toArray(Vector2[]::new));
        return chain;
    }

    private Light createNightLight(int distance, int x, int y) {
        Light light = new PointLight(rayHandler, 128, color, distance, x, y);
        light.setSoft(true);
        return light;
    }

    public void addNightLight() {
        for (MapObject point : getLayer("Lights").getObjects()) {
            Rectangle rect = ((RectangleMapObject) point).getRectangle();
            int size = 0;
            switch (point.getName()) {
                case "small":
                    size = 400;
                    break;
                case "medium":
                    size = 600;
                    break;
                case "big":
                    size = 900;
                    break;
            }
            Vector2 coords = IsoUtils.IsoTo2d(new Vector2(rect.x, rect.y));
            lights.add(createNightLight(size, (int) coords.x, (int) coords.y));
        }
//        lights.add(createNightLight(600, 2750, -300));
//        lights.add(createNightLight(600, 3100, -450));
//        lights.add(createNightLight(800, 3650, -685));
//        lights.add(createNightLight(650, 3930, 65));
//        lights.add(createNightLight(900, 5100, -230));
//        lights.add(createNightLight(900, 5000, -830));
//        lights.add(createNightLight(400, 6100, -330));
//        lights.add(createNightLight(800, 6850, -725));
//        lights.add(createNightLight(800, 5680, -1450));
//        lights.add(createNightLight(700, 4450, 310));
    }

    public void removeNightLight() {
        for (int i = 0; i < lights.size(); ) {
            lights.get(i).remove();
            lights.remove(i);
        }
    }
}