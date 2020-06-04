package com.mygdx.game.entities;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.GameContext;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.sample.Dirt;
import com.mygdx.game.items.sample.Plunger;

public class Toilet extends InteractiveEntity {
    public static int clogging = 0;
    private int capacity = 4;
    private boolean clogged = false;
    private String lastTick = "0 : 0";
    private String lastUsed = "0 : 0";
    private String currentTime;
    private Inventory inventory;
    private Player player;

    public Toilet(GameContext context, String texturePath) {
        super(context.getWorld(), context.getCamera(), texturePath);
        inventory = context.getPlayer().getInventory();
        player = context.getPlayer();
        inventory.addItem(context.getItemBuilder().createItem(GameItems.DIRT));
        inventory.addItem(context.getItemBuilder().createItem(GameItems.DIRT));
        inventory.addItem(context.getItemBuilder().createItem(GameItems.DIRT));
        inventory.addItem(context.getItemBuilder().createItem(GameItems.PLUNGER));
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        InventoryCell cell = inventory.getSellectedCell();
        Item item = cell.getItem();
        if (item instanceof Dirt && !lastUsed.equals(currentTime) && clogging != capacity && Math.abs(player.getSprite().getX() - this.getSprite().getX()) + Math.abs(player.getSprite().getY() - this.getSprite().getY()) < 150) {
            cell.setItem(null);
            clogging++;
            lastUsed = currentTime;
        } else if (item instanceof Plunger && clogging != 0) {
            clogging = 0;
            ((Plunger) item).decreaseCapacity();
            if(((Plunger) item).getCapacity()==0)
                cell.setItem(null);
        }
    }

    @Override
    public void update() {
        updateClickListener();
        try {
            currentTime = TimeManager.getTime();
            if (Integer.parseInt(currentTime.split(" : ")[0]) % 2 == 0 && Integer.parseInt(currentTime.split(" : ")[1]) == 0 && !lastTick.split(" : ")[0].equals(currentTime.split(" : ")[0])) {
                lastTick = currentTime;
                if (clogging > 0)
                    clogging--;
            }
        } catch (NumberFormatException e) {
            System.out.println("Number format exception");
        }
    }
}



