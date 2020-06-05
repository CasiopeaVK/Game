package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.Constants.DOOR_OBJECTS_SCALE;


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
        sprite.setScale(DOOR_OBJECTS_SCALE);
        sprite.setPosition(isoPosition.x - sprite.getWidth() * 2 * DOOR_OBJECTS_SCALE,
                isoPosition.y - sprite.getHeight() * DOOR_OBJECTS_SCALE - 31);
    }

    protected void open() {
        this.sprite.set(openDoor);
    }

    protected void close() {
        this.sprite.set(closeDoor);
    }

    @Override
    public void update() {
        this.setPosition(sprite.getX(), sprite.getY() + DOOR_OBJECTS_SCALE * this.sprite.getHeight());
    }
}
