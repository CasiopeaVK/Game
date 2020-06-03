package com.mygdx.game.items.sample;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;

public class PlateFood extends Item {

    public PlateFood(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
    }


}
