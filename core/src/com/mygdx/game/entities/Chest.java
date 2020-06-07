package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameContext;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryTable;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;

import java.util.ArrayList;
import java.util.function.Consumer;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class Chest extends InteractiveEntity {

    InventoryTable inventoryTable;
    Vector2 coords;
    ArrayList<Sprite> sprites;
    SmartStage stage;
    Player player;
    boolean hacked = false;

    public Chest(World world, Camera camera, String texturePath, Vector2 isoPosition, SmartStage stage, GameContext gameContext) {
        super(world, camera, texturePath);
        player = gameContext.getPlayer();
        this.stage = stage;
        this.coords = new Vector2  (isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE - 30);
        sprites = new ArrayList<Sprite>();
        sprites.add(new Sprite(new Texture("environmentTextures/canibet3.png")));
        sprites.add(new Sprite(new Texture("environmentTextures/cabinetOpen3.png")));

        sprites.get(0).setScale(ENVIRONMENT_OBJECTS_SCALE);
        sprites.get(0).setPosition(coords.x, coords.y);

        sprites.get(1).setScale(ENVIRONMENT_OBJECTS_SCALE);
        sprites.get(1).setPosition(coords.x, coords.y-46);

        sprite.set(sprites.get(0));

        ItemBuilder itemBuilder = gameContext.getItemBuilder();
        inventoryTable = new InventoryTable(2,2);
        inventoryTable.setPosition((Gdx.graphics.getWidth() - inventoryTable.getWidth())/2, (Gdx.graphics.getHeight()-inventoryTable.getHeight())/2);
        inventoryTable.setItem(itemBuilder.createItem(GameItems.PLUNGER), 0);
        inventoryTable.setItem(itemBuilder.createItem(GameItems.SCREWDRIVER), 1);
        inventoryTable.setItem(itemBuilder.createItem(GameItems.HAMER), 2);
        inventoryTable.setItem(itemBuilder.createItem(GameItems.HAMER), 3);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        Item selectItem = player.getInventory().getItemInSelectedCell();
        if(!hacked && selectItem != null && selectItem.getName().equals(GameItems.SYPRINGE.getName())){
            hacked = true;
            stage.addEntity(new HackingArcade(new Sprite(new Texture("sypringe.png")), new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) {
                    if(aBoolean){
                        hacked = true;
                        stage.addActor(inventoryTable);
                        player.getInventory().getCellWithItem(GameItems.SYPRINGE).setItem(null);
                    }else {hacked = false;}
                }
            }));
        }
    }

    @Override
    public void update() {
        updateClickListener();
        if(hacked && Gdx.input.isKeyJustPressed(Input.Keys.R) && Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) < 500){
            inventoryTable.setVisible(!inventoryTable.isVisible());
            if(inventoryTable.isVisible()){
                sprite.set(sprites.get(1));
            }else{
                sprite.set(sprites.get(0));
            }
        }else if(Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) >= 500){
            inventoryTable.setVisible(false);
            sprite.set(sprites.get(0));
        }
    }
}
