package com.mygdx.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;
import com.mygdx.game.items.Item;
import lombok.Getter;
import lombok.Setter;

public class InventoryCell extends ImageButton {

    @Getter
    private static final int CELL_SIZE = 30;
    private static final int CELL_PAD = 3;
    private Item item;

    public InventoryCell(){
        super(Constants.APP_SKIN,"default");
        this.setSize(CELL_SIZE,CELL_SIZE);
    }

    public boolean isEmpty(){
        if(item == null)
            return true;
        return false;
    }

    public void setItem(Item item){
        this.item = item;
        //TODO сделать метод установки картинки айтема в клетку
    }


}
