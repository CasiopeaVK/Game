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
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.utils.IsoUtils;

import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class Chest extends InteractiveEntity {

    InventoryTable inventoryTable;
    Vector2 coords;
    ArrayList<Sprite> sprites;
    Stage stage;
    public Chest(World world, Camera camera, String texturePath, Vector2 isoPosition, Stage stage, GameContext gameContext) {
        super(world, camera, texturePath);
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

    }

    @Override
    public void update() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            sprite.set(sprites.get(1));
            stage.addActor(inventoryTable);
        }
    }
}
