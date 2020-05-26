package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Entity;
import lombok.Getter;

public class PickUpItem extends Entity {

    @Getter
    private Item item;

    public PickUpItem(World world, Camera camera, String texturePath, Item item) {
        super(world, camera, texturePath);
        this.item = item;
        initialize(world);
    }

    private void initialize(World world) {
        sprite.setScale(0.2f);
        sprite.setPosition(300, 300);
        initPickUpTrigger();
    }

    private void initPickUpTrigger() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX() - sprite.getWidth() / 2, sprite.getY() - sprite.getWidth() / 2);

        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(40f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update() {

    }

    public void setOutlineToSprite(boolean isOutline) {
        if (isOutline) {
            sprite.setScale(sprite.getScaleX() * 1.4f);
        } else {
            sprite.setScale(sprite.getScaleX() / 1.1f);
        }
    }

    public void hideItem() {

    }

    public void showItem(float x, float y) {

    }
}
