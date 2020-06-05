package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Bed extends InteractiveEntity {
    public Bed(World world, Camera camera, Sprite sprite) {
        super(world, camera, sprite);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    @Override
    public void update() {

    }
}
