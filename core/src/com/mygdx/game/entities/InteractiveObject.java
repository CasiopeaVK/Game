package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;

public class InteractiveObject extends  Entity{
    public InteractiveObject(World world, String texturePath) {
        super(world, texturePath);
    }

    @Override
    public void update(Camera camera) {

    }
}
