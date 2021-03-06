package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.map.Map;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Entity extends Actor {
    @Getter
    protected Camera camera;
    @Getter
    protected Sprite sprite;
    protected Texture img;
    @Getter @Setter
    protected Body body;
    @Getter
    protected World world;
    @Getter
    protected float spriteScale = 0.6f;
    private String texturePath;

    public Entity(World world, Camera camera, String texturePath) {
        this.world = world;
        this.camera = camera;
        this.texturePath = texturePath;
        initialize(texturePath);
    }
    public Entity(World world, Camera camera,Sprite sprite){
        this.world = world;
        this.camera = camera;
        this.sprite = sprite;
    }

    private void initialize(String texturePath) {
        img = new Texture(texturePath);
        sprite = new Sprite(img);
    }

    protected void calculateSpawnPosition(Map map, String markerName) {
        MapObject spawnPoint = map.getObject("Markers", markerName);
        Rectangle spawnPointRect = ((RectangleMapObject) spawnPoint).getRectangle();
        Vector2 iso = new Vector2(spawnPointRect.x, spawnPointRect.y);
        MapProperties prop = map.getProperties();

        Vector2 res = IsoUtils.IsoTo2d(iso);
        setPosition(res);
    }

    protected void setPosition(Vector2 vector2) {
        sprite.setPosition(vector2.x, vector2.y);
    }

    public PolygonShape createHeptagonPolygonShape() {
        int STEPS = 7;
        Vector2[] vertices = new Vector2[STEPS + 1];
        for (int i = 0; i < STEPS; i++) {
            float t = (float) (i * 2 * Math.PI) / STEPS;
            vertices[i] = new Vector2(sprite.getWidth() / 3 * (float) Math.cos(t), sprite.getWidth() / 6 * (float) Math.sin(t));
        }
        vertices[STEPS] = new Vector2(sprite.getWidth() / 3 * (float) Math.cos(0), 0);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        return polygonShape;
    }

    public void initCharacterBody(BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);
        PolygonShape polygonShape = createHeptagonPolygonShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef).setUserData(this);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 2);
    }

    protected Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    protected Vector2 getIsoPosition() {
        return IsoUtils.LocalToIso(getPosition());
    }

    public void setSpriteScale(float scale) {
        spriteScale = scale;
    }

    abstract public void update();


    @Override
    public float getHeight() {
        return sprite.getHeight()*spriteScale;
    }

    @Override
    public float getWidth() {
        return sprite.getWidth()*spriteScale;
    }

    @Override
    public boolean remove() {
        ((SmartStage)getStage()).remove(this);
        return false;
    }
}

