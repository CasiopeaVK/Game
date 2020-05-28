package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.view.GameRenderer;

public class ItemBuilder {
    private World world;
    private Camera camera;
    private GameRenderer gameRenderer;

    public ItemBuilder(World world, Camera camera, GameRenderer renderer) {
        this.world = world;
        this.camera = camera;
        this.gameRenderer = renderer;
    }

    public Item createItem(GameItems item) {
        return new Item(world, camera, gameRenderer, item);
    }
}
