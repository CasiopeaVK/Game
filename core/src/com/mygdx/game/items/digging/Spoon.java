package com.mygdx.game.items.digging;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.view.GameRenderer;

public class Spoon extends DiggingItem {
    public static final int SPOON_POWER = 5;
    public Spoon(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
        setPower(SPOON_POWER);
    }
}
