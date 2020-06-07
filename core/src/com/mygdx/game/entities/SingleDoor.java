package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import lombok.Getter;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;
import static com.mygdx.game.Constants.UNIT_SCALE;


public class SingleDoor extends Entity {

    @Getter
    private Sprite openDoor;
    @Getter
    private Sprite closeDoor;

    public SingleDoor(World world, Camera camera, String texturePath, String texturePathOfOpenState,
                      Vector2 isoPositionOfCloseState, Vector2 isoPositionOfOpenState) {
        super(world, camera, texturePath);
        this.closeDoor = new Sprite(new Texture(texturePath));
        this.openDoor = new Sprite(new Texture(texturePathOfOpenState));
        initializeAnimationSprite(closeDoor, isoPositionOfCloseState);
        initializeAnimationSprite(openDoor, isoPositionOfOpenState);
        this.sprite.set(closeDoor);
    }

    private void initializeAnimationSprite(Sprite sprite, Vector2 isoPosition) {
        sprite.setScale(ENVIRONMENT_OBJECTS_SCALE);
        sprite.setPosition(isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE,
                isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE - 31);
    }

    protected void open() {
        this.sprite.set(openDoor);
    }

    protected void close() {
        this.sprite.set(closeDoor);
    }

    @Override
    public void update() {
        this.setPosition(sprite.getX() - 1000, sprite.getY() - 800);
        System.out.println("Door: " + sprite.getX() + " " + sprite.getY());
    }

    @Override
    public float getX() {
        return this.sprite.getX() + 4;
    }

    @Override
    public float getY() {
        return this.sprite.getY() + sprite.getHeight() * UNIT_SCALE + 11;
    }
}
