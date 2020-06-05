package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory extends Table {

    private final int COUNT_CELL = 6;

    private InventoryCell[] cells;
    private int currentCell = 0;

    public Inventory() {
        cells = new InventoryCell[COUNT_CELL];
        initializeCell();
        this.setSize(InventoryCell.getCELL_SIZE() * COUNT_CELL, InventoryCell.getCELL_SIZE());
        this.center();
        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, 0);

    }

    private void initializeCell() {
        for (int i = 0; i < COUNT_CELL; i++) {
            cells[i] = new InventoryCell(currentCell == i, i * InventoryCell.getCELL_SIZE(), 3);
            this.addActor(cells[i]);
        }
    }

    public InventoryCell getCellWithItem(GameItems item) {
        String name = item.getName();
        for (InventoryCell cell : cells) {
            if (cell.getItem() != null && cell.getItem().getName().equals(name))
                return cell;
        }
        return null;
    }

    public boolean addItem(Item item) {
        for (InventoryCell cell : cells) {
            if (cell.isEmpty()) {
                cell.setItem(item);
                return true;
            }
        }
        return false;
    }

    public void setCurrentCell(int currentCell) {
        cells[this.currentCell].setCurrent(false);
        this.currentCell = currentCell;
        cells[this.currentCell].setCurrent(true);
    }

    public boolean isFull() {
        for (InventoryCell cell : cells) {
            if (cell.isEmpty()) return false;
        }
        return true;
    }

    public boolean isSelectedEmpty() {
        return cells[currentCell].isEmpty();
    }

    public InventoryCell getSellectedCell() {
        return cells[currentCell];
    }

    public Item getItemInSelectedCell() {
        return cells[currentCell].getItem();
    }

    public InventoryCell getCellWithImprovesItem() {
        for (InventoryCell cell : cells) {
            if (cell.getItem() != null && cell.getItem().isImproves())
                return cell;
        }
        return null;
    }

    public List<InventoryCell> getListCells() {
        return Arrays.asList(cells);
    }
}
