package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

public abstract class Entity extends Actor {
    protected Camera camera;
    protected Sprite sprite;
    protected Texture img;
    protected Body body;
    protected World world;

    public Entity(World world, Camera camera, String texturePath) {
        this.world = world;
        this.camera = camera;
        initialize(texturePath);
    }

    private void initialize(String texturePath) {
        img = new Texture(texturePath);
        sprite = new Sprite(img);

    }



    protected void calculateSpawnPosition(Map map, String markerName){
        MapObject spawnPoint = map.getObject("Markers","spawn");
        Rectangle spawnPointRect = ((RectangleMapObject) spawnPoint).getRectangle();
        Vector2 iso = new Vector2(spawnPointRect.x,spawnPointRect.y);

        MapProperties prop = map.getProperties();

        int tileWidth = prop.get("tilewidth", Integer.class);
        int tileHeight = prop.get("tileheight", Integer.class);

        Vector2 res = IsoUtils.IsoTo2d(iso,new Vector2(tileWidth,tileHeight));
        sprite.setPosition(res.x,res.y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    abstract public void update(Camera camera);
}

