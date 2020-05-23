package com.mygdx.game.map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameContext;

import java.util.EnumMap;

public class MapManager {
    private static final String TAG = MapManager.class.getSimpleName();

    private final World world;
    private final Array<Body> bodies;

    private final AssetManager assetManager;

    private MapType currentMapType;
    private Map currentMap;
    private final Array<MapListener> listeners;

    private final EnumMap<MapType, Map> mapCache;

    public MapManager(final GameContext context) {
        currentMapType = null;
        currentMap = null;
        world = context.getWorld();
        assetManager = context.getAssetManager();
        bodies = new Array<Body>();
        mapCache = new EnumMap<MapType, Map>(MapType.class);
        listeners = new Array<MapListener>();
    }

    public void addMapListener(final MapListener listener){
        listeners.add(listener);
    }

    public void setMap(final MapType type) {
        if (currentMapType == type) {
            return;
        }
        if (currentMap != null) {
            world.getBodies(bodies);
        }

        Gdx.app.debug(TAG, "Changing to map: " + type);
        currentMap = mapCache.get(type);
        if (currentMap == null) {
            Gdx.app.debug(TAG, "Creating new map of type: " + type);
            final TiledMap tiledMap = assetManager.get(type.getFilePath(), TiledMap.class);
            currentMap = new Map(tiledMap, world);
            mapCache.put(type, currentMap);
        }
        for (final MapListener listener: listeners){
            listener.mapChange(currentMap);
        }
    }

    public Map getCurrentMap() {
        return currentMap;
    }

}