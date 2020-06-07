package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.inventory.InventoryTable;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.stage.SmartStage;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class TableFood extends InteractiveEntity {

    private Vector2 coords;
    private Player player;
    private ItemBuilder itemBuilder;
    private InventoryTable inventoryTable;
    private SmartStage stage;
    public TableFood(World world, Camera camera, String texturePath, Vector2 isoPosition, Player player, ItemBuilder itemBuilder, SmartStage stage) {
        super(world, camera, texturePath);
        this.coords = new Vector2  (isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE - 10);
        this.player = player;
        this.itemBuilder = itemBuilder;
        this.stage = stage;
        sprite.setScale(ENVIRONMENT_OBJECTS_SCALE);
        sprite.setPosition(coords.x, coords.y);

        inventoryTable = new InventoryTable(2,1);
        updateInventory();
        stage.addActor(inventoryTable);
        inventoryTable.setVisible(false);
        inventoryTable.setPosition((Gdx.graphics.getWidth() - inventoryTable.getWidth())/2, (Gdx.graphics.getHeight()-inventoryTable.getHeight())/2);
    }

    private void updateInventory(){
        inventoryTable.setItem(itemBuilder.createItem(GameItems.SPOON),0);
        inventoryTable.setItem(itemBuilder.createItem(GameItems.FORK),1);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }



    @Override
    public void update() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.R) && Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) < 500){
            inventoryTable.setVisible(!inventoryTable.isVisible());
        }else if(Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) >= 500){
            inventoryTable.setVisible(false);
        }

        if(TimeManager.getHours() == 7){
            updateInventory();
        }
    }
}
