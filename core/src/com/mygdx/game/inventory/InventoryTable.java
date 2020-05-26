package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.items.Item;
import lombok.Getter;

public class InventoryTable extends Table {

    private InventoryCell[] cells;
    @Getter
    private int inventorySize;

    public InventoryTable(int cellInRow, int rowCount){
        inventorySize = cellInRow*rowCount;
        cells = new InventoryCell[inventorySize];

        for(int i = 0; i < rowCount;i++){
            for (int q =0;q<cellInRow;q++){
                InventoryCell cell = new InventoryCell(false,q*InventoryCell.getCELL_SIZE(),i*InventoryCell.getCELL_SIZE()+3);
                cells[i*cellInRow + q] = cell;
                this.addActor(cell);
            }
        }
    }

    public void setItem(Item item, int position){
        cells[position].setItem(item);
    }

}
