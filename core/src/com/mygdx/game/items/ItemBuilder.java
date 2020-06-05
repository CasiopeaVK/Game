package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.digging.EmptyPlate;
import com.mygdx.game.items.digging.Fork;
import com.mygdx.game.items.digging.Screwdriver;
import com.mygdx.game.items.digging.Spoon;
import com.mygdx.game.items.improves.Hamer;
import com.mygdx.game.items.sample.*;
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
        switch (item){
            case SPOON:
                return new Spoon(world,camera,gameRenderer,item);
            case FORK:
                return new Fork(world,camera,gameRenderer,item);
            case SCREWDRIVER:
                return new Screwdriver(world,camera,gameRenderer,item);
            case HAMER:
                return new Hamer(world,camera,gameRenderer,item);
            case DIRT:
                return new Dirt(world,camera,gameRenderer,item);
            case PLATE:
                return new EmptyPlate(world,camera,gameRenderer,item);
            case PLATE_FOOD:
                return new PlateFood(world,camera,gameRenderer,item);
            case PLUNGER:
                return new Plunger(world,camera,gameRenderer,item);
            default:
                return new Sypringe(world,camera,gameRenderer,item);
        }
    }
}
