package com.mygdx.game.items.improves;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;
import lombok.Setter;

abstract class ImproveItem extends Item {

    @Getter@Setter
    private int improvePower;

    public ImproveItem(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
        setDigging(false);
        setImproves(true);
    }
}
