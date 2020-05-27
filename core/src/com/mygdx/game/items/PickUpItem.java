package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.entities.Entity;
import lombok.Getter;

public class PickUpItem extends Entity implements Pool.Poolable {

    @Getter
    private Item item;
    private float collidedRaduis = 40f;
    private float scaleFactor = 1.4f;

    public PickUpItem(World world, Camera camera, String texturePath, Item item) {
        super(world, camera, texturePath);
        this.item = item;
        initialize(world);
    }

    private void initialize(World world) {
        this.spriteScale = 0.1f;
        sprite.setScale(spriteScale);
        sprite.setPosition(300, 300);
        initPickUpTrigger();
    }

    private void initPickUpTrigger() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(collidedRaduis);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    @Override
    public void update() {

    }

    public void setOutlineToSprite(boolean isOutline) {
        if (isOutline) {
            sprite.setScale(sprite.getScaleX() * scaleFactor);
        } else {
            sprite.setScale(sprite.getScaleX() / scaleFactor);
        }
    }

    public void hideItem() {
        body.getWorld().destroyBody(body);
        sprite.setScale(0f);
    }

    @Override
    public void reset() {

    }
}
