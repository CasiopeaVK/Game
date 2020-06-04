package com.mygdx.game.entities.npc;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;


public class SingleDoor extends Entity {

    @Getter
    private Sprite openDoor;
    @Getter
    private Sprite closeDoor;

    @Getter
    @Setter
    private Vector2 position;

    public SingleDoor(World world, Camera camera, String texturePath, String texturePathOfOpenState) {
        super(world, camera, texturePath);
        this.closeDoor = new Sprite(new Texture(texturePath));
        this.openDoor = new Sprite(new Texture(texturePathOfOpenState));
        this.closeDoor.setScale(1 / 4f);
        this.openDoor.setScale(1 / 4f);
    }

    protected void open() {
        this.getSprite().set(openDoor);
        this.getSprite().setPosition(position.x, position.y);
    }

    protected void close() {
        this.getSprite().set(closeDoor);
        this.getSprite().setPosition(position.x, position.y);
    }

    @Override
    public void update() {

    }
}
