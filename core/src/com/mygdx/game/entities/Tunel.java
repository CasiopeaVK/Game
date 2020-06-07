package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameContext;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.digging.DiggingItem;
import com.mygdx.game.items.improves.ImproveItem;
import com.mygdx.game.quest.QuestLine;

import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

enum TunnelState {
    START, BETWEEN, END;
}

public class Tunel extends InteractiveEntity {


    int healthPoint = 30;
    Inventory inventory;
    ItemBuilder itemBuilder;
    Player player;
    ArrayList<Sprite> tunnels;
    Vector2 coords;
    TunnelState tunnelState;
    QuestLine questLine;
    int currentTunnel;

    public Tunel(World world, Camera camera, Sprite sprite, Inventory inventory, ItemBuilder itemBuilder, Player player, Vector2 isoPosition, QuestLine questLine) {
        super(world, camera, sprite);
        this.questLine = questLine;
        this.coords = new Vector2(isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE);
        this.inventory = inventory;
        this.itemBuilder = itemBuilder;
        this.player = player;
        this.setTouchable(Touchable.enabled);
        tunnels = new ArrayList<Sprite>();
        tunnels.add(new Sprite(new Texture("environmentTextures/tunnel0.png")));
        tunnels.add(new Sprite(new Texture("environmentTextures/tunnel1.png")));
        tunnels.add(new Sprite(new Texture("environmentTextures/tunnel2.png")));
        tunnels.add(new Sprite(new Texture("environmentTextures/tunnel3.png")));
        for (Sprite sprite1 : tunnels) {
            sprite1.setScale(ENVIRONMENT_OBJECTS_SCALE);
            sprite1.setPosition(coords.x, coords.y);
        }
        currentTunnel = 0;
        tunnelState = TunnelState.START;
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    private void work() {
        InventoryCell cell = inventory.getSellectedCell();
        Item item = cell.getItem();

        if (item instanceof DiggingItem && tunnelState != TunnelState.END) {
            InventoryCell improvesCell = inventory.getCellWithImprovesItem();
            if (improvesCell != null && item.isImprovable()) {
                healthPoint -= ((DiggingItem) item).getPower() * ((ImproveItem) improvesCell.getItem()).getImprovePower();
                improvesCell.setItem(null);
            } else {
                healthPoint -= ((DiggingItem) item).getPower();
            }
            cell.setItem(null);
            currentTunnel++;
            tunnelState = TunnelState.BETWEEN;
            if (currentTunnel == 3)
                tunnelState = TunnelState.END;
            itemBuilder.createItem(GameItems.DIRT);
        } else if (tunnelState != TunnelState.START && item == null) {
            cell.setItem(itemBuilder.createItem(GameItems.DIRT));
            currentTunnel--;
            if (currentTunnel == 0)
                tunnelState = TunnelState.START;
        }
        if (healthPoint == 0) {
            //TODO win game
        }
        System.out.println(healthPoint);
    }

    private void endGame(){
        if(healthPoint <= 0){
            questLine.incrementQuest();
        }
    }
    @Override
    public void update() {
        if (Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) < 200 && Gdx.input.isKeyJustPressed(Input.Keys.R))
            work();
        sprite.set(tunnels.get(currentTunnel));
        endGame();
    }
}
