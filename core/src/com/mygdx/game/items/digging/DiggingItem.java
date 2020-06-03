package com.mygdx.game.items.digging;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;

abstract class DiggingItem extends Item {

    @Getter
    private int power;

    public DiggingItem(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, renderer, item);
        setDigging(true);
    }

    protected void setPower(int power){this.power = power;}

}
