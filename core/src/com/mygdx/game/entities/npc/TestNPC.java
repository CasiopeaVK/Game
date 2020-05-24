package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.InteractiveObject;
import com.mygdx.game.map.Map;

public class TestNPC extends InteractiveObject {
    public TestNPC(World world, Map map, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        initialize(map);
    }

    private void initialize(Map map){
        calculateSpawnPosition(map,"testNpc");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        System.out.println(new Vector2(sprite.getWidth(),sprite.getHeight()));

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
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 4);
    }
}
