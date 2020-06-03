package com.mygdx.game.items.improves;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.view.GameRenderer;

public class Hamer extends ImproveItem {
    private static final int HAMER_IMPROVE = 2;
    public Hamer(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
        setImprovePower(HAMER_IMPROVE);
    }
}
