package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

public class Player extends Entity{
    public Player(World world, Map map, String texturePath) {
        super(world,texturePath);
        initialize(map);
    }

    private void initialize(Map map){
        sprite.setScale(0.6f, 0.6f);
        calculatePosition(map);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());


        body = world.createBody(bodyDef);

        Vector2[] vertices = {
                new Vector2(-sprite.getWidth() / 2, 0),
                new Vector2(0, sprite.getWidth() / 4),
                new Vector2(sprite.getWidth() / 2, 0),
                new Vector2(0, -sprite.getWidth() / 4),
                new Vector2(-sprite.getWidth() / 2, 0),
        };


        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
    }

    public void update(Camera camera){
        float speedX;
        float speedY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -200;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 200;
        } else {
            speedX = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = -200;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = 200;
        } else {
            speedY = 0;
        }
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.TwoDToIso(new Vector2(speedX, speedY)));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 2);
        camera.position.set(body.getPosition().x, body.getPosition().y, 0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    private void calculatePosition(Map map){
        MapObject spawnPoint = map.getObject("Markers","spawn");
        Rectangle spawnPointRect = ((RectangleMapObject) spawnPoint).getRectangle();
        Vector2 iso = new Vector2(spawnPointRect.x,spawnPointRect.y);

        MapProperties prop = map.getProperties();

        int tileWidth = prop.get("tilewidth", Integer.class);
        int tileHeight = prop.get("tileheight", Integer.class);

        System.out.println(iso);
        Vector2 res = IsoUtils.IsoTo2d(iso,new Vector2(tileWidth,tileHeight));
        System.out.println(res);
        sprite.setPosition(res.x,res.y);
    }
}
