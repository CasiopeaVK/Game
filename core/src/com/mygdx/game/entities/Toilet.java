package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Time.TimeManager;

public class Toilet extends InteractiveEntity {
    private int clogging = 0;
    private int capacity = 4;
    private boolean clogged = false;
    private int lastTick = 0;
    private String currentTime;

    public Toilet(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    @Override
    public void update() {
        try {
            currentTime = TimeManager.getTime();
            if (Integer.parseInt(currentTime.split(" : ")[0]) % 2 == 0 && Integer.parseInt(currentTime.split(" : ")[1]) == 0 && lastTick != Integer.parseInt(currentTime.split(" : ")[0])) {
                lastTick = Integer.parseInt(currentTime.split(" : ")[0]);
                System.out.println("cleaning");
            }
        } catch (NumberFormatException e) {
            System.out.println("Number format exception");
        }
    }
}



