package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.digging.DiggingItem;
import com.mygdx.game.items.improves.ImproveItem;


public class Tunel extends InteractiveEntity {


    int healthPoint = 100;
    Inventory inventory;
    ItemBuilder itemBuilder;

    public Tunel(World world, Camera camera, String texturePath, Inventory inventory, ItemBuilder itemBuilder) {
        super(world, camera, texturePath);
        this.inventory = inventory;
        this.itemBuilder = itemBuilder;
        this.setTouchable(Touchable.enabled);

    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        InventoryCell cell = inventory.getSellectedCell();
        Item item = cell.getItem();
        if (item instanceof DiggingItem) {
            InventoryCell improvesCell = inventory.getCellWithImprovesItem();
            if (improvesCell != null) {
                healthPoint -= ((DiggingItem) item).getPower() * ((ImproveItem) improvesCell.getItem()).getImprovePower();
                improvesCell.setItem(null);
            } else {
                healthPoint -= ((DiggingItem) item).getPower();
            }
            cell.setItem(itemBuilder.createItem(GameItems.DIRT));
        }
        if (healthPoint == 0) {
            //TODO win game
        }
    }

    @Override
    public void update() {
        updateClickListener();
    }
}
