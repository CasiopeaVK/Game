package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.ItemBuilder;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class HospitalTable extends InteractiveEntity {
    private Vector2 coords;
    private Player player;
    private boolean isGive = false;
    private ItemBuilder itemBuilder;

    public HospitalTable(World world, Camera camera, String texturePath, Vector2 isoPosition, Player player, ItemBuilder itemBuilder) {
        super(world, camera, texturePath);
        this.coords = new Vector2  (isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE - 30);
        this.player = player;
        this.itemBuilder = itemBuilder;
        sprite.setScale(ENVIRONMENT_OBJECTS_SCALE);
        sprite.setPosition(coords.x, coords.y);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        if(!isGive){
            player.getInventory().addItem(itemBuilder.createItem(GameItems.SYPRINGE));
            isGive = true;
        }
    }

    @Override
    public void update() {
        updateClickListener();
    }
}
