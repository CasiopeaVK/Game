package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.digging.DiggingItem;
import com.mygdx.game.items.improves.ImproveItem;
import com.mygdx.game.screenUI.GameUI;

public class Tunel extends InteractiveEntity {

    private final int POSITION_X = 200;
    private final int POSITION_Y = 100;

    int healthPoint = 100;
    Inventory inventory;
    ItemBuilder itemBuilder;

    public Tunel(World world, Camera camera, String texturePath, Inventory inventory, ItemBuilder itemBuilder) {
        super(world, camera, texturePath);
        this.inventory = inventory;
        this.itemBuilder = itemBuilder;
        initialize();
    }

    private void initialize() {
        sprite.setPosition(POSITION_X, POSITION_Y);
        sprite.setScale(0.4f);
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick(event, x, y);
            }
        });

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
            sprite.setPosition(POSITION_X, POSITION_Y);
        }
        if (healthPoint == 0) {
            sprite = new Sprite(new Texture("cube.png"));
        }
    }

    @Override
    public void update() {
        updateClickListener();
    }
}
