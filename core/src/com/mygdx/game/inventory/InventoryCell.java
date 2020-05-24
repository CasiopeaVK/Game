package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.mygdx.game.Constants;
import com.mygdx.game.items.Item;
import lombok.Getter;
import lombok.Setter;

public class InventoryCell extends Widget {

    @Getter
    private static final int CELL_SIZE = 60;
    private Item item;
    private int offset =0;
    public InventoryCell(){
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

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.setColor(1,1,1,parentAlpha);
        Constants.APP_SKIN.getDrawable("cell-draw").draw(batch,offset,3,CELL_SIZE,CELL_SIZE);
        if(item!=null){
            Constants.APP_SKIN.getDrawable(item.getName()).draw(batch,offset+5,8,50,50);
        }
        batch.setColor(1,1,1,1);
        super.draw(batch,parentAlpha);
    }

}
