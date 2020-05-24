package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.EllipseShapeBuilder;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

public class Player extends Entity{
    public Player(World world, Map map, Camera camera, String texturePath) {
        super(world,camera,texturePath);
        initialize(map);
    }

    private void initialize(Map map){
        sprite.setScale(spriteScale);
        calculateSpawnPosition(map,"spawn");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);

        int STEPS = 7;
        Vector2[] vertices = new Vector2[STEPS + 1];
        for(int i = 0; i < STEPS; i++)
        {
            float t = (float)(i*2*Math.PI)/STEPS;
            vertices[i] = new Vector2(sprite.getWidth() / 3 * (float)Math.cos(t), sprite.getWidth() / 6 * (float)Math.sin(t));
        }
        vertices[STEPS] = new Vector2(sprite.getWidth() / 3 * (float)Math.cos(0), 0);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
    }

    public void update(){
        float speedX;
        float speedY;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -400;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 400;
        } else {
            speedX = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = -400;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = 400;
        } else {
            speedY = 0;
        }
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.TwoDToIso(new Vector2(speedX, speedY)));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getWidth() / 2);
        camera.position.set(body.getPosition().x, body.getPosition().y, 0);
    }
}
