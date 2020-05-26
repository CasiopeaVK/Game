package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InventoryTable extends Table {

    private InventoryCell[] cells;

    public InventoryTable(int cellInRow, int rowCount){
        cells = new InventoryCell[cellInRow*rowCount];

        for(int i = 0; i < rowCount;i++){
            for (int q =0;q<cellInRow;q++){
                InventoryCell cell = new InventoryCell(false,q*InventoryCell.getCELL_SIZE(),i*InventoryCell.getCELL_SIZE()+3);
                cells[i*cellInRow + q] = cell;
                this.addActor(cell);
            }

        }
    }

}
