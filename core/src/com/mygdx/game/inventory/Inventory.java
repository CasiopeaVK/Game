package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;
import com.mygdx.game.items.Item;

public class Inventory extends HorizontalGroup {

    private final int COUNT_CELL = 6;

    private InventoryCell[] cells;
    private int currentCell = 0;

    public Inventory(){
        cells = new InventoryCell[COUNT_CELL];
        initializeCell();
        this.setSize(InventoryCell.getCELL_SIZE()*COUNT_CELL, InventoryCell.getCELL_SIZE());
        this.center();
        this.setPosition((Gdx.graphics.getWidth()-getWidth())/2,0);
    }

    private void initializeCell(){
        for(int i =0; i<COUNT_CELL;i++){
            cells[i] = new InventoryCell();
            cells[i].setOffset(i*cells[i].getCELL_SIZE());
            this.addActor(cells[i]);
        }

    }

    public void addItem(Item item){
        for(InventoryCell cell:cells){
            if(cell.isEmpty()){
                cell.setItem(item);
                return;
            }
        }

        cells[currentCell].setItem(item);
    }


}