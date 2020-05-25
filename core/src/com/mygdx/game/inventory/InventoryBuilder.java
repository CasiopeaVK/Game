package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InventoryBuilder {

    public static class InventoryTable extends Table{
        private InventoryCell[] cells;

        public InventoryTable(int cellsCount){
            cells = new InventoryCell[cellsCount];
        }

        public void setCells(int number, InventoryCell cell){
            cells[number] = cell;
            this.addActor(cells[number]);
        }
    }

    public static InventoryTable getInventoryTable(int cellInRow, int rowCount){
        InventoryTable table = new InventoryTable(cellInRow*rowCount);
        for(int i = 0; i < rowCount;i++){
            for (int q =0;q<cellInRow;q++){
                table.setCells(i*cellInRow + q, new InventoryCell(false,q*InventoryCell.getCELL_SIZE(),i*InventoryCell.getCELL_SIZE()+3));
            }

        }

        return table;
    }
}
