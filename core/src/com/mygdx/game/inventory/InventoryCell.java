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
    private static final int CELL_SIZE = 30;
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
        Constants.APP_SKIN.getDrawable("Semi-gray").draw(batch,offset,3,CELL_SIZE,CELL_SIZE);
        Constants.APP_SKIN.getDrawable("black").draw(batch,offset+10,13,10,10);
        batch.setColor(1,1,1,1);
        super.draw(batch,parentAlpha);
    }

}
