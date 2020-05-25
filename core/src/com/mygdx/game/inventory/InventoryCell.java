package com.mygdx.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.items.Item;
import lombok.Getter;
import lombok.Setter;

public class InventoryCell extends Table {

    @Getter
    private static final int CELL_SIZE = 60;
    private Item item;
    private int offset = 0;
    private boolean current = false;

    public InventoryCell(boolean current, final int offset) {
        super(Constants.APP_SKIN);

        this.setPosition(offset, 3);
        this.setSize(CELL_SIZE, CELL_SIZE);

        this.current = current;
        this.setTouchable(Touchable.enabled);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (item == null && Item.selectedItem == null) {
                    super.clicked(event, x, y);
                } else if (item == null && Item.selectedItem != null) {
                    item = Item.selectedItem;
                    Item.selectedItem = null;
                    super.clicked(event, x, y);
                } else if (item != null && Item.selectedItem == null) {
                    Item.selectedItem = item;
                    item = null;
                    if (Item.selectedItem != null)
                        System.out.println(Item.selectedItem.getName());
                    super.clicked(event, x, y);
                }else if (item != null && Item.selectedItem != null){
                    Item type = item;
                    item = Item.selectedItem;
                    Item.selectedItem = type;
                    super.clicked(event, x, y);
                }
            }
        });
    }

    public boolean isEmpty() {
        if (item == null)
            return true;
        return false;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setCurrent(boolean isCurrent) {
        this.current = isCurrent;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, parentAlpha);
        Constants.APP_SKIN.getDrawable(current ? "selectCell" : "cell-draw").draw(batch, this.getX(), this.getY(), CELL_SIZE, CELL_SIZE);
        if (item != null) {
            Constants.APP_SKIN.getDrawable(item.getName()).draw(batch, this.getX() + 5, this.getY() + 3, 50, 50);
        }
        batch.setColor(1, 1, 1, 1);
    }


}
