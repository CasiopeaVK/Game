package com.mygdx.game.items.digging;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.view.GameRenderer;

public class Screwdriver extends DiggingItem {
    public static final int SCREWDRIVER_POWER = 5;

    public Screwdriver(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
        setPower(SCREWDRIVER_POWER);
        setImprovable(true);
    }
}
