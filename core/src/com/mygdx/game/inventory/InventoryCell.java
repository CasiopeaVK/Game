package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.items.Item;
import lombok.Getter;

public class InventoryCell extends Table {

    private static final String SAMPLE_CELL_STYLE =  "cell-draw";
    private static final String SELECTED_CELL_STYLE =  "selectCell";

    @Getter
    private static final int CELL_SIZE = 60;
    private static final int CELL_ITEM_SIZE = 50;
    private Item item;
    private int dy;
    private boolean current = false;

    public InventoryCell(boolean current,  int offset, int dY) {
        super(Constants.APP_SKIN);

        this.dy = dY;
        this.setPosition(offset, dy);
        this.setSize(CELL_SIZE, CELL_SIZE);

        this.current = current;
        this.setTouchable(Touchable.enabled);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (item == null && Item.selectedItem == null) {
                    super.clicked(event, x, y);
                } else if (item == null) {
                    item = Item.selectedItem;
                    Item.selectedItem = null;
                    super.clicked(event, x, y);
                } else if (Item.selectedItem == null) {
                    Item.selectedItem = item;
                    item = null;
                    super.clicked(event, x, y);
                }else {
                    Item type = item;
                    item = Item.selectedItem;
                    Item.selectedItem = type;
                    super.clicked(event, x, y);
                }
            }
        });
    }

    public boolean isEmpty() {
        return item == null;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setCurrent(boolean isCurrent) {
        this.current = isCurrent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, parentAlpha);
        Constants.APP_SKIN.getDrawable(current ? SELECTED_CELL_STYLE : SAMPLE_CELL_STYLE).draw(batch, this.getX(), this.getY(), CELL_SIZE, CELL_SIZE);

        if (item != null) {
            int dx =(CELL_SIZE-CELL_ITEM_SIZE)/2;
            Constants.APP_SKIN.getDrawable(item.getName()).draw(batch, this.getX() + dx, this.getY()+dx, CELL_ITEM_SIZE, CELL_ITEM_SIZE);
        }

        batch.setColor(1, 1, 1, 1);
    }


}
